package com.how2java.tmall.service;

import com.how2java.tmall.dao.UserDao;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public boolean isExist(String name) {
        User user = getByName(name);
        return null != user;
    }

    public User getByName(String name) {
        return userDao.findByName(name);
    }

    public Page4Navigator<User> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFormJPA = userDao.findAll(pageable);
        return new Page4Navigator<>(pageFormJPA, navigatePages);
    }

    public void add(User user) {
        userDao.save(user);
    }

}