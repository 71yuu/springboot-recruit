package com.yuu.recruit.controller;

import com.yuu.recruit.domain.Employer;
import com.yuu.recruit.domain.Task;
import com.yuu.recruit.service.BidService;
import com.yuu.recruit.service.EmployerService;
import com.yuu.recruit.service.TaskCategoryService;
import com.yuu.recruit.service.TaskService;
import com.yuu.recruit.vo.TaskCategoryVo;
import com.yuu.recruit.vo.TaskVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 雇主控制器
 *
 * @author by yuu
 * @Classname EmployerController
 * @Date 2019/10/15 21:30
 * @see com.yuu.recruit.controller
 */
@Controller
@RequestMapping("employer")
public class EmployerController {

    @Resource
    private TaskService taskService;

    @Resource
    private TaskCategoryService taskCategoryService;

    @Resource
    private BidService bidService;

    @Resource
    private EmployerService employerService;

    /**
     * 雇主退出登录
     *
     * @param session
     * @return
     */
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("employer");
        return "redirect:/index";
    }

    /**
     * 跳转到个人中心
     */
    @GetMapping("dashboard")
    public String dashboard(HttpSession session, Model model) {

        // 获取雇主登录情况
        Employer employer = (Employer) session.getAttribute("employer");

        // 查询总发布任务数量
        List<TaskVo> tasks = taskService.getByEmployerId(employer.getId());
        Integer taskCount = tasks != null ? tasks.size() : 0;


        // 查询任务总投标人数
        Integer bidCount = taskService.getBidCount(employer.getId());

        // 任务提交完成
        List<TaskVo> taskVos = taskService.getRecentlySubmit(employer.getId());

        // 放置域对象中
        model.addAttribute("taskCount", taskCount);
        model.addAttribute("bidCount", bidCount);
        model.addAttribute("tasks", taskVos);

        return "employer/dashboard";
    }

    /**
     * 跳转到任务发布页面
     *
     * @return
     */
    @GetMapping("task/post")
    public String postTask(Model model) {
        // 查询出所有任务分类信息
        List<TaskCategoryVo> taskCategoryVos = taskCategoryService.getAll();
        model.addAttribute("taskCategories", taskCategoryVos);
        return "employer/post_task";
    }

    /**
     * 雇主发布一个任务
     *
     * @param session
     * @return
     */
    @PostMapping("task/post")
    public String postTask(HttpSession session, Task task, String skills, String upload, RedirectAttributes redirectAttributes) {
        // 获取雇主信息
        Employer employer = (Employer) session.getAttribute("employer");

        // 添加任务
        task.setEmployerId(employer.getId());
        taskService.postTask(task, skills, upload);

        // 提示消息
        redirectAttributes.addFlashAttribute("msg", "发布任务成功，等待管理员审核！");
        return "redirect:/employer/task/post";
    }

    /**
     * 跳转到我的任务页面
     *
     * @param session
     * @return
     */
    @GetMapping("myTasks")
    public String myTask(HttpSession session, Model model) {
        // 查询雇主信息
        Employer employer = (Employer) session.getAttribute("employer");

        // 查询雇主的所有任务
        List<TaskVo> taskVos = taskService.getByEmployerId(employer.getId());

        model.addAttribute("tasks", taskVos);

        return "employer/my_task";
    }

    /**
     * 跳转到雇主修改任务页面
     *
     * @param taskId 任务 ID
     * @return
     */
    @GetMapping("task/update")
    public String updateTask(Long taskId, Model model) {
        // 查询所有任务
        List<TaskCategoryVo> categorys = taskCategoryService.getAll();

        TaskVo taskVo = taskService.getById(taskId);

        model.addAttribute("task", taskVo);
        model.addAttribute("taskCategories", categorys);
        return "employer/update_task";
    }

    /**
     * 添加技能
     *
     * @param skillName 技能名称
     * @return
     */
    @PostMapping("skill/add")
    @ResponseBody
    public String addSkill(String skillName, Long taskId, HttpSession session) {
        if (!"".equals(skillName)) {
            employerService.addSkill(taskId, skillName);
        }
        return "添加技能";
    }

    /**
     * 删除技能
     *
     * @param skillId
     * @return
     */
    @PostMapping("skill/delete")
    @ResponseBody
    public String deleteSkill(Long skillId) {
        employerService.deleteSkill(skillId);
        return "删除技能";
    }

    /**
     * 更新任务
     *
     * @param task 任务
     * @return
     */
    @PostMapping("task/update")
    public String updateTask(Task task, String upload, RedirectAttributes redirectAttributes) {
        // 修改任务
        if (!"".equals(upload)) {
            task.setFilename(upload);
        }
        taskService.updateTask(task);
        // 提示消息
        redirectAttributes.addFlashAttribute("msg", "发布任务成功，等待管理员审核！");
        return "redirect:/employer/myTasks";
    }

    /**
     * 删除任务
     *
     * @param taskId 任务 ID
     * @return
     */
    @GetMapping("task/delete")
    public String deleteTask(Long taskId) {
        taskService.deleteById(taskId);
        return "redirect:/employer/myTasks";
    }

    /**
     * 跳转到任务竞标者管理页面
     *
     * @return
     */
    @GetMapping("taskBidders")
    public String manageBidders(Long taskId, Model model) {

        // 查询任务信息
        TaskVo taskVo = taskService.getById(taskId);

        // 放置域对象中
        model.addAttribute("task", taskVo);


        return "employer/manage_bidders";
    }


    /**
     * 中标：接受投标
     *
     * @param taskId
     * @return
     */
    @GetMapping("acceptBid")
    public String acceptBid(Long taskId, Long employeeId) {
        bidService.acceptBid(taskId, employeeId);
        return "redirect:/employer/myTasks";
    }

    /**
     * 确定完成任务
     *
     * @param taskId
     * @return
     */
    @GetMapping("task/success")
    public String successTask(Long taskId) {
        taskService.successTask(taskId);
        return "redirect:/employer/myTasks";
    }

    /**
     * 跳转到个人信息设置页面
     *
     * @return
     */
    @GetMapping("settings/base")
    public String settings(HttpSession session, Model model) {
        // 获取 session 中的雇员信息
        Employer employer = (Employer) session.getAttribute("employer");
        model.addAttribute("employer", employer);
        return "employer/settings_base";
    }

    /**
     * 保存个人基本信息
     *
     * @param employer
     * @return
     */
    @PostMapping("settings/base/save")
    public String saveBase(Employer employer, HttpSession session) {
        // 更新个人信息到数据库
        Employer currEmployer = employerService.save(employer);

        // 更新 session 中的个人信息
        session.setAttribute("employer", currEmployer);
        return "redirect:/employer/settings/base";
    }

    /**
     * 跳转到修改密码页
     *
     * @return
     */
    @GetMapping("settings/password")
    public String updatePass() {
        return "employer/settings_pass";
    }

    /**
     * 修改密码
     *
     * @param password 原来的密码
     * @param newPassword 新密码
     * @return
     */
    @PostMapping("settings/password")
    public String updatePass(String password, String newPassword, HttpSession session, RedirectAttributes redirectAttributes) {
        // 查询雇员登录信息
        Employer employer = (Employer) session.getAttribute("employer");
        String msg = employerService.updatePass(employer.getId(), password, newPassword);
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/employer/settings/password";
    }
}
