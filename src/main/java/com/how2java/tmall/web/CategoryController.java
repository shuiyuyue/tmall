package com.how2java.tmall.web;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.util.ImageUtil;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController     //此注解可以将数据转化为json格式返回
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public Page4Navigator<Category> list(@RequestParam(value = "start",defaultValue = "0") int start,@RequestParam(value = "size",defaultValue = "5") int size){
       //如果start小于0，那就让它为0
        start=start < 0 ? 0 : start;
        //Page4Navigator是封装的工具类 ，5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        Page4Navigator<Category> page = categoryService.list(start, size, 5);
        return page;
    }

    @PostMapping("/categories")
    public Object add(Category bean, MultipartFile image, HttpServletRequest request) throws IOException {
        categoryService.add(bean);
        saveOrUpdateImageFile(bean,image,request);
        return bean;
    }

    public void saveOrUpdateImageFile(Category bean, MultipartFile image, HttpServletRequest request) throws IOException {
        //上传文件的位置
        File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
        //上传文件的位置及名称
        File file = new File(imageFolder,bean.getId()+".jpg");
        //判断目录是否存在，不存在就创建
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        //image.transferTo 进行文件复制
        image.transferTo(file);
        //调用ImageUtil的change2jpg 进行文件类型强制转换为 jpg格式
        BufferedImage img= ImageUtil.change2jpg(file);
        //保存文件
        ImageIO.write(img,"jpg",file);
    }

    @DeleteMapping("/categories/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request){
        categoryService.delete(id);
        File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, id + ".jpg");
        file.delete();
        return null;
    }
    @GetMapping("/categories/{id}")
    public Category get(@PathVariable("id") int id){
        Category bean = categoryService.get(id);
        return bean;
    }
    @PutMapping("/categories/{id}")
    public Object update(Category bean,MultipartFile image,HttpServletRequest request) throws IOException {
        //Put方式的请求不能诸如参数，所以此处使用getParameter
        String name = request.getParameter("name");
        bean.setName(name);
        categoryService.update(bean);
        if (image != null) {
            saveOrUpdateImageFile(bean,image,request);
        }
        return bean;
    }
}
