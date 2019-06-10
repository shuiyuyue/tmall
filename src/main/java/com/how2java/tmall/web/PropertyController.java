package com.how2java.tmall.web;

import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PropertyController {
    @Autowired
    PropertyService propertyService;

    @GetMapping("/categories/{cid}/properties")//PathVariable可以将请求路径中的值入参
    public Page4Navigator<Property> list(@PathVariable("cid") int cid, @RequestParam(value = "start",defaultValue = "0") int start, @RequestParam(value = "size",defaultValue = "5") int size){
        start = start < 0 ? 0:start;
        Page4Navigator<Property> page = propertyService.list(cid, start, size, 5);
        return page;
    }

    @GetMapping("/properties/{id}")
    public Property get(@PathVariable("id") int id){
        Property property = propertyService.get(id);
        return property;
    }

    @PostMapping("/properties")
    public Object add(@RequestBody Property bean){
        propertyService.add(bean);
        return bean;
    }
    @DeleteMapping("/properties/{id}")
    public String delete(@PathVariable("id")int id){
        propertyService.delete(id);
        return null;
    }
    @PutMapping("/properties")
    public Object update(@RequestBody Property bean){
        propertyService.update(bean);
        return bean;
    }
}
