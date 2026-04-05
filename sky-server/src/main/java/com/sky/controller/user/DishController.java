package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "C端-菜品瀏覽接口")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate  redisTemplate;

    /**
     * 根據分類 id 查詢菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根據分類 id 查詢菜品")
    public Result<List<DishVO>> list(Long categoryId) {

        // 構造 redis 中的 key，規則 dish_分類id
        String key = "dish_" + categoryId;

        // 查詢 redis 中是否存在商品數據
        List<DishVO> list = (List<DishVO>)redisTemplate.opsForValue().get(key); // 放進 redis 是甚麼類型，取出就是甚麼類型

        if(list != null && list.size()>0){
            // 如果存在，直接返回，無須查詢數據庫
            return Result.success(list);
        }


        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE); // 查詢起售中的菜品

        // 如果不存在，則查詢數據庫，並將數據存入 redis
        list = dishService.listWithFlavor(dish);
        redisTemplate.opsForValue().set(key, list);

        return Result.success(list);
    }

}
