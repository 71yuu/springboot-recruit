package com.yuu.recruit.service.impl;

import com.yuu.recruit.domain.Employer;
import com.yuu.recruit.domain.TaskSkill;
import com.yuu.recruit.mapper.EmployerMapper;
import com.yuu.recruit.mapper.TaskSkillMapper;
import com.yuu.recruit.service.EmployerService;
import com.yuu.recruit.utils.IDUtil;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 雇主业务逻辑实现
 */
@Service
public class EmployerServiceImpl implements EmployerService {

    @Resource
    private EmployerMapper employerMapper;

    @Resource
    private TaskSkillMapper taskSkillMapper;

    @Override
    public Integer getAllCount() {
        // 调用 employerMapper 查询所有雇主数据
        List<Employer> employers = employerMapper.selectAll();
        // 三元表达式返回总数，如果 employers 不为空返回集合的数据，为空返回 0
        return employers != null ? employers.size() : 0;
    }

    @Override
    public List<Employer> getAll() {
        return employerMapper.selectAll();
    }

    @Override
    public Employer getByUsername(String username) {
        // 构造查询条件
        Example example = new Example(Employer.class);
        // 根据用户名查询
        example.createCriteria().andEqualTo("username", username);
        // 执行查询
        Employer employer = employerMapper.selectOneByExample(example);
        return employer;
    }

    @Override
    public void register(String username, String password) {
        Employer employer = new Employer();
        // 设置 ID,使用 IDUtil 获取随机ID
        employer.setId(IDUtil.getRandomId());
        // 设置用户名
        employer.setUsername(username);
        // 设置密码
        employer.setPassword(password);
        // 设置创建时间
        employer.setCreateTime(new Date());
        // 设置默认头像
        employer.setHeadImg("http://recruit1.oss-cn-shenzhen.aliyuncs.com/10f65b3a-e73d-4d8b-b95b-3841534ea0dc.png");
        // 插入到数据库
        employerMapper.insert(employer);
    }

    @Override
    public Employer login(String username, String password) {
        // 根据用户名获取用户信息
        Employer employer = getByUsername(username);

        // 验证密码是否正确,密码一致登录成功
        if (employer != null && employer.getPassword().equals(password)) {
            return employer;
        }

        // 密码不一致，返回 null
        return null;
    }

    @Override
    public void addSkill(Long taskId, String skillName) {
        TaskSkill taskSkill = new TaskSkill();
        taskSkill.setId(IDUtil.getRandomId());
        taskSkill.setTaskId(taskId);
        taskSkill.setSkillName(skillName);
        taskSkillMapper.insert(taskSkill);
    }

    @Override
    public void deleteSkill(Long skillId) {
        taskSkillMapper.deleteByPrimaryKey(skillId);
    }

    @Override
    public Employer save(Employer employer) {
        // 更新
        employerMapper.updateByPrimaryKeySelective(employer);

        // 重新查询雇主信息
        Employer currEmployer = employerMapper.selectByPrimaryKey(employer.getId());
        return currEmployer;
    }

    @Override
    public String updatePass(Long employerId, String password, String newPassword) {
        // 根据主键查询雇员ID信息
        Employer employer = employerMapper.selectByPrimaryKey(employerId);

        // 更新密码
        if (employer != null && employer.getPassword().equals(password)) {
            employer.setPassword(newPassword);
            employerMapper.updateByPrimaryKey(employer);
            return "修改密码成功";

        }

        // 旧密码错误
        else {
            return "旧密码输入错误";
        }
    }
}


