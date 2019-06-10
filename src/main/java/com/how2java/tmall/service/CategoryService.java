package com.how2java.tmall.service;

import com.how2java.tmall.dao.CategoryDAO;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryDAO categoryDAO;

    public Page4Navigator<Category> list(int start,int size,int navigatePages){
        //Sort对象用来指示排序,传入一个属性名,默认采用升序排序
        Sort sort = new Sort(Sort.Direction.DESC, "id");

        //Pageable 接口用于构造翻页查询，PageRequest 是其实现类
        Pageable pageable = new PageRequest(start,size,sort);

        //Page接口可以获取当前页面的记录,总页数,总记录数,是否有上一页或下一页等
        Page<Category> pageFromJPA = categoryDAO.findAll(pageable);

        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }


    public List<Category> list(){
        Sort sort  = new Sort(Sort.Direction.DESC,"id");//创建一个排序对象，查询的时候根据这个对象排序
        return categoryDAO.findAll(sort);
    }

    public void add(Category bean){
        categoryDAO.save(bean);
    }

    public void delete(int id){
        categoryDAO.delete(id);
    }

    public Category get(int id){
        Category c = categoryDAO.findOne(id);
        return c;
    }

    public void update(Category bean){
        categoryDAO.save(bean);
    }

    public void removeCategoryFromProduct(List<Category> cs) {
        for (Category category : cs) {
            removeCategoryFromProduct(category);
        }
    }

    //删除Product对象上的分类信息
    public void removeCategoryFromProduct(Category category) {
        //获取该分类下的所有产品
        List<Product> products = category.getProducts();
        if (products != null) {
            for (Product product:products) {
                product.setCategory(null);
            }
        }

        List<List<Product>> productsByRow = category.getProductsByRow();
        if (productsByRow != null) {
            for (List<Product> ps: productsByRow) {
                for (Product product:ps) {
                    product.setCategory(null);
                }
            }
        }
    }


}






