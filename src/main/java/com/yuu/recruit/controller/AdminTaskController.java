package com.yuu.recruit.controller;

import com.yuu.recruit.service.TaskService;
import com.yuu.recruit.vo.TaskVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 管理员管理任务控制器
 *
 * @author by yuu
 * @Classname AdminTaskController
 * @Date 2019/10/14 10:01
 * @see com.yuu.recruit.controller
 */
@Controller
@RequestMapping("admin/task")
public class AdminTaskController {

    @Resource
    private TaskService taskService;

    /**
     * 跳转到任务列表页
     *
     * @return
     */
    @GetMapping("")
    public String taskList(Model model) {
        // 查询所有任务
        List<TaskVo> taskVos = taskService.getAll();

        // 设置到域对象中，提供给页面展示
        model.addAttribute("tasks", taskVos);
        return "admin/task";
    }

    /**
     * 跳转到任务审核列表页
     *
     * @param model
     * @return
     */
    @GetMapping("check")
    public String taskCheck(Model model) {
        // 查询所有待审核任务
        List<TaskVo> taskVos = taskService.getUnCheckAll();

        // 设置到域对象中，提供给页面展示
        model.addAttribute("tasks", taskVos);
        return "admin/task_check";
    }

    /**
     * 审核通过
     *
     * @param model
     * @return
     */
    @GetMapping("checkSuccess")
    public String checkSuccess(Long taskId) {
        // 通过审核
        taskService.checkSuccess(taskId);
        return "redirect:/admin/task/check";
    }

    /**
     * 审核失败
     *
     * @param taskId 任务 ID
     * @return
     */
    @GetMapping("unCheckSuccess")
    public String unCheckSuccess(Long taskId) {
        // 审核失败
        taskService.unCheckSuccess(taskId);
        return "redirect:/admin/task/check";
    }
}
