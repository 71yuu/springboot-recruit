package com.yuu.recruit.controller;

import com.yuu.recruit.domain.Employee;
import com.yuu.recruit.result.PageResult;
import com.yuu.recruit.service.EmployeeBookmarkedService;
import com.yuu.recruit.service.TaskCategoryService;
import com.yuu.recruit.service.TaskService;
import com.yuu.recruit.vo.TaskCategoryVo;
import com.yuu.recruit.vo.TaskVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务控制器
 *
 * @author by yuu
 * @Classname TaskController
 * @Date 2019/10/14 17:18
 * @see com.yuu.recruit.controller
 */
@Controller
@RequestMapping("task")
public class TaskController {

    @Resource
    private TaskService taskService;

    @Resource
    private TaskCategoryService taskCategoryService;

    @Resource
    private EmployeeBookmarkedService employeeBookmarkedService;

    /**
     * 跳转到分类列表页
     *
     * @param categoryId 分类列表页
     * @param key 查询条件
     * @param page
     * @return
     */
    @GetMapping("list")
    public String list(@RequestParam(defaultValue = "0") Long categoryId,
                       @RequestParam(defaultValue = "") String key,
                       @RequestParam(defaultValue = "1") int page,
                       HttpSession session,
                       Model model) {
        // 如果雇员登录了，查询出雇员收藏信息
        Employee employee = (Employee) session.getAttribute("employee");
        List<Long> bookMarkedIds = new ArrayList<>();
        if (employee != null) {
            // 查询雇员收藏信息
            Long employeeId = employee.getId();
            // 查询雇员收藏任务ID集合
            bookMarkedIds = employeeBookmarkedService.getIdsByEmployeeId(employeeId);
        }

        // 查询出所有分类信息，因为选择分类的时候，需要分类信息
        List<TaskCategoryVo> taskCategories = taskCategoryService.getAll();


        // 调用 TaskService 进行分页查询，得到分页结果
        PageResult<TaskVo> tasksPage = taskService.page(categoryId, key, page);

        // 将查询结果放置到域对象中，供页面查询
        model.addAttribute("taskCategories", taskCategories);
        model.addAttribute("tasksPage", tasksPage);
        // 将查询条件页放置到域对象中，方便页面读取
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("key", key);
        model.addAttribute("bookMarkedIds", bookMarkedIds);

        return "task_list";
    }

    /**
     * 跳转到任务详情页
     *
     * @param taskId 任务 ID
     * @return
     */
    @GetMapping("page")
    public String page(@RequestParam(required = true) Long taskId, Model model) {
        // 根据任务 ID 查询出任务详情
        TaskVo taskVo = taskService.getById(taskId);
        // 放入域对象中，提供给页面查询
        model.addAttribute("task", taskVo);
        return "task_page";
    }

}
