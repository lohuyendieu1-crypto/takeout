package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/category")
@Api(tags = "分類管理相關接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @ApiOperation("保存菜品分類")
    public Result sava(@RequestBody CategoryDTO categoryDTO){
      log.info("保存分類:{}",categoryDTO);
      categoryService.save(categoryDTO);
      return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("分類分頁查詢")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("菜品分類分頁查詢{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }


    @DeleteMapping
    @ApiOperation("根據id刪除分類")
    public Result deleteById(Long id){
        log.info("根據id{}刪除分類",id);
        categoryService.deleteById(id);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("修改分類")
    public Result update(@RequestBody CategoryDTO categoryDTO){
        log.info("修改分類{}",categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status,Long id){
        log.info("啟用或禁用分類:{},{}",status,id);
        categoryService.startOrStop(status,id);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("根據類型查詢分類")
    public Result<List<Category>> list(@RequestParam Integer type){
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }

}
