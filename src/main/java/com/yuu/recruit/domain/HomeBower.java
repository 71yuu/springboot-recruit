package com.yuu.recruit.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "home_bower")
public class HomeBower implements Serializable {
    /**
     * 主页浏览表
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 雇员ID
     */
    @Column(name = "employee_id")
    private Long employeeId;

    /**
     * 雇主ID
     */
    @Column(name = "employer_id")
    private Long employerId;

    /**
     * 浏览时间
     */
    @Column(name = "create_time")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
