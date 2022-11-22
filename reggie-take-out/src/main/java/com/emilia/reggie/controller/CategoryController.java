package com.emilia.reggie.controller;

import com.emilia.reggie.common.R;
import com.emilia.reggie.model.entity.Category;
import com.emilia.reggie.model.entity.Page;
import com.emilia.reggie.service.CategoryService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired(required = false)
    private CategoryService categoryService;

    //新增菜品分类
    @PostMapping
    public R add(@RequestBody Category category, HttpSession session) {

        Long employeeId = (Long) session.getAttribute("employeeId");
        category.setCreateUser(employeeId);
        category.setUpdateUser(employeeId);
        categoryService.add(category);

        return R.success("添加成功");
    }

    //list分页功能
    @GetMapping("page")
    public R page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {
        R<Page> pages = categoryService.page(page, pageSize);
        return pages;
    }

    //删除功能
    @DeleteMapping
    public R delete(Long id) {
        categoryService.delete(id);
        return R.success("删除成功");
    }

    //修改功能
    @PutMapping
    public R update(@RequestBody Category category, HttpSession session) {
        Long employeeId = (Long) session.getAttribute("employeeId");
        category.setUpdateUser(employeeId);

        categoryService.update(category);
        return R.success("更新成功");
    }

    //下载文件
    @GetMapping("exportExcel")
    public void exportExcel(HttpServletResponse response) throws IOException {

        //excel表数据
        List<Category> categoryList = categoryService.findAll();

        //0.下载-->通知浏览器处理文件内荣以文件下载的形式处理
        //disposition:处置  attachment:附属物
        response.setHeader("content-disposition", "attachment;filename=category.xlsx");
        //1.读取模板
        InputStream read = this.getClass().getResourceAsStream("/excel/category.xlsx");

        //2.workbook工作簿
        Workbook workbook = new XSSFWorkbook(read);
        Sheet sheet = workbook.getSheetAt(0);

        List<CellStyle> cellStyles = new ArrayList<>();//样式
        Row row = sheet.getRow(1);//第一行

        for (int i = 1; i < 6; i++) {
            CellStyle cellStyle = row.getCell(i).getCellStyle();
            cellStyles.add(cellStyle);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


        for (int i = 0; i < categoryList.size(); i++) {
            Category category = categoryList.get(i);
            row = sheet.createRow(i + 1);

            Cell cell = row.createCell(1);
            cell.setCellValue(category.getType());
            cell.setCellStyle(cellStyles.get(0));


            cell = row.createCell(2);
            cell.setCellValue(category.getName());
            cell.setCellStyle(cellStyles.get(1));

            cell = row.createCell(3);
            cell.setCellValue(category.getSort());
            cell.setCellStyle(cellStyles.get(2));

            cell = row.createCell(4);
            String createTime = formatter.format(category.getCreateTime());
            cell.setCellValue(createTime);
            cell.setCellStyle(cellStyles.get(3));

            cell = row.createCell(5);
            String updateTime = formatter.format(category.getUpdateTime());
            cell.setCellValue(updateTime);
            cell.setCellStyle(cellStyles.get(4));
        }

        //输出
        workbook.write(response.getOutputStream());

    }

    @GetMapping("list")
    public R<List<Category>> list(Integer type) {

        return categoryService.list(type);
    }
}
