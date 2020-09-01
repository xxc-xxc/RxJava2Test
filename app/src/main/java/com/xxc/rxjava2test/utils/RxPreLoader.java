package com.xxc.rxjava2test.utils;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Create By xxc
 * Date: 2020/8/20 17:24
 * Desc: 使用BehaviorSubject实现简单的预加载
 */
public class RxPreLoader<T> {

    // 缓存订阅之前的最新数据
    private BehaviorSubject<T> mData;
    private Disposable mDisposable;

    public RxPreLoader(T defaultValue) {
        mData = BehaviorSubject.createDefault(defaultValue);
    }

    /**
     * 发送事件
     * @param object
     */
    public void publish(T object) {
        mData.onNext(object);
    }

    /**
     * 订阅事件
     * @param onNext
     * @return
     */
    public Disposable subscribe(Consumer onNext) {
        mDisposable = mData.subscribe(onNext);
        return mDisposable;
    }

    /**
     * 取消订阅
     */
    public void dispose() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }

    /**
     * 获取缓存数据的Subject
     * @return
     */
    public BehaviorSubject<T> getCacheDataSubject() {
        return mData;
    }

    /**
     * 直接获取最近的一个数据
     * @return
     */
    public T getLastCacheData() {
        return mData.getValue();
    }
}
