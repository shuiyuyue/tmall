package com.how2java.tmall.service;

import com.how2java.tmall.dao.PropertyDao;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.util.Page4Navigator;
import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {
    @Autowired
    PropertyDao propertyDao;
    @Autowired
    CategoryService categoryService;

    //分页查询
    public Page4Navigator<Property> list(int cid, int start, int size, int navigatePages) {
        //查询对应的分类信息
        Category category = categoryService.get(cid);
        //排序方式，对应的主键
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        //查询出对应的数据
        Pageable pageable = new PageRequest(start, size, sort);

        Page<Property> pageFromJPA = propertyDao.findByCategory(category, pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    //根据分类获得所有属性集合
    public List<Property> listByCategoty(Category category) {
        return propertyDao.findByCategory(category);
    }

    //查询单个属性
    public Property get(int id) {
        return propertyDao.findOne(id);
    }

    //新增属性
    public void add(Property bean) {
        propertyDao.save(bean);
    }

    //删除属性
    public void delete(int id) {
        propertyDao.delete(id);
    }
    //修改属性
    public void update(Property bean) {
        propertyDao.save(bean);
    }

}
