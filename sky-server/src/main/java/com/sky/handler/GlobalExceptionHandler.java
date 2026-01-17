package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕獲業務異常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("異常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result exceptionHandler(SQLIntegrityConstraintViolationException exception){
        // Duplicate entry 'zhangsan' for key 'employee.idx_username'
        String message = exception.getMessage();
        if(message.contains("Duplicate entry")){
            // 提取用戶名，根據空格進行切割
            String[] split = message.split(" ");
            String info = split[2] + MessageConstant.ALREADY_EXISTS; // 下標 2 對應用戶名
            return Result.error(info);
        }else{
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }


}
