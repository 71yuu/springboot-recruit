package com.yuu.recruit.service;

import com.yuu.recruit.domain.Bid;
import com.yuu.recruit.vo.BidVo;

import java.util.List;

/**
 * 雇员投标业务逻辑接口
 */
public interface BidService {


    /**
     * 雇员投标
     *
     * @param bid
     */
    void bid(Bid bid);

    /**
     * 判断雇员是否已经投标过该任务
     *
     * @param taskId 任务 ID
     * @param id     雇员ID
     * @return
     */
    boolean getBidByTaskIdAndEmployeeId(Long taskId, Long id);

    /**
     * 获取所有未中标的信息
     *
     * @param id 雇员 ID
     * @return
     */
    List<BidVo> getNoBitByEmployeeId(Long id);

    /**
     * 删除竞标时间
     *
     * @param bid 竞标 ID
     */
    void deleteById(Long bid);

    /**
     * 接受投标
     *
     * @param taskId 任务 ID
     * @param employeeId 雇员 ID
     */
    void acceptBid(Long taskId, Long employeeId);
}





