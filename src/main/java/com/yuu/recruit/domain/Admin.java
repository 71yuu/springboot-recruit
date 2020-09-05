package com.yuu.recruit.domain;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "`admin`")
public class Admin implements Serializable {
    /**
     * 管理员ID
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
     * 密码
     */
    @Column(name = "`password`")
    private String password;

    private static final long serialVersionUID = 1L;
}
