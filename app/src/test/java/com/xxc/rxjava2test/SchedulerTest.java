package com.xxc.rxjava2test;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Create By xxc
 * Date: 2020/9/1 15:30
 * Desc: RxJava2线程调度
 */
public class SchedulerTest {

    /**
     * 1.线程切换
     * 多次调用subscribeOn设置线程，只有第一次有效（被观察者线程）
     * 多次调用observeOn设置线程，线程就会切换一次（观察者线程）
     */
    @Test
    public void switchThread() {
        Observable.just("aaa", "bbb")
                .observeOn(Schedulers.newThread())
                .map(String::toUpperCase)
                .subscribeOn(Schedulers.single())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(System.out::println);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
