package com.yuu.recruit.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "employee")
public class Employee implements Serializable {
    /**
     * 雇员ID
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 登录密码
     */
    @Column(name = "`password`")
    private String password;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 手机号
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 头像地址
     */
    @Column(name = "head_img")
    private String headImg;

    /**
     * 标语
     */
    @Column(name = "tagline")
    private String tagline;

    /**
     * 个人简介
     */
    @Column(name = "profile")
    private String profile;

    /**
     * 主页被浏览次数
     */
    @Column(name = "browse_count")
    private Integer browseCount;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
