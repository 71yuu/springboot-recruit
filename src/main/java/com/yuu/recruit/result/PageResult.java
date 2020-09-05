package com.yuu.recruit.result;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果返回对象
 *
 * @author by yuu
 * @Classname PageResult
 * @Date 2019/10/14 19:50
 * @see com.yuu.recruit.result
 */
@Data
public class PageResult<T> implements Serializable {

    /**
     * 第几页
     */
    private int page;

    /**
     * 返回数据集合
     */
    private List<T> list;

    /**
     * 总数据条数
     */
    private int total;

    /**
     * 总页数
     */
    private int pageTotal;
}
