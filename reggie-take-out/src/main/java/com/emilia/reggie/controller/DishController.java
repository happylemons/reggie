package com.emilia.reggie.controller;

import com.emilia.reggie.common.R;
import com.emilia.reggie.model.entity.Page;
import com.emilia.reggie.model.vo.DishVo;
import com.emilia.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("dish")
public class DishController {

    @Autowired(required = false)
    private DishService dishService;

    @PostMapping
    public R addDish(@RequestBody DishVo dishVo, HttpSession session) {
        Long employeeId = (Long) session.getAttribute("employeeId");
        dishVo.setCreateUser(employeeId);
        dishVo.setUpdateUser(employeeId);
        dishService.addDish(dishVo);
        return R.success("添加成功");
    }

    @GetMapping("page")
    public R<Page<DishVo>> page(@RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                String name) {
        return dishService.findByPage(page, pageSize, name);
    }


    @GetMapping("{id}")
    public R<DishVo> findById(@PathVariable Long id) {
        return dishService.findById(id);
    }

    @PutMapping
    public R updateDish(@RequestBody DishVo dishVo, HttpSession session){

        Long empId = (Long) session.getAttribute("employee");

        //2. 补全更新人
        dishVo.setUpdateUser(empId);
        //3.交给service保存信息
        dishService.update(dishVo);
        //4. 返回结果
        return R.success("更新菜品成功");
    }
}
