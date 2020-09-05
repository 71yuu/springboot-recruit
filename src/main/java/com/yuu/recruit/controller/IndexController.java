package com.yuu.recruit.controller;

import com.yuu.recruit.domain.Employee;
import com.yuu.recruit.domain.Employer;
import com.yuu.recruit.service.EmployeeService;
import com.yuu.recruit.service.EmployerService;
import com.yuu.recruit.service.TaskCategoryService;
import com.yuu.recruit.service.TaskService;
import com.yuu.recruit.vo.TaskCategoryVo;
import com.yuu.recruit.vo.TaskVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 系统首页控制器
 *
 * @author by yuu
 * @Classname IndexController
 * @Date 2019/10/14 12:26
 * @see com.yuu.recruit.controller
 */
@Controller
public class IndexController {

    @Resource
    private TaskCategoryService taskCategoryService;

    @Resource
    private TaskService taskService;

    @Resource
    private EmployeeService employeeService;

    @Resource
    private EmployerService employerService;

    /**
     * 跳转到系统首页，使用 localhost:8080 和 localhost:8080/index 都可以访问
     *
     * @return
     */
    @GetMapping({"", "/index"})
    public String index(Model model) {

        // 查询所有任务分类, 为什么要用 Vo 对象，因为首页展示的热门分类中需要显示，该分类的任务数量，所以需要创建一个 Vo 对象，来包含分类任务数量信息
        List<TaskCategoryVo> taskCategoryVos = taskCategoryService.getAll();

        // 查询任务发布总数
        Integer taskCount = taskService.getAllCount();

        // 查询自由职业者(雇员）总数
        Integer employeeCount = employeeService.getAllCount();

        // 雇主数量
        Integer employerCount = employerService.getAllCount();

        // 查询热门分类,分类状态为 1 则为人们分类
        List<TaskCategoryVo> popularCategories = taskCategoryService.getPopular();

        // 查询近期任务（查询 5 条）,根据创建时间来查询
        List<TaskVo> recentTaskVos =  taskService.getRecently();

        // 添加到 Model 域对象中，供页面展示
        model.addAttribute("taskCategories", taskCategoryVos);
        model.addAttribute("taskCount", taskCount);
        model.addAttribute("employeeCount", employeeCount);
        model.addAttribute("employerCount", employerCount);
        model.addAttribute("popularCategories", popularCategories);
        model.addAttribute("recentTasks", recentTaskVos);

        return "index";
    }

    /**
     * 跳转到注册页面
     *
     * @return
     */
    @GetMapping("register")
    public String register() {
        return "register";
    }

    /**
     * 用户注册，包括雇员和雇主，通过传过来的 accountType 判断
     *
     * @return
     */
    @PostMapping("register")
    public String register(Integer accountType, String username, String password, RedirectAttributes redirectAttributes) {
        // 如果传过来的 accountType 为 0 则为雇员注册
        if (accountType == 0) {
            // 先判断用户名是否注册过
            Employee employee = employeeService.getByUsername(username);
            // 如果 employee 不为 null,说明该用户名已经被注册过了，不能注册
            if (employee != null) {
                // 设置到重定向的域对象中，供页面提示
                redirectAttributes.addFlashAttribute("msg", "用户名已被注册");
                // 重定向到注册页
                return "redirect:/register";
            }

            // 用户名不存在，注册
            employeeService.register(username, password);

            // 注册成功，重定向到登录页
            return "redirect:/login";
        }

        // 如果 accountType != 0 则为雇主注册
        else {
            // 先判断用户名是否注册过
            Employer employer = employerService.getByUsername(username);
            // 如果 employer 不为 null,说明该用户名已经被注册过了，不能注册
            if (employer != null) {
                // 设置到重定向的域对象中，供页面提示
                redirectAttributes.addFlashAttribute("msg", "用户名已被注册");
                // 重定向到注册页
                return "redirect:/register";
            }

            // 用户名不存在，注册
            employerService.register(username, password);

            // 注册成功，重定向到注册页
            return "redirect:/login";
        }
    }

    /**
     * 跳转到登录页
     *
     * @return
     */
    @GetMapping("login")
    public String login() {
        return "login";
    }


    public IndexController() {
    }

    /**
     * 用户登录，包括雇员和雇主，通过传过来的 accountType 判断
     *
     * @return
     */
    @PostMapping("login")
    public String login(Integer accountType, String username, String password, RedirectAttributes redirectAttributes, HttpSession session) {
        // 如果传过来的 accountType 为 0 则为雇员登录
        if (accountType == 0) {
            // 雇员登录
            Employee employee = employeeService.login(username, password);
            // 如果雇员不为 null 登录成功
            if (employee != null) {
                // 将登录信息放入 session 中
                session.setAttribute("employee", employee);
                // 注销雇主登录信息
                session.removeAttribute("employer");
                // 重定向到首页
                return "redirect:/index";
            }

            // 登录失败
            else {
                redirectAttributes.addFlashAttribute("msg", "用户名或密码错误");
                // 登录失败，重定向到登录页
                return "redirect:/login";
            }
        }

        // 如果 accountType != 0 则为雇主登录
        else {
            // 雇主登录
            Employer employer = employerService.login(username, password);
            // 如果雇主不为 null 登录成功
            if (employer != null) {
                // 将登录信息放入 session 中
                session.setAttribute("employer", employer);
                // 注销雇员登录信息
                session.removeAttribute("employee");
                // 重定向到首页
                return "redirect:/index";
            }

            // 登录失败
            else {
                redirectAttributes.addFlashAttribute("msg", "用户名或密码错误");
                // 登录失败，重定向到登录页
                return "redirect:/login";
            }
        }
    }


}
