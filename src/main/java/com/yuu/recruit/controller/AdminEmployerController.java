package com.yuu.recruit.controller;

import com.yuu.recruit.domain.Employer;
import com.yuu.recruit.service.EmployerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 管理员雇主管理控制器
 *
 * @author by yuu
 * @Classname AdminEmployerController
 * @Date 2019/10/14 9:35
 * @see com.yuu.recruit.controller
 */
@Controller
@RequestMapping("admin/employer")
public class AdminEmployerController {

    @Resource
    private EmployerService employerService;

    /**
     * 跳转到雇主列表页
     *
     * @return
     */
    @GetMapping("")
    public String taskCategory(Model model) {
        // 查询所有雇主
        List<Employer> employers = employerService.getAll();

        // 设置到域对象中，提供给页面展示
        model.addAttribute("employers", employers);
        return "admin/employer";
    }
}
