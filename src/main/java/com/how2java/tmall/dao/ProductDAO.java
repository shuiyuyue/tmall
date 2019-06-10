package com.how2java.tmall.dao;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product,Integer> {
    //根据分类查询产品
    Page<Product> findByCategory(Category category, Pageable pageable);

    //根据分类查询所有商品信息，不需要分页
    List<Product> findByCategoryOrderById(Category category);
    //根据名称模糊查询
    List<Product> findByNameLike(String keyword, Pageable pageable);
}
