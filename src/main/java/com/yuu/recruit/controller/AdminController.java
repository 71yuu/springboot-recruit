package com.yuu.recruit.controller;

import com.yuu.recruit.domain.Employee;
import com.yuu.recruit.service.EmployeeService;
import com.yuu.recruit.service.EmployerService;
import com.yuu.recruit.service.TaskService;
import com.yuu.recruit.domain.Admin;
import com.yuu.recruit.service.AdminService;
import com.yuu.recruit.vo.TaskVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 管理员控制器
 *
 * @author by yuu
 * @Classname AdminController
 * @Date 2019/10/13 16:26
 * @see com.yuu.recruit.controller
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    /**
     * Spring 容器自动注入 AdminService
     */
    @Resource
    private AdminService adminService;

    @Resource
    private EmployerService employerService;

    @Resource
    private EmployeeService employeeService;

    @Resource
    private TaskService taskService;

    /**
     * 跳转到登录页面
     *
     * @return
     */
    @GetMapping("login")
    public String login() {
        return "admin/login";
    }

    /**
     * 管理员登录
     *
     * @param admin 管理员，在参数上使用实体类接收，可以自动接受 username,password 属性
     * @return
     */
    @PostMapping("login")
    public String login(Admin admin, RedirectAttributes redirectAttributes, HttpSession session) {
        // 调用 AdminService 处理登录的业务逻辑，返回一个 Admin 对象，如果为空登录失败，如果不为空登录成功
        Admin currAdmin = adminService.login(admin);

        // 如果为空，登录失败，返回一个消息给登录页面提示, RedirectAttributes 是重定向用于把消息存放在域对象中，供页面获取
        if (currAdmin == null) {
            // 将消息放入重定向的与对象中
            redirectAttributes.addFlashAttribute("msg", "用户名或密码错误");
            // 重定向到登录页
            return "redirect:/admin/login";
        }

        // 如果 Admin 不为空，登录成功
        else {
            // 把管理员信息放入 session 中，保持管理员登录状态
            session.setAttribute("admin", admin);
            // 重定向到管理员首页
            return "redirect:/admin/index";
        }
    }

    /**
     * 跳转到管理员首页
     *
     * @param model 用户将数据存放在域对象中的对象
     * @return
     */
    @GetMapping("index")
    public String index(Model model) {

        // 总雇主数量
        Integer employerCount = employerService.getAllCount();

        // 总雇员数量
        Integer employeeCount = employeeService.getAllCount();

        // 总任务成交数量
        Integer allTaskCompleteCount = taskService.getAllCompleteCount();

        // 总任务成交金额
        Double allTaskCompletePrice = taskService.getAllCompletePrice();

        // 最近 10 个注册雇员
        List<Employee> employees = employeeService.getRecently();

        // 获取最近成交的 10 个任务
        List<TaskVo> taskVos = taskService.getRecentlyComplete();

        // 将查询出来的数据，放置域对象中，供页面展示
        model.addAttribute("employerCount", employerCount);
        model.addAttribute("employeeCount", employeeCount);
        model.addAttribute("allTaskCompleteCount", allTaskCompleteCount);
        model.addAttribute("allTaskCompletePrice", allTaskCompletePrice);
        model.addAttribute("employees", employees);
        model.addAttribute("taskVos", taskVos);

        // 跳转到管理员首页
        return "admin/index";
    }

    /**
     * 退出登录
     *
     * @return
     */
    @GetMapping("logout")
    public String logout(HttpSession session) {
        // 退出登录，只需要删除存放在 session 中的 admin 信息即可
        session.removeAttribute("admin");
        // 重定向到登录页
        return "redirect:/admin/login";
    }
}
