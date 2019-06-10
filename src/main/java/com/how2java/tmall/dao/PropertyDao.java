package com.how2java.tmall.dao;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.pojo.PropertyValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyDao extends JpaRepository<Property,Integer>{
    //根据分类进行查询
    Page<Property> findByCategory(Category category, Pageable pageable);

    //根据分类查询所有属性值
    List<Property> findByCategory(Category category);
}
