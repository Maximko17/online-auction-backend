package com.example.onlineauction.controller;

import com.example.onlineauction.dto.bid.BidsCountAndMaxBidDto;
import com.example.onlineauction.dto.bid.GetLotBidsDto;
import com.example.onlineauction.dto.bid.NewBidRequestDto;
import com.example.onlineauction.dto.lot.*;
import com.example.onlineauction.dto.lot.createLot.CreateLotRequestDto;
import com.example.onlineauction.dto.lot.createLot.CreateLotResponseDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@RestController
@RequestMapping("/api/v1/lots")
@RequiredArgsConstructor
public class LotController {
    private final Set<SseEmitter> clients = new CopyOnWriteArraySet<>();

    private final LotService lotService;
    private final TrackingService trackingService;
    private final BidService bidService;
    private final ModelMapper modelMapper;

    @PostMapping
    public GetLotListResponseDto getLotList(@RequestBody GetLotListRequestDto getLotListRequestDto) {
        Page<LotEntity> lotByFilters = lotService.getListByFilters(getLotListRequestDto);
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

    @GetMapping("/{id}")
    public ResponseEntity<LotInfoDto> getLot(@PathVariable Long id) {
        LotEntity lotEntity = lotService.getFullLotInfo(id);
        BidsCountAndMaxBidDto bidCountAndMax = bidService.getBidsCountAndMaxBidByLotId(id);

        LotInfoDto lotInfo = modelMapper.map(lotEntity, LotInfoDto.class);
        lotInfo.setLastBid(bidCountAndMax.getLastBid());
        lotInfo.setTotalBids(bidCountAndMax.getTotalBids());

        return new ResponseEntity<>(lotInfo, HttpStatus.OK);
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
    public ResponseEntity<List<GetLotBidsDto>> bids(@PathVariable("id") Long lotId) throws IOException {
        List<BidEntity> bids = bidService.findAllByLotId(lotId);
        List<GetLotBidsDto> lotBidsDtos = modelMapper
                .map(bids, new TypeToken<List<GetLotBidsDto>>() {}.getType());
        return new ResponseEntity<>(lotBidsDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}/bids-stream")
    public SseEmitter bidsStream(@PathVariable("id") Long lotId) throws IOException {
        SseEmitter emitter = new SseEmitter();
        clients.add(emitter);
        emitter.onTimeout(() -> clients.remove(emitter));
        emitter.onError(throwable -> clients.remove(emitter));

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

    @Async
    @EventListener
    public void bidsChangeHandler(BidEntity bidEntity) {
        List<SseEmitter> errorEmitters = new ArrayList<>();
        clients.forEach(emitter -> {
            try {
                emitter.send(bidEntity, MediaType.APPLICATION_JSON);
            } catch (Exception e) {
                errorEmitters.add(emitter);
            }
        });
        errorEmitters.forEach(clients::remove);
    }

}
