package com.emilia.reggie.service;

import com.emilia.reggie.common.R;
import com.emilia.reggie.model.entity.Page;
import com.emilia.reggie.model.entity.SetMeal;
import com.emilia.reggie.model.vo.SetMealVo;

import java.util.List;

public interface SetMealService {
    void add(SetMealVo setMealVo);


    R<Page<SetMeal>> page(Integer page, Integer pageSize, String name);

    void delete(List<Long> ids);

    void updateStatusByIds(List<Long> ids, Integer status);
}
