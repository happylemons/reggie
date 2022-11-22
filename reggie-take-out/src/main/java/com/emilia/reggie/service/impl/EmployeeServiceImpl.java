package com.emilia.reggie.service.impl;

import com.emilia.reggie.common.R;
import com.emilia.reggie.dao.EmployeeDao;
import com.emilia.reggie.model.entity.Employee;
import com.emilia.reggie.model.entity.Page;
import com.emilia.reggie.exception.NameExistsException;
import com.emilia.reggie.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired(required = false)
    private EmployeeDao employeeDao;


    @Override
    public R<Employee> login(Employee employee) {
        Employee findEmp = employeeDao.findUsername(employee);

        if (findEmp == null) {
            return R.error("用户不存在");
        }
        String password = employee.getPassword();
        String md5Hex = DigestUtils.md5Hex(password);
        System.out.println(md5Hex);
        if (!md5Hex.equals(findEmp.getPassword())) {
            return R.error("用户或者密码错误");
        }

        return R.success(findEmp);
    }

    @Override
    public Employee add(Employee employee) {
        //username->unique
        Employee findEmp = employeeDao.findUsername(employee);
        if (findEmp != null) {
            throw new NameExistsException(employee.getUsername() + " 已经存在,请勿重复添加");
        }

        //缺少字段:password status create_time update_time
        //1.设置md5加密的密码
        String md5 = DigestUtils.md5Hex("123456");
        employee.setPassword(md5);
        employee.setStatus(1);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employeeDao.add(employee);
        return employee;
    }

    @Override
    public R<Page> findByPage(Integer page, Integer pageSize, String name) {

        PageHelper.startPage(page, pageSize);

        List<Employee> employeeList = employeeDao.findByPage(name);

        PageInfo<Employee> pageInfo = new PageInfo<>(employeeList);
        Page<Employee> pages = new Page<>(pageInfo.getList(), pageInfo.getTotal(), pageSize, page);
        return R.success(pages);
    }

    @Override
    public void update(Employee employee) {
        employee.setUpdateTime(LocalDateTime.now());
        employeeDao.update(employee);
    }

    @Override
    public R<Employee> findById(Long id) {
        Employee employee = employeeDao.findById(id);
        return R.success(employee);
    }
}
