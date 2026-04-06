package com.sky.service;


import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    /**
     * 添加購物車
     * @param shoppingCartDTO
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 添加購物車
     * @return
     */
    List<ShoppingCart> showShoppintCart();

    /**
     * 清空購物車
     */
    void cheanShoppingCart();

    /**
     * 減少購物車中菜品或套餐的數量
     * @param shoppingCartDTO
     */
    void subShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
