package com.emilia.reggie.controller;

import com.emilia.reggie.common.R;
import com.emilia.reggie.model.entity.Employee;
import com.emilia.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R login(@RequestBody Employee employee, HttpSession session) {

        R<Employee> r = employeeService.login(employee);

        if (r.getCode() == 1) {
            Long employeeId = r.getData().getId();//直接用employee.getId是拿不到id数据的,数据在R里
            session.setAttribute("employeeId", employeeId);
            return r;
        }
        return r;
    }

    @PostMapping("logout")
    public R logout(HttpSession session) {
        session.invalidate();
        return R.success("logout success");
    }

    @PostMapping
    public R add(@RequestBody  Employee employee, HttpSession session) {
        //缺少字段 service:password status create_time update_time
        // controller: 设置 create_user update_user
        Long employeeId = (Long) session.getAttribute("employeeId");
        employee.setUpdateUser(employeeId);
        employee.setCreateUser(employeeId);

        employeeService.add(employee);
        return R.success(employee);
    }

    @GetMapping("page")
    public R page(  @RequestParam(defaultValue = "1") Integer page,
                  @RequestParam(defaultValue = "10") Integer pageSize,
                  String name) {
        return employeeService.findByPage(page, pageSize, name);
    }

    @PutMapping
    public R update(@RequestBody Employee employee, HttpSession session) {
        //修改者信息
        Long employeeId = (Long) session.getAttribute("employeeId");
        employee.setUpdateUser(employeeId);
        employeeService.update(employee);
        return R.success(employee);
    }

    @GetMapping("{id}")
    public R<Employee> findById(@PathVariable Long id) {
        return employeeService.findById(id);
    }

}
