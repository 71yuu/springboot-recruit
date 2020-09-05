package com.yuu.recruit.service;

import com.yuu.recruit.vo.HomeBowerVo;

import java.util.List;

/**
 * 首页业务逻辑接口
 */
public interface HomeBowerService{

    /**
     * 获取雇员
     *
     * @param id
     * @return
     */
    List<HomeBowerVo> getByRecentlyEmployeeId(Long id);
}
