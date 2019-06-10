package com.how2java.tmall.service;

import com.how2java.tmall.dao.PropertyValueDAO;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.pojo.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueServic {

    @Autowired
    PropertyValueDAO propertyValueDAO;
    @Autowired
    PropertyService propertyService;

    //修改属性值
    public void update(PropertyValue bean) {
        propertyValueDAO.save(bean);
    }

    public void init(Product product) {
        //根据传入一个产品，获得这个产品对应的分类id，用分类id去查对应的所有属性
        List<Property> propertys = propertyService.listByCategoty(product.getCategory());

        for (Property property : propertys) {
            //根据产品和属性查询一个属性值
            PropertyValue propertyValue = propertyValueDAO.getByPropertyAndProduct(property,product);
            if (propertyValue == null) {
                //如果不存在就创建一个，并设置它的属性值，对应的商品id和属性id
                propertyValue = new PropertyValue();
                propertyValue.setProduct(product);
                propertyValue.setProperty(property);
                propertyValueDAO.save(propertyValue);
            }
        }
    }

    //根据属性和产品查询一个属性值
    public PropertyValue getByPropertyAndProduct(Product product, Property property) {
        return propertyValueDAO.getByPropertyAndProduct(property, product);
    }

    //根据产品查询属性值
    public List<PropertyValue> list(Product product) {
        return propertyValueDAO.findByProductOrderByIdDesc(product);
    }
}
