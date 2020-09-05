package com.yuu.recruit.vo;

import com.yuu.recruit.domain.Employer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 雇员主页浏览视图展示对象
 *
 * @author by yuu
 * @Classname HomeBowerVo
 * @Date 2019/10/15 8:40
 * @see com.yuu.recruit.vo
 */
@Data
public class HomeBowerVo implements Serializable {

    /**
     * 主页浏览表
     */
    private Long id;

    /**
     * 雇主信息
     */
    private Employer employer;

    /**
     * 浏览时间
     */
    private Date createTime;
}
