package com.yuu.recruit.vo;

import com.yuu.recruit.domain.Employee;
import com.yuu.recruit.domain.Employer;
import com.yuu.recruit.domain.TaskCategory;
import com.yuu.recruit.domain.TaskSkill;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 任务页面展示对象
 *
 * @author by yuu
 * @Classname TaskVo
 * @Date 2019/10/13 20:19
 * @see com.yuu.recruit.vo
 */
@Data
public class TaskVo implements Serializable {

    private static final long serialVersionUID = -1264009471871480548L;

    /**
     * 任务ID
     */
    private Long id;

    /**
     * 任务分类
     */
    private TaskCategory taskCategory;

    /**
     * 任务发布雇主信息
     */
    private Employer employer;

    /**
     * 任务标题
     */
    private String taskTitle;

    /**
     * 任务简介
     */
    private String taskProfile;

    /**
     * 任务描述
     */
    private String taskDesc;

    /**
     * 最低预算价格
     */
    private Double feesLow;

    /**
     * 最高预算价格
     */
    private Double feesHigh;

    /**
     * 任务附件地址
     */
    private String feesFile;

    /**
     * 任务附件地址
     */
    private String filename;

    /**
     * 完成任务雇员ID
     */
    private Employee employee;

    /**
     * 任务成交价格
     */
    private Double taskPrice;

    /**
     * 任务状态
     */
    private Byte taskStatus;

    /**
     * 任务中标时间
     */
    private Date bidTime;

    /**
     * 任务成交时间
     */
    private Date closeTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 多久以前发布
     */
    private String beforeTime;

    /**
     * 任务到期时间
     */
    private Date expireTime;

    /**
     * 任务所需技能标签
     */
    private List<TaskSkill> skills;

    /**
     * 该任务所有竞标者
     */
    private List<BidVo> bidVos;

    /**
     * 平均竞标价格
     */
    private Double avgPrice;
}
