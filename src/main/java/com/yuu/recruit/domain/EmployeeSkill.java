package com.yuu.recruit.domain;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "employee_skill")
public class EmployeeSkill implements Serializable {
    /**
     * 雇员技能对应ID
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
     * 技能名称
     */
    @Column(name = "skill_name")
    private String skillName;

    private static final long serialVersionUID = 1L;
}
