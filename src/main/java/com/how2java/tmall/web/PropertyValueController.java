package com.how2java.tmall.web;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.PropertyValueServic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PropertyValueController {
    @Autowired
    PropertyValueServic propertyValueServic;

    @Autowired
    ProductService productService;

    @GetMapping("/products/{pid｝propertyValues")
    public List<PropertyValue> list(@PathVariable("pid") int pid) {
        Product product = productService.get(pid);
        propertyValueServic.init(product);
        List<PropertyValue> propertyValues = propertyValueServic.list(product);
        return propertyValues;
    }

    @PutMapping("/propertyValues")
    public Object update(PropertyValue bean) {
        propertyValueServic.update(bean);
        return bean;
    }
}
