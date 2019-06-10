package com.how2java.tmall.service;

import com.how2java.tmall.dao.ProductDAO;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductDAO productDAO;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductImageService productImageService;

    public void add(Product bean) {
        productDAO.save(bean);
    }

    public void delete(int id) {
        productDAO.delete(id);
    }

    public Product get(int id) {
        Product one = productDAO.findOne(id);
        return one;
    }

    public void update(Product bean) {
        productDAO.save(bean);
    }

    //根据分类查询所有商品并分页
    public Page4Navigator<Product> list(int cid, int start, int size, int navigatePages) {
        Category category = categoryService.get(cid);
        Sort sort = new Sort(Sort.Direction.DESC, "id");

        Pageable pageable = new PageRequest(start, size, sort);

        Page<Product> pageFromJPA = productDAO.findByCategory(category, pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    //为多个分类填充产品集合
    public void fill(List<Category> categorys) {
        for (Category category : categorys) {
            fill(category);
        }
    }

    //为分类填充产品集合
    public void fill(Category category) {
        List<Product> products = listByCategory(category);
        productImageService.setFirstProductImages(products);
        category.setProducts(products);
    }

    //为多个分类填充推荐产品集合，即把分类下的产品集合，安装按照8个为一行，拆成多行，以便于后续在页面上使用
    public void fillByRow(List<Category> categories) {
        //每行的商品为8个
        int productNumberEachRow = 8;


        for (Category category:categories) {
            //查询分类下的所有商品
            List<Product> products = category.getProducts();

            //创建集合
            List<List<Product>> productByRow = new ArrayList<>();
            //
            for(int i= 0;i<products.size();i+=productNumberEachRow) {
                int size = i+productNumberEachRow;
                size = size > products.size() ? products.size():size;
                List<Product> productsOfEachRow = products.subList(i, size);
                productByRow.add(productsOfEachRow);
            }
        }
    }


    //查询某个分类下的所有产品
    private List<Product> listByCategory(Category category) {
        return productDAO.findByCategoryOrderById(category);
    }


}
