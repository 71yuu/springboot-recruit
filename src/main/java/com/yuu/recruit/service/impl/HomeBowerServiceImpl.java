package com.yuu.recruit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuu.recruit.domain.Employer;
import com.yuu.recruit.domain.HomeBower;
import com.yuu.recruit.mapper.EmployerMapper;
import com.yuu.recruit.mapper.HomeBowerMapper;
import com.yuu.recruit.service.HomeBowerService;
import com.yuu.recruit.vo.HomeBowerVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页业务逻辑接口实现
 */
@Service
public class HomeBowerServiceImpl implements HomeBowerService {

    @Resource
    private HomeBowerMapper homeBowerMapper;

    @Resource
    private EmployerMapper employerMapper;

    @Override
    public List<HomeBowerVo> getByRecentlyEmployeeId(Long id) {
        // 获取最近 10 条浏览情况
        PageHelper.startPage(1, 10);

        // 构造查询条件
        Example example = new Example(HomeBower.class);
        example.createCriteria().andEqualTo("employeeId", id);
        // 分页查询
        PageInfo<HomeBower> pageInfo = new PageInfo<>(homeBowerMapper.selectByExample(example));
        List<HomeBower> homeBowers = pageInfo.getList();

        // 将 HomeBower 转换为 HomeBowerVo 视图展示对象
        List<HomeBowerVo> homeBowerVos = new ArrayList<>();
        for (HomeBower homeBower : homeBowers) {
            // 创建一个 HomeBowerVo 对象
            HomeBowerVo homeBowerVo = new HomeBowerVo();
            // 相同的属性进行复制
            BeanUtils.copyProperties(homeBower, homeBowerVo);
            // 查询雇主信息
            Employer employer = employerMapper.selectByPrimaryKey(homeBower.getEmployerId());
            homeBowerVo.setEmployer(employer);
            // 添加到集合中
            homeBowerVos.add(homeBowerVo);
        }

        return homeBowerVos;
    }
}
