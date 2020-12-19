package com.iemij.test.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.Id;
import javax.persistence.Table;

@Table( name = "test")
@ApiModel(value="测试对象模型")
public class Test {
    @Id
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
