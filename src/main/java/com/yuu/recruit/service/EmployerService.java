package com.yuu.recruit.service;

import com.yuu.recruit.domain.Employer;

import java.util.List;

/**
 * 雇主业务逻辑接口
 */
public interface EmployerService {

    /**
     * 获取雇主总数
     *
     * @return
     */
    Integer getAllCount();

    /**
     * 获取所有雇主
     *
     * @return
     */
    List<Employer> getAll();

    /**
     * 根据用户名获取雇主信息
     *
     * @param username 用户名
     * @return
     */
    Employer getByUsername(String username);

    /**
     * 雇主注册
     *
     * @param username 用户名
     * @param password 密码
     */
    void register(String username, String password);

    /**
     * 雇主登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    Employer login(String username, String password);

    /**
     * 添加任务技能
     *
     * @param taskId    技能 ID
     * @param skillName 技能名称
     */
    void addSkill(Long taskId, String skillName);

    /**
     * 删除任务技能
     *
     * @param skillId
     */
    void deleteSkill(Long skillId);

    /**
     * 保存个人基本信息
     *
     * @param employer
     * @return
     */
    Employer save(Employer employer);

    /**
     * 修改密码
     *
     * @param id 雇主 ID
     * @param password 密码
     * @param newPassword 新密码
     * @return
     */
    String updatePass(Long id, String password, String newPassword);
}


