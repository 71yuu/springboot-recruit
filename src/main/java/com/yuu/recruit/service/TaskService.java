package com.yuu.recruit.service;

import com.yuu.recruit.domain.Task;
import com.yuu.recruit.result.PageResult;
import com.yuu.recruit.vo.TaskVo;

import java.util.List;

/**
 * 任务业务逻辑接口
 */
public interface TaskService {

    /**
     * 获取所有任务完成数量
     *
     * @return
     */
    Integer getAllCompleteCount();

    /**
     * 获取所有任务完成价格总数
     *
     * @return
     */
    Double getAllCompletePrice();

    /**
     * 获取最近完成的十条任务
     *
     * @return
     */
    List<TaskVo> getRecentlyComplete();

    /**
     * 获取所有任务
     *
     * @return
     */
    List<TaskVo> getAll();

    /**
     * 获取任务发布总数
     *
     * @return
     */
    Integer getAllCount();

    /**
     * 查询近期发布任务
     *
     * @return
     */
    List<TaskVo> getRecently();

    /**
     * 分页查询任务列表
     *
     * @param categoryId 任务 ID
     * @param key        查询条件
     * @param page       第几页
     * @return
     */
    PageResult<TaskVo> page(Long categoryId, String key, int page);

    /**
     * 根据任务 ID 获取任务详情
     *
     * @param taskId 任务 ID
     * @return
     */
    TaskVo getById(Long taskId);

    /**
     * 根据完成雇员 ID 获取中标总数
     *
     * @param id
     * @return
     */
    Integer getByBidEmployeeId(Long id);

    /**
     * 获取雇员已完成的订单
     *
     * @param id 雇员 ID
     * @return
     */
    List<TaskVo> getCompletedByEmployeeId(Long id);

    /**
     * 获取雇员待完成的订单
     *
     * @param id 雇员 ID
     * @return
     */
    List<TaskVo> getUnCompletedByEmployeeId(Long id);

    /**
     * 雇员中标任务
     *
     * @param id
     */
    void bidTask(Long id, Long taskId);

    /**
     * 雇员提交任务
     *
     * @param id     雇员 ID
     * @param taskId 任务 ID
     */
    void submitTask(Long id, Long taskId);

    /**
     * 雇主获取任务总发布数量
     *
     * @param id
     * @return
     */
    List<TaskVo> getByEmployerId(Long id);

    /**
     * 获取所有任务总投标数
     *
     * @param id
     * @return
     */
    Integer getBidCount(Long id);

    /**
     * 获取任务提交请求
     *
     * @param id
     * @return
     */
    List<TaskVo> getRecentlySubmit(Long id);

    /**
     * 获取雇员ID
     *
     * @param employeeId 雇员ID
     * @return
     */
    List<TaskVo> getByEmployeeId(Long employeeId);

    /**
     * 雇主发布任务
     *
     * @param task
     */
    void postTask(Task task, String skills, String upload);

    /**
     * 查询所有待审核任务
     *
     * @return
     */
    List<TaskVo> getUnCheckAll();

    /**
     * 通过审核
     *
     * @param taskId 任务 ID
     */
    void checkSuccess(Long taskId);

    /**
     * 审核失败
     *
     * @param taskId 任务 ID
     */
    void unCheckSuccess(Long taskId);

    /**
     * 修改任务
     *
     * @param task
     */
    void updateTask(Task task);

    /**
     * 删除任务
     *
     * @param taskId
     */
    void deleteById(Long taskId);

    /**
     * 确认完成任务
     *
     * @param taskId 任务 ID
     */
    void successTask(Long taskId);
}






