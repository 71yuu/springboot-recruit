package com.yuu.recruit.domain;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "task_skill")
public class TaskSkill implements Serializable {
    /**
     * 任务技能ID
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 技能名称
     */
    @Column(name = "skill_name")
    private String skillName;

    /**
     * 任务ID
     */
    @Column(name = "task_id")
    private Long taskId;

    private static final long serialVersionUID = 1L;
}
