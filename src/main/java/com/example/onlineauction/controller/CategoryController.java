package com.example.onlineauction.controller;

import com.example.onlineauction.dto.bid.BidsCountAndMaxBidDto;
import com.example.onlineauction.dto.category.CategoryEntityDto;
import com.example.onlineauction.dto.lot.LotInfoDto;
import com.example.onlineauction.entity.CategoryEntity;
import com.example.onlineauction.entity.LotEntity;
import com.example.onlineauction.service.CategoryService;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
//
//    @GetMapping("/root")
//    public ResponseEntity<List<CategoryEntityDto>> getRootCategories() {
//        List<CategoryEntityDto> allRoot = categoryService.findAllRoot();
//        return new ResponseEntity<>(allRoot, HttpStatus.OK);
//    }
//
//    @GetMapping("/parent/{id}")
//    public ResponseEntity<List<CategoryEntityDto>> getChildCategories(@PathVariable("id") Long id) {
//        List<CategoryEntityDto> allRoot = categoryService.findAllByParentId(id);
//        return new ResponseEntity<>(allRoot, HttpStatus.OK);
//    }

    @GetMapping("/tree")
    public ResponseEntity<List<CategoryEntityDto>> categoriesTree(@PathParam("startDepth") Integer startDepth,
                                                                  @PathParam("endDepth") Integer endDepth) {
        List<CategoryEntityDto> categories = categoryService
                .findAllBetweenDepths(startDepth, endDepth);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/lot/{id}/tree")
    public ResponseEntity<List<CategoryEntityDto>> categoriesTree(@PathVariable("id") Long lotId) {
        List<CategoryEntityDto> categories = categoryService.findAllByLotId(lotId);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

}
