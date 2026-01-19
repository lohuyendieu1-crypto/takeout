package com.sky.aspect;

import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

import com.sky.annotation.AutoFill;

/**
 * 自定義切面，實現公共字段自動填充邏輯
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    /**
     *
     * 切入點：對哪些類的哪些方法進行攔截。
     * @Pointcut裏面寫的是對哪些方法進行攔截，要滿足2點：
     * ①必須是mapper下的所有類的方法，②還要有AutoFill這個注解。
     */

    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut(){

    }

    /**
     * 前置通知，在通知中進行公共字段的賦值
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){
        log.info("開始進行公共字段自動填充");
        // 獲取到當前被攔截的方法上的數據庫操作類型
        MethodSignature signature = (MethodSignature)joinPoint.getSignature(); // 方法簽名對象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class); // 獲取方法上的註解對象
        /**
         * Java 在運行時，會把那張撕下來的標籤(方法上的註解)，變成一個真實存在的 Java 對象（Object）。
         * 這個對象實現了 AutoFill 接口。
         * 這個對象裡存著你在標籤上寫的數據（比如 OperationType.INSERT）。
         */
        OperationType operationType = autoFill.value(); // 獲取數據庫的操作類型



        // 獲取到當前被攔截的方法的參數--實體對象(比如傳入的參數是員工還是菜品還是其它的)
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0){
            return;
        }
        Object entity = args[0]; // 方法參數
        // 準備賦值的數據
        LocalDateTime now =  LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        // 根據當前不同的操作類型，爲對應的屬性通過反射來賦值
        if(operationType.equals(OperationType.INSERT)){
            // 為4個公共字段賦值
            try {
                // 方法名被定義成了常量類了，可以改寫
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                //通過反射爲對象屬性賦值
                setCreateTime.invoke(entity,now);
                setCreateUser.invoke(entity,currentId);
                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,currentId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }else if(operationType.equals(OperationType.UPDATE)){
            // 為2個公共字段賦值
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                //通過反射爲對象屬性賦值
                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,currentId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
