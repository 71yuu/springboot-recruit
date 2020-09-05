package com.yuu.recruit.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuu.recruit.consts.TaskStatus;
import com.yuu.recruit.domain.*;
import com.yuu.recruit.mapper.*;
import com.yuu.recruit.result.PageResult;
import com.yuu.recruit.service.TaskService;
import com.yuu.recruit.utils.IDUtil;
import com.yuu.recruit.vo.BidVo;
import com.yuu.recruit.vo.TaskVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 任务业务逻辑实现
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private TaskCategoryMapper taskCategoryMapper;

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private EmployerMapper employerMapper;

    @Resource
    private TaskSkillMapper taskSkillMapper;

    @Resource
    private BidMapper bidMapper;

    @Override
    public Integer getAllCompleteCount() {
        // 构建查询条件,Task.class 是要查询那个实体类 当前查询的是 Task 任务实体类，所以是 Task.class
        Example example = new Example(Task.class);
        // 构建查询条件，需要找出已经成交的任务，也就是 Task 的状态为 2 TaskStatus 是定义的一个状态常量类
        example.createCriteria().andEqualTo("taskStatus", TaskStatus.COMPLETE);
        // 根据条件查询
        List<Task> tasks = taskMapper.selectByExample(example);
        return tasks != null ? tasks.size() : 0;
    }

    @Override
    public Double getAllCompletePrice() {
        Example example = new Example(Task.class);
        example.createCriteria().andEqualTo("taskStatus", TaskStatus.COMPLETE);
        List<Task> tasks = taskMapper.selectByExample(example);

        // 循环遍历查询出来的完成任务，计算价格
        Double allCompletePrice = 0.0;
        for (Task task : tasks) {
            allCompletePrice += task.getTaskPrice();
        }

        return allCompletePrice;
    }

    @Override
    public List<TaskVo> getRecentlyComplete() {
        // 设置分页查询条件
        PageHelper.startPage(0, 10);

        // 构建查询条件,查询任务状态为 3 的任务
        Example example = new Example(Task.class);
        example.createCriteria().andEqualTo("taskStatus", TaskStatus.COMPLETE);
        example.orderBy("createTime").desc();
        PageInfo<Task> taskPageInfo = new PageInfo<>(taskMapper.selectByExample(example));

        // 获取查询结果
        List<Task> tasks = taskPageInfo.getList();

        // Task 转换为 TaskVo 视图展示类
        List<TaskVo> taskVos = tasksToTaskVos(tasks);

        return taskVos;
    }

    @Override
    public List<TaskVo> getAll() {
        // 查询出所有任务
        List<Task> tasks = taskMapper.selectAll();

        // 将 Task 转换为 TaskVo
        List<TaskVo> taskVos = tasksToTaskVos(tasks);

        return taskVos;
    }

    @Override
    public Integer getAllCount() {
        // 查询出所有任务
        Example example = new Example(Task.class);
        example.or().andNotEqualTo("taskStatus", TaskStatus.UNCHECK).andNotEqualTo("taskStatus", TaskStatus.CHECK_FAIL);
        List<Task> tasks = taskMapper.selectByExample(example);
        // 三元表达式返回任务总数，如果任务集合不为空，则返回集合大小也就是任务总数，如果任务集合是空的，则返回 0
        return tasks != null ? tasks.size() : 0;
    }

    @Override
    public List<TaskVo> getRecently() {
        // 设置分页查询条件，第一个条件是第几页，因为只查 5 条，所以是 0 ，代表第 0 页，5 是要查询的条数
        PageHelper.startPage(1, 5);

        // 构建查询条件,查询任务状态为 0 的任务，0 代表还未中标的任务
        Example example = new Example(Task.class);
        example.createCriteria().orEqualTo("taskStatus", TaskStatus.NO_BIT);
        // 根据创建时间，倒叙查询
        example.orderBy("createTime").desc();
        // 分页查询，查询出前 5 条
        PageInfo<Task> taskPageInfo = new PageInfo<>(taskMapper.selectByExample(example));

        // 获取查询结果
        List<Task> tasks = taskPageInfo.getList();

        // Task 转换为 TaskVo 视图展示类
        List<TaskVo> taskVos = tasksToTaskVos(tasks);

        return taskVos;
    }

    @Override
    public PageResult<TaskVo> page(Long categoryId, String key, int page) {
        // 使用 PageHelper 进行分页,两个参数，第一个 page 代表查询第几页，pageSize，代表每页查询的条数，默认 10 条
        int pageSize = 10;
        PageHelper.startPage(page, pageSize);

        // 构建查询条件，根据分类ID和搜索条件查询
        Example example = new Example(Task.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("taskStatus", TaskStatus.NO_BIT);
        // 如果分类 ID 为空查询，不为0按分类 ID 查询
        if (categoryId != 0) {
            criteria.andEqualTo("categoryId", categoryId);
        }
        // 如果 key 不为 ""，按照搜索条件查询
        if (!"".equals(key)) {
            // 按照任务标题查询 或 按照任务简介模糊查询
            example.and().orLike("taskTitle", '%' + key + '%').
                    orLike("taskProfile", '%' + key + '%');
        }

        // 分页查询
        PageInfo<Task> taskPageInfo = new PageInfo<>(taskMapper.selectByExample(example));

        // 获取查询结果
        List<Task> tasks = taskPageInfo.getList();

        // Task 转换为 TaskVo 视图展示类
        List<TaskVo> taskVos = tasksToTaskVos(tasks);

        // 创建一个分页对象
        PageResult<TaskVo> pageResult = new PageResult<>();
        // 第几页
        pageResult.setPage(page);
        // 分页数据
        pageResult.setList(taskVos);
        // 数据总条数
        Long total = taskPageInfo.getTotal();
        pageResult.setTotal(total.intValue());
        // 总页数
        int pageTotal = (total.intValue() + pageSize - 1) / pageSize;
        pageResult.setPageTotal(pageTotal);

        return pageResult;
    }

    @Override
    public TaskVo getById(Long taskId) {
        // 根据主键查询
        Task task = taskMapper.selectByPrimaryKey(taskId);

        // 将 task 转换为 taskVo
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        List<TaskVo> taskVos = tasksToTaskVos(tasks);

        // 因为只有一个，所以获取第一个
        TaskVo taskVo = taskVos.get(0);
        return taskVo;
    }

    @Override
    public Integer getByBidEmployeeId(Long id) {
        Example example = new Example(Task.class);
        // 根据雇员ID查询中标的总数，因为任务中有一个雇员ID字段，只有在中标之后才有有雇员信息
        example.createCriteria().andEqualTo("employeeId", id);
        List<Task> tasks = taskMapper.selectByExample(example);
        return tasks != null ? tasks.size() : 0;
    }

    @Override
    public List<TaskVo> getCompletedByEmployeeId(Long id) {
        // 构造查询条件
        Example example = new Example(Task.class);
        // 根据雇员 ID 和任务状态查询，任务状态为 2 代表已完成
        example.createCriteria().andEqualTo("employeeId", id)
                .andEqualTo("taskStatus", TaskStatus.COMPLETE);
        List<Task> tasks = taskMapper.selectByExample(example);

        // 转换为视图展示对象
        List<TaskVo> taskVos = tasksToTaskVos(tasks);

        return taskVos;
    }

    @Override
    public List<TaskVo> getUnCompletedByEmployeeId(Long id) {
        // 构造查询条件
        Example example = new Example(Task.class);
        // 根据雇员 ID 和任务状态查询，任务状态为 1 代表已中标，但未完成 或者 2 已提交，雇主还未确认
        example.createCriteria().andEqualTo("employeeId", id);
        example.and().orEqualTo("taskStatus", TaskStatus.BIT).orEqualTo("taskStatus", TaskStatus.SUBMIT);
        List<Task> tasks = taskMapper.selectByExample(example);

        // 转换为视图展示对象
        List<TaskVo> taskVos = tasksToTaskVos(tasks);

        return taskVos;
    }

    @Override
    public void bidTask(Long id, Long taskId) {
        // 根据任务主键 ID 获取任务
        Task task = taskMapper.selectByPrimaryKey(taskId);
        // 设置任务中标雇员
        task.setEmployeeId(id);
        // 设置任务状态
        task.setTaskStatus(TaskStatus.BIT);
        // 更新任务状态
        taskMapper.updateByPrimaryKey(task);
    }

    @Override
    public void submitTask(Long id, Long taskId) {
        // 根据任务主键 ID 获取任务
        Task task = taskMapper.selectByPrimaryKey(taskId);
        // 设置任务中标雇员
        task.setEmployeeId(id);
        // 设置任务状态
        task.setTaskStatus(TaskStatus.SUBMIT);
        // 更新任务状态
        taskMapper.updateByPrimaryKey(task);
    }

    @Override
    public List<TaskVo> getByEmployerId(Long id) {
        Example example = new Example(Task.class);
        example.createCriteria().andEqualTo("employerId", id);
        List<Task> tasks = taskMapper.selectByExample(example);
        List<TaskVo> taskVos = tasksToTaskVos(tasks);
        return taskVos;
    }

    @Override
    public Integer getBidCount(Long id) {
        Example example = new Example(Task.class);
        example.createCriteria().andEqualTo("employerId", id);
        List<Task> tasks = taskMapper.selectByExample(example);
        // 根据任务集合查询总投标数量
        Integer bidCount = 0;
        for (Task task : tasks) {
            Example example2 = new Example(Bid.class);
            example2.createCriteria().andEqualTo("taskId", task.getId());
            List<Bid> bids = bidMapper.selectByExample(example2);
            bidCount += bids.size();
        }
        return bidCount;
    }

    @Override
    public List<TaskVo> getRecentlySubmit(Long id) {
        // 先查询所有任务集合
        Example example = new Example(Task.class);
        example.createCriteria().andEqualTo("employerId", id)
                .andEqualTo("taskStatus", TaskStatus.SUBMIT);
        List<Task> tasks = taskMapper.selectByExample(example);
        List<TaskVo> taskVos = tasksToTaskVos(tasks);
        return taskVos;
    }

    @Override
    public List<TaskVo> getByEmployeeId(Long employeeId) {
        Example example = new Example(Task.class);
        example.createCriteria().andEqualTo("employeeId", employeeId)
                                .andEqualTo("taskStatus", TaskStatus.COMPLETE);
        List<Task> tasks = taskMapper.selectByExample(example);
        List<TaskVo> taskVos = tasksToTaskVos(tasks);
        return taskVos;
    }

    @Override
    public void postTask(Task task, String skills, String upload) {
        // 设置 ID
        task.setId(IDUtil.getRandomId());
        // 设置创建时间
        task.setCreateTime(new Date());
        // 设置任务状态
        task.setTaskStatus(TaskStatus.UNCHECK);
        // 设置附件名称
        task.setFilename(upload);
        // 插入到数据库
        taskMapper.insert(task);

        // 添加任务技能
        String[] skill = skills.split(",");
        for (String s : skill) {
            TaskSkill taskSkill = new TaskSkill();
            taskSkill.setId(IDUtil.getRandomId());
            taskSkill.setSkillName(s);
            taskSkill.setTaskId(task.getId());
            taskSkillMapper.insert(taskSkill);
        }
    }

    @Override
    public List<TaskVo> getUnCheckAll() {
        Example example = new Example(Task.class);
        example.createCriteria().andEqualTo("taskStatus", TaskStatus.UNCHECK);
        List<Task> tasks = taskMapper.selectByExample(example);
        List<TaskVo> taskVos = tasksToTaskVos(tasks);
        return taskVos;
    }

    @Override
    public void checkSuccess(Long taskId) {
        Task task = taskMapper.selectByPrimaryKey(taskId);
        task.setTaskStatus(TaskStatus.NO_BIT);
        taskMapper.updateByPrimaryKey(task);
    }

    @Override
    public void unCheckSuccess(Long taskId) {
        Task task = taskMapper.selectByPrimaryKey(taskId);
        task.setTaskStatus(TaskStatus.CHECK_FAIL);
        taskMapper.updateByPrimaryKey(task);
    }

    @Override
    public void updateTask(Task task) {
        if (task.getTaskStatus().equals(TaskStatus.CHECK_FAIL)) {
            task.setTaskStatus(TaskStatus.UNCHECK);
        }
        taskMapper.updateByPrimaryKeySelective(task);
    }

    @Override
    public void deleteById(Long taskId) {
        taskMapper.deleteByPrimaryKey(taskId);
    }

    @Override
    public void successTask(Long taskId) {
        // 更新任务状态，变为已完成状态
        Task task = taskMapper.selectByPrimaryKey(taskId);
        // 设置完成时间
        task.setCloseTime(new Date());
        // 修改任务状态为完成
        task.setTaskStatus(TaskStatus.COMPLETE);
        taskMapper.updateByPrimaryKey(task);
    }

    /**
     * Task 集合转换为 TaskVo 集合
     *
     * @param tasks
     */
    private List<TaskVo> tasksToTaskVos(List<Task> tasks) {
        List<TaskVo> taskVos = new ArrayList<>();
        for (Task task : tasks) {
            TaskVo taskVo = new TaskVo();
            // 调用 Spring 提供的工具类复制相同的属性
            BeanUtils.copyProperties(task, taskVo);

            // 查询雇主信息
            Employer employer = employerMapper.selectByPrimaryKey(task.getEmployerId());
            taskVo.setEmployer(employer);

            // 查询出任务分类
            TaskCategory taskCategory = taskCategoryMapper.selectByPrimaryKey(task.getCategoryId());
            taskVo.setTaskCategory(taskCategory);

            // 查询出任务完成雇员信息
            if (task.getTaskStatus() != TaskStatus.NO_BIT) {
                Employee employee = employeeMapper.selectByPrimaryKey(task.getEmployeeId());
                taskVo.setEmployee(employee);
            }

            // 计算多久以前发布
            // 先计算差多少秒，调用 Hutool 的日期计算工具类
            String beforeTime = taskBeforeTime(task);
            taskVo.setBeforeTime(beforeTime);

            // 查询任务所需技能标签,技能与标签是一对多的关系，因为任务和雇员都对应技能表，所以采用第三张表来映射关系
            List<TaskSkill> skills = findTaskSkills(task);
            taskVo.setSkills(skills);

            // 查询该任务所有竞标者
            List<BidVo> bidVos = findTaskBids(task);
            taskVo.setBidVos(bidVos);

            // 计算平均竞标价格
            // 总竞标价格
            Double totalPrice = 0.0;
            Double avgPrice = 0.0;
            if (bidVos.size() != 0) {
                for (BidVo bidVo : bidVos) {
                    totalPrice += bidVo.getBidPrice();
                }
                avgPrice = totalPrice / bidVos.size();
            }
            taskVo.setAvgPrice(avgPrice);

            // 如果任务属于未完成状态，计算到期时间和剩余完成时间
            if (task.getTaskStatus().equals(TaskStatus.BIT)) {
                // 计算到期时间
                // 获取该任务中标雇员
                Long employeeId = task.getEmployeeId();
                // 查询任务中标信息，查询到期时间
                Example example = new Example(Bid.class);
                example.createCriteria().andEqualTo("employeeId", employeeId)
                        .andEqualTo("taskId", task.getId());
                Bid bid = bidMapper.selectOneByExample(example);
                // 获取到期时间
                Date expireTime = bid.getDeliveryTime();
                taskVo.setExpireTime(expireTime);
            }

            // 添加到集合中
            taskVos.add(taskVo);
        }
        return taskVos;
    }

    /**
     * 查询任务所有竞标者信息
     *
     * @param task 任务
     * @return
     */
    private List<BidVo> findTaskBids(Task task) {
        Example example = new Example(Bid.class);
        // 根据任务 ID，查询出所有竞标信息
        example.createCriteria().andEqualTo("taskId", task.getId());
        List<Bid> bids = bidMapper.selectByExample(example);
        List<BidVo> bidVos = new ArrayList<>();
        for (Bid bid : bids) {
            BidVo bidVo = new BidVo();
            // 相同属性进行复制
            BeanUtils.copyProperties(bid, bidVo);
            // 根据投标雇员ID查询出雇员信息
            Employee currEmployee = employeeMapper.selectByPrimaryKey(bid.getEmployeeId());
            bidVo.setEmployee(currEmployee);
            // 复制完值添加到集合中
            bidVos.add(bidVo);
        }
        return bidVos;
    }

    /**
     * 查询任务所需要的技能
     *
     * @param task
     * @return
     */
    private List<TaskSkill> findTaskSkills(Task task) {
        Example example = new Example(TaskSkill.class);
        example.createCriteria().andEqualTo("taskId", task.getId());
        // 查询出所有的任务与技能对应关系，再根据技能ID，查询出技能信息
        List<TaskSkill> taskSkills = taskSkillMapper.selectByExample(example);
        return taskSkills;
    }

    /**
     * 计算任务多久以前发布
     *
     * @param task
     * @return
     */
    private String taskBeforeTime(Task task) {
        String beforeTime = "";
        Date createTime = task.getCreateTime();
        long ms = DateUtil.between(createTime, new Date(), DateUnit.MS);
        // 如果相差秒数大于 60，计算分钟相差数
        if (ms > 60) {
            // 计算相差分钟数，如果分钟数大于 60 计算小时
            long minute = DateUtil.between(createTime, new Date(), DateUnit.MINUTE);
            if (minute > 60) {
                // 计算相差小时数，如果小时大于 24 计算天数
                long hour = DateUtil.between(createTime, new Date(), DateUnit.HOUR);
                if (hour > 24) {
                    // 计算相差天数，如果天数大于 31 计算月数
                    long day = DateUtil.between(createTime, new Date(), DateUnit.DAY);
                    if (day > 31) {
                        // 计算相差月数，如果月数大于 12 计算年数
                        long month = DateUtil.betweenMonth(createTime, new Date(), true);
                        if (month > 12) {
                            long year = DateUtil.betweenYear(createTime, new Date(), true);
                            beforeTime = year + "年前";
                        } else {
                            beforeTime = month + "月前";
                        }
                    } else {
                        beforeTime = day + "天前";
                    }
                } else {
                    beforeTime = hour + "小时前";
                }
            } else {
                beforeTime = minute + "分钟前";
            }
        } else {
            beforeTime = ms + "秒前";
        }
        return beforeTime;
    }
}







