package com.yuu.recruit.domain;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "employee_bookmarked")
public class EmployeeBookmarked implements Serializable {
    /**
     * 雇员收藏任务ID
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
     * 任务ID
     */
    @Column(name = "task_id")
    private Long taskId;

    private static final long serialVersionUID = 1L;
}
