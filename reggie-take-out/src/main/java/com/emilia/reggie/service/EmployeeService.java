package com.emilia.reggie.service;

import com.emilia.reggie.common.R;
import com.emilia.reggie.model.entity.Employee;
import com.emilia.reggie.model.entity.Page;

public interface EmployeeService {
    R<Employee> login(Employee employee);

    Employee add(Employee employee);

    R<Page> findByPage(Integer page, Integer pageSize, String name);

    void update(Employee employee);

    R<Employee> findById(Long id);
}
