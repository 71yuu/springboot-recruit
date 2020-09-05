package com.yuu.recruit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuu.recruit.domain.Employee;
import com.yuu.recruit.domain.EmployeeSkill;
import com.yuu.recruit.domain.HomeBower;
import com.yuu.recruit.mapper.EmployeeMapper;
import com.yuu.recruit.mapper.EmployeeSkillMapper;
import com.yuu.recruit.mapper.HomeBowerMapper;
import com.yuu.recruit.service.EmployeeService;
import com.yuu.recruit.utils.IDUtil;
import com.yuu.recruit.vo.EmployeeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 雇员业务逻辑实现
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private EmployeeSkillMapper employeeSkillMapper;

    @Resource
    private HomeBowerMapper homeBowerMapper;

    @Override
    public Integer getAllCount() {
        List<Employee> employees = employeeMapper.selectAll();
        return employees != null ? employees.size() : 0;
    }

    @Override
    public List<Employee> getRecently() {
        // 使用 PageHelper 分页查询
        PageHelper.startPage(0, 10);

        // 设置分页查询条件
        Example example = new Example(Employee.class);
        // 按照创建时间倒叙查询
        example.orderBy("createTime").desc();
        PageInfo<Employee> employeePageInfo = new PageInfo<>(employeeMapper.selectByExample(example));

        // 获取查询结果
        List<Employee> list = employeePageInfo.getList();

        return list;
    }

    @Override
    public List<Employee> getAll() {
        return employeeMapper.selectAll();
    }

    @Override
    public Employee getByUsername(String username) {
        // 构造查询条件
        Example example = new Example(Employee.class);
        // 按用户名查询
        example.createCriteria().andEqualTo("username", username);
        Employee employee = employeeMapper.selectOneByExample(example);
        return employee;
    }

    @Override
    public void register(String username, String password) {
        // 新建一个雇员实体类
        Employee employee = new Employee();
        // 设置 ID
        employee.setId(IDUtil.getRandomId());
        // 设置用户名
        employee.setUsername(username);
        // 设置密码
        employee.setPassword(password);
        // 设置创建时间
        employee.setCreateTime(new Date());
        // 设置主页点击次数
        employee.setBrowseCount(0);
        // 设置默认头像
        employee.setHeadImg("http://recruit1.oss-cn-shenzhen.aliyuncs.com/10f65b3a-e73d-4d8b-b95b-3841534ea0dc.png");

        // 插入到数据库
        employeeMapper.insert(employee);
    }

    @Override
    public Employee login(String username, String password) {
        // 根据用户名获取用户信息
        Employee employee = getByUsername(username);

        // 验证密码是否正确,密码一致登录成功
        if (employee != null && employee.getPassword().equals(password)) {
            return employee;
        }

        // 密码不一致，返回 null
        return null;
    }

    @Override
    public Integer getBowerCount(Long employeeId) {
        // 根据雇员主键ID查询出雇员信息
        Employee employee = employeeMapper.selectByPrimaryKey(employeeId);
        // 获取雇员主页浏览数量
        Integer bowerCount = employee.getBrowseCount();
        return bowerCount;
    }

    @Override
    public Employee save(Employee employee) {
        // 更新
        employeeMapper.updateByPrimaryKeySelective(employee);

        // 重新查询雇员信息
        Employee currEmployee = employeeMapper.selectByPrimaryKey(employee.getId());
        return currEmployee;
    }

    @Override
    public String updatePass(Long employeeId, String password, String newPassword) {
        // 根据主键查询雇员ID信息
        Employee employee = employeeMapper.selectByPrimaryKey(employeeId);

        // 更新密码
        if (employee != null && employee.getPassword().equals(password)) {
            employee.setPassword(newPassword);
            employeeMapper.updateByPrimaryKey(employee);
            return "修改密码成功";

        }

        // 旧密码错误
        else {
            return "旧密码输入错误";
        }
    }

    @Override
    public EmployeeVo getById(Long employeeId) {
        // 查询雇员信息
        Employee employee = employeeMapper.selectByPrimaryKey(employeeId);

        // 查询雇员技能信息
        Example example = new Example(EmployeeSkill.class);
        example.createCriteria().andEqualTo("employeeId", employee.getId());

        // 转换为视图展示对象
        EmployeeVo employeeVo = new EmployeeVo();
        BeanUtils.copyProperties(employee, employeeVo);

        // 查询出雇员所有技能
        List<EmployeeSkill> employeeSkills = employeeSkillMapper.selectByExample(example);
        employeeVo.setSkills(employeeSkills);
        return employeeVo;
    }

    @Override
    public void addSkill(Long id, String skillName) {
        // 创建一个技能实体类，添加到数据库
        EmployeeSkill employeeSkill = new EmployeeSkill();
        employeeSkill.setId(IDUtil.getRandomId());
        employeeSkill.setSkillName(skillName);
        employeeSkill.setEmployeeId(id);
        employeeSkillMapper.insert(employeeSkill);
    }

    @Override
    public void deleteSkill(Long skillId) {
        employeeSkillMapper.deleteByPrimaryKey(skillId);
    }

    @Override
    public void bower(Long employeeId, Long employerId) {
        // 增加一条主页浏览记录
        HomeBower homeBower = new HomeBower();
        homeBower.setId(IDUtil.getRandomId());
        homeBower.setEmployeeId(employeeId);
        homeBower.setEmployerId(employerId);
        homeBower.setCreateTime(new Date());
        homeBowerMapper.insert(homeBower);

        // 雇员主页访问次数 + 1
        Employee employee = employeeMapper.selectByPrimaryKey(employeeId);
        Integer bowerCount = employee.getBrowseCount() + 1;
        employee.setBrowseCount(bowerCount);
        employeeMapper.updateByPrimaryKey(employee);
    }
}


