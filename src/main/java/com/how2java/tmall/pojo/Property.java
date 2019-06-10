package com.how2java.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity //表示这是一个实体类
@Table(name = "property")   //对应数据库的表
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //表示主键，Jpa要求每个类必须有一个主键
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    //属性表与分类表的关系是多对一，此处注解
    @ManyToOne
    @JoinColumn(name = "cid")
    private Category category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                '}';
    }
}
