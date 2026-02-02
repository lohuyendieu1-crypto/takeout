package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetMealService {
    /**
     * 新增套餐，同時保存套餐與菜品關係
     * @param setmealDTO
     */
    void saveWithDish(SetmealDTO setmealDTO);

    /**
     * 套餐分頁查詢
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 批量刪除套餐
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根據 id 查詢套餐與菜品關係
     * @param id
     * @return
     */
    SetmealVO getByIdWithDish(Long id);

    /**
     * 更新套餐信息，同時更新套餐與菜品關係
     * @param setmealDTO
     */
    void updateWithDish(SetmealDTO setmealDTO);

    /**
     * 起售或停售套餐
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);
}
