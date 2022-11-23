package com.emilia.reggie.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.emilia.reggie.model.entity.SetMealDish;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 套餐
 */
@Data
public class SetMealVo {


    //套餐分类
    private String categoryId;

    //套餐名称
    private String name;

    //套餐价格
    private BigDecimal price;

    private List<SetMealDish> setmealDishes;

    private String categoryName;

    //描述信息
    private String description;

    //图片
    private String image;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

}
