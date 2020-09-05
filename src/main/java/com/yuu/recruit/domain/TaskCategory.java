package com.yuu.recruit.domain;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "task_category")
public class TaskCategory implements Serializable {
    /**
     * 任务分类ID
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 分类名称
     */
    @Column(name = "category_name")
    private String categoryName;

    /**
     * 分类图片展示地址
     */
    @Column(name = "category_img")
    private String categoryImg;

    /**
     * 是否热门 0 否 1 热门
     */
    @Column(name = "is_popular")
    private Byte isPopular;

    private static final long serialVersionUID = 1L;
}
