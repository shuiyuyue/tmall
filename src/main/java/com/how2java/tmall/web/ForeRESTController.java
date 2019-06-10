package com.how2java.tmall.web;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.UserService;
import com.how2java.tmall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@RestController
public class ForeRESTController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;

    @GetMapping("/forehome")
    public Object home() {
        //查询所有分类
        List<Category> cs = categoryService.list();
        //为分类填充商品
        productService.fill(cs);
        //填充推荐商品
        productService.fillByRow(cs);
        //删除Product对象上的分类信息
        categoryService.removeCategoryFromProduct(cs);
        return cs;
    }

    @PostMapping
    public Object register(@RequestBody User user) {
        String name = user.getName();
        String password = user.getPassword();
        //转义用户名，防止恶意注册的用户名
        name = HtmlUtils.htmlEscape(name);

        user.setName(name);
        Boolean exist = userService.isExist(name);

        if (exist) {
            String message = "用户名已经被注册，再想个吧";
            return Result.fail(message);
        }
        user.setPassword(password);
        userService.add(user);
        return Result.success();
    }
}
