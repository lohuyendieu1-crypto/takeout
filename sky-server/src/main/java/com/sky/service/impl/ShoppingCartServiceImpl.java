package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {


    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper  setmealMapper;

    /**
     * 添加購物車
     * @param shoppingCartDTO
     */
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {

        // 判斷當前加入到購物車中的商品是否已經存在了
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        Long userId = BaseContext.getCurrentId(); // 從攔截器攔到的token中解析出用戶的 id
        shoppingCart.setUserId(userId);

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);


        // 如果已經存在了，則在原來的數量上加一(update操作)
        if (list != null && list.size() > 0){
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1); // 數據庫 update 數量
            shoppingCartMapper.updateNumberById(cart);
        }else{
            // 如果不存在，則新增一條數據，數量默認就是一
            // 一些菜品套餐口味基礎信息要在原本各自 eitity 上補全 (名字 價格 圖片等)，dto 裡面沒有

            // 判斷本次添加到購物車的是商品還是套餐
            Long dishId = shoppingCartDTO.getDishId();
            if(dishId != null){
                // 本次添加到購物車的是菜品
                Dish dish = dishMapper.getById(dishId);
                shoppingCart.setName(dish.getName()); // 直接用上面的 shoppingCart 因為等等要插入到數據庫的 shoppingCart 就是它了
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());


            }else{
                // 本次添加到購物車的是套餐
                Long setmealId = shoppingCartDTO.getSetmealId();
                Setmeal setmeal = setmealMapper.getById(setmealId);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());

            }
            shoppingCart.setNumber(1); // 設置數量默認為 1
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }
    }

    @Override
    public List<ShoppingCart> showShoppintCart() {
        Long userId = BaseContext.getCurrentId(); // 獲取到當前微信用戶的 id
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userId)
                .build();
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        return list;
    }

    @Override
    public void cheanShoppingCart() {
        Long userId = BaseContext.getCurrentId(); // 獲取到當前微信用戶的 id
        shoppingCartMapper.deleteByUserId(userId);
    }

    /**
     * 删除購物車中一個商品
     * @param shoppingCartDTO
     */
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        // 設置查詢條件，查詢當前登錄用戶的購物車數據
        shoppingCart.setUserId(BaseContext.getCurrentId());

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        if(list != null && list.size() > 0){
            shoppingCart = list.get(0);

            Integer number = shoppingCart.getNumber();
            if(number == 1){
                //當前商品在購物車中的份數爲1，直接删除當前記錄
                shoppingCartMapper.deleteById(shoppingCart.getId());
            }else {
                //當前商品在購物車中的份數不爲1，修改份數即可
                shoppingCart.setNumber(shoppingCart.getNumber() - 1);
                shoppingCartMapper.updateNumberById(shoppingCart);
            }
        }
    }
}
