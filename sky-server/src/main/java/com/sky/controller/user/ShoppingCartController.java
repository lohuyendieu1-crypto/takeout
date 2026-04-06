package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Api(tags = "C端-購物車接口")
@Slf4j
public class ShoppingCartController {


    @Autowired
    private ShoppingCartService  shoppingCartService;


    @PostMapping("/add")
    @ApiOperation("添加菜品或套餐到購物車")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加菜品或套餐到購物車:{}", shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("查看購物車")
    public Result<List<ShoppingCart>> list() {

        List<ShoppingCart> list = shoppingCartService.showShoppintCart();
        return Result.success(list);
    }


    @DeleteMapping("/clean")
    @ApiOperation("清空購物車")
    public Result clean(){
        shoppingCartService.cheanShoppingCart();
        return Result.success();
    }

    @PostMapping("/sub")
    @ApiOperation("減少購物車中菜品或套餐的數量")
    public Result sub(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("減少購物車中菜品或套餐的數量:{}", shoppingCartDTO);
        shoppingCartService.subShoppingCart(shoppingCartDTO);
        return Result.success();

    }
}
