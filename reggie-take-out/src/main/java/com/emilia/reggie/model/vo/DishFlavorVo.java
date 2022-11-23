package com.emilia.reggie.model.vo;

import lombok.Data;

/**
 * 菜品口味
 */
@Data
public class DishFlavorVo {
    //菜品id
    private Long dishId;

    //口味名称
    private String name;

    //口味数据list
    private String value;

    //是否删除
    private Integer isDeleted;

}
