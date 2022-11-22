package com.emilia.reggie.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {

    protected List<T> records;
    protected Long total;
    protected Integer pageSize;
    protected Integer page;

}
