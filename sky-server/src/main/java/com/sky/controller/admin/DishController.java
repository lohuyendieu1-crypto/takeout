package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("admin/dish")
@Api(tags = "菜品管理相關接口")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;


    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品:{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);

        // 清理緩存數據
        String key = "dish_" + dishDTO.getCategoryId(); // 不用清所有 redis key 清理有被修改的就好
        cleanCache(key);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("菜品分頁查詢")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分頁查詢:{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("批量刪除菜品")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("批量刪除菜品:{}", ids);
        dishService.deleteBatch(ids);

        // 有可能刪除的菜品屬於不同分類，所以需要清理多個 key
        // 但判斷刪除的菜品屬於哪個分類，效率不高，所以直接清理所有菜品相關的 key
        // 以 dish_ 開頭的 key 都是菜品相關的 key
        cleanCache("dish_*");

        return Result.success();
    }

    /**
     * 根據id查詢菜品信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根據id查詢菜品信息")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("根據id查詢菜品信息：{}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    /**
     * 修改菜品
     * @param dishDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改菜品")
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品:{}", dishDTO);
        dishService.updateWithFlavor(dishDTO);

        // 修改菜品可能會轉換分類，影響到兩個分類的緩存
        // 但判斷麻煩就直接刪除全部
        cleanCache("dish_*");


        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("根據分類id查詢菜品")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    /**
     * 菜品起售停售
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    public Result<String> startOrStop(@PathVariable Integer status, Long id) {
        dishService.startOrStop(status, id);

        cleanCache("dish_*");

        return Result.success();
    }

    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }


}
