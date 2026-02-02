package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Service
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private DishMapper dishMapper;



    @Override
    @Transactional
    public void saveWithDish(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        // 向套餐表插入一條數據
        setmealMapper.insert(setmeal);

        Long id = setmeal.getId();

        // 保存套餐與菜品關係
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();

       if(setmealDishes != null && setmealDishes.size() > 0) {
           // 設置套餐菜品關係的套餐 id
           setmealDishes.forEach(
                   setmealDish ->  setmealDish.setSetmealId(setmeal.getId()));
           // 向套餐菜品關係表插入多條數據
           setmealDishMapper.insertBatch(setmealDishes);
       }
    }

    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {

        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());

        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);


        return new PageResult(page.getTotal(), page.getResult());
    }


    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {

        ids.forEach(id -> {
            Setmeal setmeal = setmealMapper.getById(id);
            if(setmeal != null) {
                // 不能刪除起售中的套餐
                if(StatusConstant.ENABLE.equals(setmeal.getStatus())) {
                    throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
                }
            }
        });

        // 刪除套餐表中的數據
        setmealMapper.deleteByIds(ids);

        // 刪除套餐與菜品中間表中的數據
        setmealDishMapper.deleteBySetmealIds(ids);
    }

    /**
     * 根據 id 查詢套餐與菜品關係
     * @param id
     * @return
     */
    @Override
    public SetmealVO getByIdWithDish(Long id) {
        // 根據 id 查詢套餐
        Setmeal setmeal = setmealMapper.getById(id);
        // 根據套餐 id 查詢套餐與菜品關係數據
        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);

        // 封裝返回結果
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    /**
     * 更新套餐信息，同時更新套餐與菜品關係
     * @param setmealDTO
     */
    @Override
    @Transactional
    public void updateWithDish(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        // 1、修改套餐表，執行update
        setmealMapper.update(setmeal);

        // 套餐 id 轉 List 。删除套餐和菜品的關聯關係，操作setmeal_dish表，執行delete
        setmealDishMapper.deleteBySetmealIds(Arrays.asList(setmealDTO.getId()));

        // 傳進來的新菜品進行更新
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && setmealDishes.size() > 0) {
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(setmealDTO.getId());
            });
            // 重新插入套餐和菜品的關聯關係，操作setmeal_dish表
            setmealDishMapper.insertBatch(setmealDishes);
        }
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        // 起售套餐時，判斷套餐內是否有停售菜品，有停售菜品提示"套餐內包含未啓售菜品，無法啓售"
        // 用dishMapper來查，因為是菜品表裡面才有菜品狀態
        if(status == StatusConstant.ENABLE) {
            List<Dish> dishList = dishMapper.getBySetmealId(id);
            if(dishList != null && dishList.size() > 0) {
                dishList.forEach(dish -> {
                    if(dish.getStatus() == StatusConstant.DISABLE) {
                        throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                    }
                });
            }

        }

        Setmeal setmeal = Setmeal.builder()
                .id(id)
                .status(status)
                .build();
        setmealMapper.update(setmeal);

    }
}
