package com.emilia.reggie;

import com.emilia.reggie.dao.EmployeeDao;
import com.emilia.reggie.model.entity.Employee;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PageHelperTest {

    @Autowired(required = false)
    private EmployeeDao employeeDao;

    @Test
    public void testPage(){
        PageHelper.startPage(1,2);

        List<Employee> employeeList = employeeDao.findByPage(null);

        PageInfo<Employee> pageInfo = new PageInfo<>(employeeList);

        System.out.println("当前页："+pageInfo.getPageNum());
        System.out.println("页面大小："+pageInfo.getPageSize());
        System.out.println("总页数："+pageInfo.getPages());
        System.out.println("总记录数："+pageInfo.getTotal());
        System.out.println("当前页的数据："+pageInfo.getList());


    }
}