package com.emilia.reggie.controller;

import com.emilia.reggie.common.R;
import com.emilia.reggie.model.entity.Page;
import com.emilia.reggie.model.entity.SetMeal;
import com.emilia.reggie.model.vo.SetMealVo;
import com.emilia.reggie.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("setmeal")
public class SetMealController {

    @Autowired(required = false)
    private SetMealService setMealService;

    @PostMapping
    public R add(@RequestBody SetMealVo setMealVo, HttpSession session) {
        Long employeeId = (Long) session.getAttribute("employeeId");
        setMealVo.setCreateUser(employeeId);
        setMealVo.setUpdateUser(employeeId);
        setMealService.add(setMealVo);
        return R.success("添加成功");
    }

    @GetMapping("page")
    public R<Page<SetMeal>> page(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                 String name) {
        R<Page<SetMeal>> pages = setMealService.page(page, pageSize, name);
        return pages;
    }

    //批量删除 单个删除
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        setMealService.delete(ids);
        return R.success("删除成功");

    }

    //批量停售
    @PostMapping("status/{status}")
    public R<String> updateStatus(@RequestParam List<Long> ids, @PathVariable Integer status) {
        setMealService.updateStatusByIds(ids, status);
        return R.success("修改成功");
    }

    @GetMapping("list")
    public R<List<SetMeal>> list(Long categoryId, Integer status) {
        return setMealService.list(categoryId, status);
    }


}
