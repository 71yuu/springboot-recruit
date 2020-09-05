package com.yuu.recruit.service.impl;

import com.yuu.recruit.consts.TaskCategoryStatus;
import com.yuu.recruit.consts.TaskStatus;
import com.yuu.recruit.domain.Task;
import com.yuu.recruit.domain.TaskCategory;
import com.yuu.recruit.mapper.TaskCategoryMapper;
import com.yuu.recruit.mapper.TaskMapper;
import com.yuu.recruit.service.TaskCategoryService;
import com.yuu.recruit.service.TaskService;
import com.yuu.recruit.utils.IDUtil;
import com.yuu.recruit.vo.TaskCategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务分类业务逻辑实现
 */
@Service
public class TaskCategoryServiceImpl implements TaskCategoryService {

    @Resource
    private TaskCategoryMapper taskCategoryMapper;

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private TaskService taskService;

    @Override
    public List<TaskCategoryVo> getAll() {
        // 查询所有
        List<TaskCategory> taskCategories = taskCategoryMapper.selectAll();

        // 将所有的 TaskCategory 转换为 TaskCategoryVo 页面展示对象
        List<TaskCategoryVo> taskCategoryVos = taskCategoriesToTaskCategoryVos(taskCategories);

        return taskCategoryVos;
    }

    @Override
    public TaskCategory getById(Long id) {
        TaskCategory taskCategory = taskCategoryMapper.selectByPrimaryKey(id);
        return taskCategory;
    }

    @Override
    public void save(TaskCategory taskCategory) {
        // 如果 id 不为空，则为修改任务分类
        if (taskCategory.getId() != null) {
            // 根据主键选择性添加分类，有值的就修改，为 null 不修改
            taskCategoryMapper.updateByPrimaryKeySelective(taskCategory);
        }

        // id 为空，新增任务分类
        else {
            // 生成随机ID
            taskCategory.setId(IDUtil.getRandomId());
            // 设置是否热门，默认不热门（0）
            taskCategory.setIsPopular(TaskCategoryStatus.NO_POPULAR);
            // 插入到数据库
            taskCategoryMapper.insert(taskCategory);
        }
    }

    @Override
    public void onPopular(Long id) {
        // 根据 ID 查询出任务分类信息
        TaskCategory taskCategory = taskCategoryMapper.selectByPrimaryKey(id);
        // 设置为热门
        taskCategory.setIsPopular(TaskCategoryStatus.POPULAR);
        // 更新任务分类信息
        taskCategoryMapper.updateByPrimaryKeySelective(taskCategory);
    }

    @Override
    public void closePopular(Long id) {
        // 根据 ID 查询出任务分类信息
        TaskCategory taskCategory = taskCategoryMapper.selectByPrimaryKey(id);
        // 解除热门
        taskCategory.setIsPopular(TaskCategoryStatus.NO_POPULAR);
        // 更新任务分类信息
        taskCategoryMapper.updateByPrimaryKeySelective(taskCategory);
    }

    @Override
    public void delete(Long id) {
        taskCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<TaskCategoryVo> getPopular() {
        // 构建查询条件
        Example example = new Example(TaskCategory.class);

        // 分类状态为 1 为热门分类
        example.createCriteria().andEqualTo("isPopular", TaskCategoryStatus.POPULAR);

        // 执行查询
        List<TaskCategory> tasks = taskCategoryMapper.selectByExample(example);

        // 将查询到的任务分类信息转换为 Vo 对象，因为页面展示需要分类数量
        List<TaskCategoryVo> taskCategoryVos = taskCategoriesToTaskCategoryVos(tasks);

        return taskCategoryVos;
    }

    /**
     * 将所有的 TaskCategory 转换为 TaskCategoryVo 页面展示对象
     *
     * @param taskCategories
     * @return
     */
    private List<TaskCategoryVo> taskCategoriesToTaskCategoryVos(List<TaskCategory> taskCategories) {
        // 将所有的 TaskCategory 转换为 TaskCategoryVo 页面展示对象
        List<TaskCategoryVo> taskCategoryVos = new ArrayList<>();
        for (TaskCategory taskCategory : taskCategories) {
            TaskCategoryVo taskCategoryVo = new TaskCategoryVo();
            BeanUtils.copyProperties(taskCategory, taskCategoryVo);

            // 查询分类总任务数量
            Example example = new Example(Task.class);
            example.createCriteria().andEqualTo("categoryId", taskCategoryVo.getId());
            example.and().andNotEqualTo("taskStatus", TaskStatus.CHECK_FAIL).andNotEqualTo("taskStatus", TaskStatus.UNCHECK).andNotEqualTo("taskStatus", TaskStatus.COMPLETE);
            List<Task> tasks = taskMapper.selectByExample(example);
            Integer taskCount = tasks != null ? tasks.size() : 0;
            taskCategoryVo.setTaskCount(taskCount);

            // 添加到集合
            taskCategoryVos.add(taskCategoryVo);
        }
        return taskCategoryVos;
    }
}




