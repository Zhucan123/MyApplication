package com.example.zhucan.myapplication;

/**
 * Created by zhucan on 2017/1/13.
 */

/*连连看的规则是在最多只能转弯俩次的情况下有一条空白路线即可消除
 *1.如果俩个点在同一个边缘边上可消除
 *2.俩个点相邻可直接消除
 *3.Z型路线和C型路线畅通可消除
 *    a.Z型路线即先右转90度,再左转90度情况(或者先左转90度,再右转90度,即相反转向)
 *    b.C型路线即先右转90度,再又右转90度情况(或者先左转90度,再左转90度,即相同转向)
*/

public class LookandLook {

    // 俩个点的id编号
    private int ida;
    private int idb;

    // 空白处的id编号数组
    private int[] ids;

    public LookandLook() {
    }

    // 获取编号和编号数组
    public void setid(int ida, int idb, int[] ids) {
        if (idb > ida) {
            this.ida = ida;
            this.idb = idb;
        } else {
            this.ida = idb;
            this.idb = ida;
        }
        this.ids = ids;
    }

    // 判断俩个点的位置关系来确定是否连通
    public boolean getConnection() {
        // 先判断是否在同边上
        if (ida <= 10 & idb <= 10)
            return true;
        else if (ida % 10 == 0 & idb % 10 == 0)
            return true;
        else if (ida % 10 == 1 & idb % 10 == 1)
            return true;
        else if (ida > 90 & idb > 90)
            return true;
            // 如果不是同边则判断Z或C型路线是否想通
        else if (getLine())
            return true;
        return false;
    }

    // 判断连线中的一个格子是否为空白处
    private boolean getWhite(int id) {
        // id是判断的id编号,m和n是连连看点击连线的俩个点
        if (id == ida || id == idb) {
            return true;
        } else {
            for (int i = 0; i < ids.length; i++) {
                if (id == ids[i]) {
                    return true;
                }
            }
        }
        return false;
    }

    // 判断Z或C型路线是否连通
    private boolean getLine() {
        // 先定义一个转角点id
        int t;
        // 水平方向的路线
        for (int m = 0; m <= 10; m++) {
            t = ida - ida % 10 + m;
            if (getLineConnect1(t))
                return true;
        }
        // 垂直方向路线
        for (int n = 0; n < 10; n++) {
            t = ida % 10 + n * 10;
            if (getLineConnect2(t))
                return true;
        }

        return false;
    }

    // 判断水平方向的一条线路是否相通的方法
    private boolean getLineConnect1(int t) {

        // 转点在左边缘边的情况
        if (t == ida - ida % 10 + 1) {
            for (int i = 0; i <= ida % 10 - t % 10; i++) {
                if (!getWhite(ida - i))
                    return false;
            }
            for (int j = 0; j <= idb % 10 - t % 10; j++) {
                if (!getWhite(idb - j))
                    return false;
            }
            return true;
        } else

            // 转点在右边缘边情况
            if (t == ida - ida % 10 + 10) {
                for (int i = 0; i <= t - ida % 10; i++) {
                    if (!getWhite(ida + i))
                        return false;
                }
                for (int j = 0; j <= t - idb % 10; j++) {
                    if (!getWhite(idb + j))
                        return false;
                }
                return true;
            } else

                // 转点在a和b的左边的情况
                if (t % 10 < ida % 10 & t % 10 < idb % 10) {
                    for (int i = 0; i <= ida % 10 - t % 10; i++) {
                        if (!getWhite(ida - i))
                            return false;
                    }
                    for (int j = 0; j <= idb % 10 - t % 10; j++) {
                        if (!getWhite(idb - j))
                            return false;
                    }
                    for (int k = 0; k <= idb / 10 - ida / 10; k++) {
                        if (!getWhite(t + k * 10))
                            return false;
                    }
                } else

                    // 转点在a左边b右边的情况
                    if (t % 10 < ida % 10 & t % 10 >= idb % 10) {
                        for (int i = 0; i <= ida % 10 - t % 10; i++) {
                            if (!getWhite(ida - i))
                                return false;
                        }
                        for (int j = 0; j <= t % 10 - idb % 10; j++) {
                            if (!getWhite(idb + j))
                                return false;
                        }
                        for (int k = 0; k <= idb / 10 - ida / 10; k++) {
                            if (!getWhite(t + k * 10))
                                return false;
                        }
                    } else

                        // 转点在a右边b左边的情况
                        if (t % 10 >= ida % 10 & t % 10 < idb % 10) {
                            for (int i = 0; i <= t % 10 - ida % 10; i++) {
                                if (!getWhite(ida + i))
                                    return false;
                            }
                            for (int j = 0; j <= idb % 10 - t % 10; j++) {
                                if (!getWhite(idb - j))
                                    return false;
                            }
                            for (int k = 0; k <= idb / 10 - ida / 10; k++) {
                                if (!getWhite(t + k * 10))
                                    return false;
                            }
                        } else

                            // 转点在a右边 b右边的情况
                            if (t % 10 >= ida % 10 & t % 10 >= idb % 10) {
                                for (int i = 0; i <= t % 10 - ida % 10; i++) {
                                    if (!getWhite(ida + i))
                                        return false;
                                }
                                for (int j = 0; j <= t % 10 - idb % 10; j++) {
                                    if (!getWhite(idb + j))
                                        return false;
                                }
                                for (int k = 0; k <= idb / 10 - ida / 10; k++) {
                                    if (!getWhite(t + k * 10))
                                        return false;
                                }
                            }

        return true;
    }

