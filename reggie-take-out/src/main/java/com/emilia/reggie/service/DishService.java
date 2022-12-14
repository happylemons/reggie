package com.emilia.reggie.service;

import com.emilia.reggie.common.R;
import com.emilia.reggie.model.entity.Page;
import com.emilia.reggie.model.vo.DishVo;

import java.util.List;

public interface DishService {

    void addDish(DishVo dishVo);

    R<Page<DishVo>> findByPage(Integer page, Integer pageSize, String name);

    R<DishVo> findById(Long id);

    void update(DishVo dishVo);

    R<List<DishVo>> findByCategoryId(Long categoryId, Integer status);

    void delete(List<Long> ids);

    void updateStatusByIds(List<Long> ids, Integer status);
}
