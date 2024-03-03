package com.example.onlineauction.controller;

import com.example.onlineauction.dto.bid.GetLotBidsDto;
import com.example.onlineauction.dto.bid.NewBidRequestDto;
import com.example.onlineauction.dto.lot.LotInfoDto;
import com.example.onlineauction.dto.lot.createLot.CreateLotRequestDto;
import com.example.onlineauction.dto.lot.createLot.CreateLotResponseDto;
import com.example.onlineauction.dto.lot.editLot.EditLotRequestData;
import com.example.onlineauction.dto.lot.getLotList.GetLotListRequestDto;
import com.example.onlineauction.dto.lot.getLotList.GetLotListResponseDto;
import com.example.onlineauction.entity.BidEntity;
import com.example.onlineauction.entity.LotEntity;
import com.example.onlineauction.security.JwtUserDetails;
import com.example.onlineauction.service.BidService;
import com.example.onlineauction.service.LotService;
import com.example.onlineauction.service.TrackingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/v1/lots")
@RequiredArgsConstructor
public class LotController {
    private final Map<Long, SseEmitter> sseClients = new ConcurrentHashMap<>();

    private final LotService lotService;
    private final TrackingService trackingService;
    private final BidService bidService;
    private final ModelMapper modelMapper;

    @PostMapping
    public GetLotListResponseDto getLotList(@RequestBody GetLotListRequestDto requestDto) {
        Page<LotInfoDto> lotByFilters = lotService.getListByFilters(
                requestDto.getFilters(),
                requestDto.getOrder(),
                requestDto.getPage(),
                requestDto.getLimit()
        );
        return new GetLotListResponseDto(lotByFilters.getContent(), lotByFilters.getTotalPages());
    }

    @PostMapping(path = "/new", consumes = "multipart/form-data")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CreateLotResponseDto createNewLot(@Validated @RequestPart("lot") CreateLotRequestDto createLotRequestDto,
                                             @RequestPart("image") List<MultipartFile> images,
                                             @AuthenticationPrincipal JwtUserDetails principal) {
        LotEntity lot = modelMapper.map(createLotRequestDto, LotEntity.class);
        Long lotId = lotService.create(lot, images, principal.getUserEntity());
        return new CreateLotResponseDto().setId(lotId);
    }

    @PutMapping(path = "/{id}", consumes = "multipart/form-data")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void editLot(@PathVariable Long id,
                        @Validated @RequestPart("lot") EditLotRequestData editLotRequestData,
                        @RequestPart(value = "image", required = false) List<MultipartFile> images,
                        @AuthenticationPrincipal JwtUserDetails principal) {
        lotService.edit(id, editLotRequestData, images, principal.getUserEntity());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LotInfoDto> getLot(@PathVariable Long id) {
        LotInfoDto fullLotInfo = lotService.getFullLotInfo(id);

        return new ResponseEntity<>(fullLotInfo, HttpStatus.OK);
    }

    @PostMapping(path = "/{id}/bids/new")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Long> makeNewBid(@PathVariable("id") Long lotId,
                                           @Validated @RequestBody NewBidRequestDto bidDto,
                                           @AuthenticationPrincipal JwtUserDetails principal) {
        Long bidId = bidService.create(principal.getUserEntity(), lotId, bidDto.getBid());
        return new ResponseEntity<>(bidId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/bids")
    public ResponseEntity<List<GetLotBidsDto>> bids(@PathVariable("id") Long lotId) {
        List<BidEntity> bids = bidService.findAllByLotId(lotId);
        List<GetLotBidsDto> lotBidsDtos = modelMapper
                .map(bids, new TypeToken<List<GetLotBidsDto>>() {
                }.getType());
        return new ResponseEntity<>(lotBidsDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}/bids-stream")
    public SseEmitter bidsStream(@PathVariable("id") Long lotId) throws IOException {
        SseEmitter emitter = new SseEmitter();
        sseClients.computeIfPresent(lotId, (s, o) -> {
            if (lotService.isClosedById(lotId)) {
                emitter.completeWithError(new IllegalStateException("Lot auction already closed"));
            }
            return null;
        });
        sseClients.putIfAbsent(lotId, emitter);
        emitter.onTimeout(() -> sseClients.compute(lotId, (s, o) -> null));
        emitter.onError(throwable -> sseClients.compute(lotId, (s, o) -> null));

        emitter.send(SseEmitter.event()
                .id(String.valueOf(lotId))
                .name(String.format("lot '%d' bids-stream", lotId))
        );

        return emitter;
    }

    @PostMapping("/{id}/track")
    public ResponseEntity<Void> trackLot(@PathVariable("id") Long lotId,
                                         @AuthenticationPrincipal JwtUserDetails principal) {
        trackingService.track(principal.getUserEntity(), lotId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}/track")
    public ResponseEntity<Void> deleteTrack(@PathVariable("id") Long lotId,
                                            @AuthenticationPrincipal JwtUserDetails principal) {
        trackingService.deleteTrack(principal.getUserEntity(), lotId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Async
    @EventListener
    public void bidsChangeHandler(BidEntity bidEntity) {
        sseClients.computeIfPresent(bidEntity.getLot().getId(), (lotId, emitter) -> {
            try {
                emitter.send(bidEntity, MediaType.APPLICATION_JSON);
            } catch (Exception e) {
                sseClients.compute(lotId, (s, o) -> null);
            }
            return emitter;
        });
    }

}
