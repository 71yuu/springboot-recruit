package com.yuu.recruit.service.impl;

import com.yuu.recruit.domain.EmployeeBookmarked;
import com.yuu.recruit.mapper.EmployeeBookmarkedMapper;
import com.yuu.recruit.service.EmployeeBookmarkedService;
import com.yuu.recruit.service.TaskService;
import com.yuu.recruit.utils.IDUtil;
import com.yuu.recruit.vo.EmployeeBookmarkedVo;
import com.yuu.recruit.vo.TaskVo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 雇员收藏业务逻辑接口实现
 */
@Service
public class EmployeeBookmarkedServiceImpl implements EmployeeBookmarkedService{

    @Resource
    private EmployeeBookmarkedMapper employeeBookmarkedMapper;

    @Resource
    private TaskService taskService;

    @Override
    public EmployeeBookmarked bookmarked(Long id, Long taskId) {
        // 先根据雇员ID和任务ID获取收藏情况
        Example example = new Example(EmployeeBookmarked.class);
        example.createCriteria().andEqualTo("employeeId", id)
                                .andEqualTo("taskId", taskId);
        EmployeeBookmarked employeeBookmarked = employeeBookmarkedMapper.selectOneByExample(example);

        // 如果查询到了说明雇员已经收藏该任务，取消收藏,删除该条记录
        if (employeeBookmarked != null) {
            employeeBookmarkedMapper.deleteByExample(example);
        }

        // 如果没有查询到，说明雇员没有收藏该任务，添加一条收藏记录
        else {
            employeeBookmarked = new EmployeeBookmarked();
            employeeBookmarked.setId(IDUtil.getRandomId());
            employeeBookmarked.setEmployeeId(id);
            employeeBookmarked.setTaskId(taskId);
            employeeBookmarkedMapper.insert(employeeBookmarked);
        }

        return employeeBookmarked;
    }

    @Override
    public List<EmployeeBookmarkedVo> getByEmployeeId(Long employeeId) {
        Example example = new Example(EmployeeBookmarked.class);
        example.createCriteria().andEqualTo("employeeId", employeeId);
        List<EmployeeBookmarked> employeeBookmarkeds = employeeBookmarkedMapper.selectByExample(example);
        // 转换为视图展示对象
        List<EmployeeBookmarkedVo> employeeBookmarkedVos = new ArrayList<>();
        for (EmployeeBookmarked employeeBookmarked : employeeBookmarkeds) {
            // 创建一个 Vo 对象
            EmployeeBookmarkedVo employeeBookmarkedVo = new EmployeeBookmarkedVo();
            // 设置 ID
            employeeBookmarkedVo.setId(employeeId);
            // 查询任务信息
            TaskVo taskVo = taskService.getById(employeeBookmarked.getTaskId());
            // 设置任务信息
            employeeBookmarkedVo.setTaskVo(taskVo);
            employeeBookmarkedVos.add(employeeBookmarkedVo);
        }
        return employeeBookmarkedVos;
    }

    @Override
    public List<Long> getIdsByEmployeeId(Long employeeId) {
        Example example = new Example(EmployeeBookmarked.class);
        example.createCriteria().andEqualTo("employeeId", employeeId);
        List<EmployeeBookmarked> employeeBookmarkeds = employeeBookmarkedMapper.selectByExample(example);
        List<Long> ids = new ArrayList<>();
        for (EmployeeBookmarked employeeBookmarked : employeeBookmarkeds) {
            Long aLong = new Long(employeeBookmarked.getTaskId());
            ids.add(aLong);
        }
        return ids;
    }

    @Override
    public void remove(Long id, Long taskId) {
        // 构造查询条件
        Example example = new Example(EmployeeBookmarked.class);
        example.createCriteria().andEqualTo("employeeId", id)
                                .andEqualTo("taskId", taskId);
        // 删除收藏
        employeeBookmarkedMapper.deleteByExample(example);
    }
}
