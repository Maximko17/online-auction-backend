package com.example.onlineauction.controller;

import com.example.onlineauction.dto.category.CategoryEntityDto;
import com.example.onlineauction.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/root")
    public ResponseEntity<List<CategoryEntityDto>> getRootCategories() {
        List<CategoryEntityDto> categories = categoryService
                .findRootCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("{id}/tree")
    public ResponseEntity<List<CategoryEntityDto>> getCategoryTree(@PathVariable("id") Long id) {
        List<CategoryEntityDto> categories = categoryService.findTree(id);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/lot/{id}/tree")
    public ResponseEntity<List<CategoryEntityDto>> getCategoryTreeForLot(@PathVariable("id") Long lotId) {
        List<CategoryEntityDto> categories = categoryService.findAllByLotId(lotId);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

}
