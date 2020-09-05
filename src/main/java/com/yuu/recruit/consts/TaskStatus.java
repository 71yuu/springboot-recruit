package com.yuu.recruit.consts;

/**
 * 任务状态常量
 *
 * @author by yuu
 * @Classname TaskStatus
 * @Date 2019/10/13 18:16
 * @see com.yuu.recruit.consts
 */
public class TaskStatus {

    /**
     * 审核失败
     */
    public static final Byte CHECK_FAIL = -2;

    /**
     * 待审核
     */
    public static final Byte UNCHECK = -1;

    /**
     * 未中标
     */
    public static final Byte NO_BIT = 0;

    /**
     * 已中标
     */
    public static final Byte BIT = 1;

    /**
     * 雇员已经提交任务
     */
    public static final Byte SUBMIT = 2;

    /**
     * 任务交易成功
     */
    public static final Byte COMPLETE = 3;

}