    // 判断垂直方向的一个路线是否相通的方法
    private boolean getLineConnect2(int t) {
        // 转点在上边缘边的情况
        if (t == ida % 10) {
            for (int i = 0; i <= ida / 10 - t / 10; i++) {
                if (!getWhite(ida - i * 10))
                    return false;
            }
            for (int j = 0; j <= idb / 10 - t / 10; j++) {
                if (!getWhite(idb - j * 10))
                    return false;
            }
            return true;
        } else

            // 转点在下边缘边情况
            if (t == ida + (9 - ida / 10) * 10) {
                for (int i = 0; i <= t / 10 - ida / 10; i++) {
                    if (!getWhite(ida + i * 10))
                        return false;
                }
                for (int j = 0; j <= t / 10 - idb / 10; j++) {
                    if (!getWhite(idb + j * 10))
                        return false;
                }
                return true;
            } else

                // 转点在a和b的上边的情况
                if (t < ida & t < idb) {
                    for (int i = 0; i <= ida / 10 - idb / 10; i++) {
                        if (!getWhite(ida - i * 10))
                            return false;
                    }
                    for (int j = 0; j <= idb / 10 - t / 10; j++) {
                        if (!getWhite(idb - j * 10))
                            return false;
                    }
                    // 这个是分a右b左,a左b右的情况
                    if (ida % 10 > idb % 10)
                        for (int k = 0; k <= ida % 10 - idb % 10; k++) {
                            if (!getWhite(t - k))
                                return false;
                        }
                    else if (ida % 10 <= idb % 10)
                        for (int k = 0; k <= idb % 10 - ida % 10; k++) {
                            if (!getWhite(t + k))
                                return false;
                        }
                } else

                    // 转点在a下边b上的情况
                    if (t >= ida & t < idb) {
                        for (int i = 0; i <= t / 10 - ida / 10; i++) {
                            if (!getWhite(ida + i * 10))
                                return false;
                        }
                        for (int j = 0; j <= idb / 10 - t / 10; j++) {
                            if (!getWhite(idb - j * 10))
                                return false;
                        }
                        // 这个是分a右b左,a左b右的情况
                        if (ida % 10 > idb % 10)
                            for (int k = 0; k <= ida % 10 - idb % 10; k++) {
                                if (!getWhite(t - k))
                                    return false;
                            }
                        else if (ida % 10 <= idb % 10)
                            for (int k = 0; k <= idb % 10 - ida % 10; k++) {
                                if (!getWhite(t + k))
                                    return false;
                            }
                    } else

                        // 转点在a下边b下边的情况
                        if (t >= ida & t >= idb) {
                            for (int i = 0; i <= t / 10 - ida / 10; i++) {
                                if (!getWhite(ida + i * 10))
                                    return false;
                            }
                            for (int j = 0; j <= idb - t; j++) {
                                if (!getWhite(idb + j * 10))
                                    return false;
                            }
                            // 这个是分a右b左,a左b右的情况
                            if (ida % 10 > idb % 10)
                                for (int k = 0; k <= ida % 10 - idb % 10; k++) {
                                    if (!getWhite(t - k))
                                        return false;
                                }
                            else if (ida % 10 <= idb % 10)
                                for (int k = 0; k <= idb % 10 - ida % 10; k++) {
                                    if (!getWhite(t + k))
                                        return false;
                                }
                        }
        return true;
    }
}

