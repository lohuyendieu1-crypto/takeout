package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 比對密碼
        // TODO 後期需要進行 md5 加密，然後再進行比對
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            // 密碼錯誤
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            // 帳號被鎖定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回實體對象
        return employee;
    }

    @Override
    public void save(EmployeeDTO employeeDTO) {
        System.out.println("當前線程的id :" + Thread.currentThread().getId());
        Employee employee = new Employee();

        // 對象屬性拷貝，dto -> entity 但屬性名稱相同的情況下才可以
        BeanUtils.copyProperties(employeeDTO, employee);

        // 設置帳號狀態，默認正常狀態1表示正常 0表示禁用
        employee.setStatus(StatusConstant.ENABLE);

        // 設置密碼，默認密碼123456，需要進行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

//        // 設置當前紀錄的創建時間和修改時間
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        // 設置創建人id和修改人id
//
//        employee.setCreateUser(BaseContext.getCurrentId());
//        employee.setUpdateUser(BaseContext.getCurrentId());

        // 調用 mapper 層，將數據插入到數據庫中
        employeeMapper.insert(employee);

    }

    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        // 開始分頁
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());

        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);

        long total = page.getTotal();
        List<Employee> employees = page.getResult();
        return new PageResult(total, employees);
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        // 在update語句中可以動態一點，不只更新狀態，還可以更新其他字段

        Employee employee = Employee.builder()
                        .status(status)
                        .id(id)
                        .updateTime(LocalDateTime.now())
                        .updateUser(BaseContext.getCurrentId())
                        .build();

        employeeMapper.update(employee);
    }

    /**
     * 根據id查詢員工信息
     * @param id
     * @return
     */
    @Override
    public Employee getById(Long id) {

         Employee employee = employeeMapper.getById(id);
         employee.setPassword("****"); // 為了安全起見，返回的密碼設置為****，不返回真實密碼
            return employee;
    }

    /**
     * 修改員工信息
     * @param employeeDTO
     */
    @Override
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.update(employee);
    }
}
