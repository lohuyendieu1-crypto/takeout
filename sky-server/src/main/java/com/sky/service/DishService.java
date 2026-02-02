package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    /**
     * 新增菜品，同時保存口味數據
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分頁查詢
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 批量刪除菜品
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根據 id 查詢菜品及其口味信息
     * @param id
     * @return
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * 更新菜品信息及其口味信息
     * @param dishDTO
     */
    void updateWithFlavor(DishDTO dishDTO);

    /**
     * 根據分類 id 查詢商品
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);

    /**
     * 起售或停售菜品
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);
}
