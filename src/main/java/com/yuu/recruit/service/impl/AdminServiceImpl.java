package com.yuu.recruit.service.impl;

import com.yuu.recruit.domain.Admin;
import com.yuu.recruit.service.AdminService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.yuu.recruit.mapper.AdminMapper;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

/**
 * 管理员的业务逻辑实现
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Override
    public Admin login(Admin admin) {
        // 查询出用户名的管理员
        // 使用 tk.mybatis 进行查询，不需要编写 xml 文件
        Example example = new Example(Admin.class);
        // 构造查询条件, 根据用户名查询
        example.createCriteria().andEqualTo("username", admin.getUsername());
        // 使用 adminMapper 查询
        Admin currAdmin = adminMapper.selectOneByExample(example);

        // 判断密码是否正确, 密码采用 MD5 加密，所以需要把页面传过来的密码加密后进行比较
        if (currAdmin != null && currAdmin.getPassword().equals(DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()))) {
            // 密码相同登录成功
            return currAdmin;
        }

        // 密码不相同，登录失败,返回一个 null 值
        else {
            return null;
        }
    }
}
