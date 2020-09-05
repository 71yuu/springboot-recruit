package com.yuu.recruit.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 任务分类页面展示对象
 *
 * @author by yuu
 * @Classname TaskCategoryVo
 * @Date 2019/10/13 21:28
 * @see com.yuu.recruit.vo
 */
@Data
public class TaskCategoryVo implements Serializable {

    /**
     * 任务分类ID
     */
    private Long id;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类图片展示地址
     */
    private String categoryImg;

    /**
     * 是否热门 0 否 1 热门
     */
    private Byte isPopular;

    /**
     * 分类状态
     */
    private Byte status;

    /**
     * 分类任务数量
     */
    private Integer taskCount;

}
