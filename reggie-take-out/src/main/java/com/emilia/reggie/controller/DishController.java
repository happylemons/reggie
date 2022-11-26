package com.emilia.reggie.controller;

import com.emilia.reggie.common.R;
import com.emilia.reggie.model.entity.Page;
import com.emilia.reggie.model.vo.DishVo;
import com.emilia.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    public R updateDish(@RequestBody DishVo dishVo, HttpSession session) {

        Long employeeId = (Long) session.getAttribute("employeeId");
        //2. 补全
        dishVo.setUpdateUser(employeeId);

        dishService.update(dishVo);
        //4. 返回结果
        return R.success("修改成功");
    }

    @GetMapping("list")
    public R<List<DishVo>> list(Long categoryId, Integer status) {
        return dishService.findByCategoryId(categoryId,status);
    }


    //批量停售
    @PostMapping("status/{status}")
    public R<String> updateStatus(@RequestParam List<Long> ids, @PathVariable Integer status) {
        dishService.updateStatusByIds(ids, status);
        return R.success("修改成功");
    }
    //批量删除 单个删除
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        dishService.delete(ids);
        return R.success("删除成功");

    }


}
