package com.yuu.recruit.service;

import com.yuu.recruit.domain.Employee;
import com.yuu.recruit.vo.EmployeeVo;

import java.util.List;

/**
 * 雇员业务逻辑接口
 */
public interface EmployeeService {

    /**
     * 查询雇员总数
     *
     * @return
     */
    Integer getAllCount();

    /**
     * 获取最近注册的 10 个会员
     *
     * @return
     */
    List<Employee> getRecently();

    /**
     * 查询所有雇员信息
     *
     * @return
     */
    List<Employee> getAll();

    /**
     * 根据用户名获取雇员信息
     *
     * @param username 用户名
     * @return
     */
    Employee getByUsername(String username);

    /**
     * 雇员注册
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    void register(String username, String password);

    /**
     * 雇员登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    Employee login(String username, String password);

    /**
     * 总浏览次数
     *
     * @return
     */
    Integer getBowerCount(Long employeeId);

    /**
     * 保存个人信息
     *
     * @param employee
     * @return
     */
    Employee save(Employee employee);

    /**
     * 修改密码
     *
     * @param password
     * @param newPassword
     * @return
     */
    String updatePass(Long employeeId, String password, String newPassword);

    /**
     * 根据 ID 获取雇员信息
     *
     * @param employeeId
     * @return
     */
    EmployeeVo getById(Long employeeId);

    /**
     * 添加技能
     *
     * @param id        雇员 ID
     * @param skillName 技能名称
     */
    void addSkill(Long id, String skillName);

    /**
     * 删除技能
     *
     * @param skillId
     */
    void deleteSkill(Long skillId);

    /**
     * 雇主浏览雇员简介页
     *
     * @param employeeId
     * @param id
     */
    void bower(Long employeeId, Long id);
}


