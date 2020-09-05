package com.yuu.recruit.controller;

import com.yuu.recruit.domain.TaskCategory;
import com.yuu.recruit.service.TaskCategoryService;
import com.yuu.recruit.vo.TaskCategoryVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 管理员管理任务分类控制器
 *
 * @author by yuu
 * @Classname AdminTaskCategoryController
 * @Date 2019/10/13 21:10
 * @see com.yuu.recruit.controller
 */
@Controller
@RequestMapping("admin/task/category")
public class AdminTaskCategoryController {

    @Resource
    private TaskCategoryService taskCategoryService;

    /**
     * 跳转到任务分类列表页
     *
     * @return
     */
    @GetMapping("")
    public String taskCategory(Model model) {
        // 查询所有分类
        List<TaskCategoryVo> taskCategories = taskCategoryService.getAll();

        // 设置到域对象中，提供给页面展示
        model.addAttribute("taskCategories", taskCategories);
        return "admin/task_category";
    }

    /**
     * 跳转到任务分类列表页，添加/编辑都在这个页面，通过 ID 判断是编辑还是删除
     *
     * @return
     */
    @GetMapping("form")
    public String form(Long id, Model model) {

        // 如果 ID 不为空则编辑任务分类，用 ID 去数据库查询出分类信息
        if (id != null) {
            TaskCategory taskCategory = taskCategoryService.getById(id);
            model.addAttribute("taskCategory", taskCategory);
        }

        return "admin/task_category_form";
    }

    /**
     * 保存任务分类信息,编辑和添加任务人类
     *
     * @param taskCategory 任务分类
     * @return
     */
    @PostMapping("save")
    public String save(TaskCategory taskCategory) {
        // 保存任务分类信息
        taskCategoryService.save(taskCategory);
        // 保存完，重定向到任务分类列表页
        return "redirect:/admin/task/category";
    }

    /**
     * 设为热门
     * @PathVariable 为路径参数，代表参数可以直接写在路径上
     *
     * @param id 任务分类ID
     * @return
     */
    @GetMapping("onPopular/{id}")
    public String onPopular(@PathVariable Long id) {
        taskCategoryService.onPopular(id);
        // 重定向到任务分类列表页
        return "redirect:/admin/task/category";
    }

    /**
     * 解除热门
     *
     * @param id
     * @return
     */
    @GetMapping("closePopular/{id}")
    public String closePopular(@PathVariable Long id) {
        taskCategoryService.closePopular(id);
        return "redirect:/admin/task/category";
    }

    /**
     * 删除任务分类信息
     *
     * @param id
     * @return
     */
    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        taskCategoryService.delete(id);
        return "redirect:/admin/task/category";
    }


}
