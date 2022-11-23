package com.emilia.reggie.model.vo;

import com.emilia.reggie.model.entity.DishFlavor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * 菜品口味
 */
@Data
@NoArgsConstructor
public class DishFlavorVo {
    //菜品id
    private Long dishId;

    //口味名称
    private String name;

    //口味数据list
    private String value;

    //是否删除
    private Integer isDeleted;

    public DishFlavorVo(DishFlavor dishFlavor){
        BeanUtils.copyProperties(dishFlavor, this);
    }

}
