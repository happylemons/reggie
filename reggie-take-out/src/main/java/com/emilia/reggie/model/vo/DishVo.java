package com.emilia.reggie.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.emilia.reggie.model.entity.Dish;
import com.emilia.reggie.model.entity.DishFlavor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishVo {

    //浏览器传入的参数:
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

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    //前端传入的菜品口味
    private List<DishFlavorVo> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;

    public DishVo(Dish dish, List<DishFlavor> flavorList){
        BeanUtils.copyProperties(dish, this);
        flavorList.stream()
                .map(DishFlavorVo::new)
                .forEach(flavors::add);
    }
}
