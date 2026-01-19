package com.sky.service;

import com.sky.dto.CategoryDTO;

public interface CategoryService {

    /**
     * 保存菜品分類
     * @param categoryDTO
     */
    void save(CategoryDTO categoryDTO);
}
