package com.yuu.recruit.service;

import com.yuu.recruit.domain.TaskCategory;
import com.yuu.recruit.vo.TaskCategoryVo;

import java.util.List;

/**
 * 任务分类业务逻辑接口
 */
public interface TaskCategoryService {

    /**
     * 查询所有任务分类
     *
     * @return
     */
    List<TaskCategoryVo> getAll();

    /**
     * 查询任务分类信息
     *
     * @param id 分类 ID
     * @return
     */
    TaskCategory getById(Long id);

    /**
     * 保存任务分类信息
     *
     * @param taskCategory
     */
    void save(TaskCategory taskCategory);

    /**
     * 设为热门
     *
     * @param id
     */
    void onPopular(Long id);

    /**
     * 解除热门
     *
     * @param id
     */
    void closePopular(Long id);

    /**
     * 删除任务分类
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 查询热门分类
     *
     * @return
     */
    List<TaskCategoryVo> getPopular();

}




