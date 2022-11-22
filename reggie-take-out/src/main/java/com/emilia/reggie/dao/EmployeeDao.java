package com.emilia.reggie.dao;

import com.emilia.reggie.model.entity.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeDao {
    Employee findUsername(Employee employee);

    void add(Employee employee);

    List<Employee> findByPage(@Param("name") String name);

    void update(Employee employee);

    Employee findById(Long id);
}
