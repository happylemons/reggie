package com.emilia.reggie.model.dto;

import com.emilia.reggie.model.vo.DishFlavorVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishDto {

    //后端传给前端:
    private Long id;
    //菜品名称
    private String name;

    //菜品分类id
    private Long categoryId;

    //菜品价格
    private BigDecimal price;

    //商品码
    private String code;

    //图片
    private String image;

    //描述信息
    private String description;


    //前端传入的菜品口味
    private List<DishFlavorVo> flavors = new ArrayList<>();


}
