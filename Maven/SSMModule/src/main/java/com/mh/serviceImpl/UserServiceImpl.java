package com.mh.serviceImpl;

import com.mh.mapper.UserMapper;
import com.mh.pojo.User;
import com.mh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> show() {
        List<User> list = userMapper.selAll();
        return list;
    }
}
