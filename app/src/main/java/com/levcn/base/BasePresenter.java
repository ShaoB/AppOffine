package com.levcn.base;

import java.lang.ref.WeakReference;

/**
 * @author : shaoBin
 * date   : 2021/7/5 16:59
 * desc   :
 */
public class BasePresenter<T extends IBaseView> {
    protected WeakReference<T> wef;

    /**
     * 解绑
     */
    public void detach() {
        if (this.wef != null) {
            this.wef.clear();
        }
    }

    /**
     * 绑定
     *
     * @param t
     */
    public void attach(T t) {
        this.wef = new WeakReference<T>(t);
    }
}
