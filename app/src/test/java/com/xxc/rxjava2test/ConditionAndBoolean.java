package com.xxc.rxjava2test;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Create By xxc
 * Date: 2020/9/18 18:14
 * Desc: 条件和布尔操作符
 */
public class ConditionAndBoolean {

    // 1.all：判断发射的所有数据是否都满足某个条件
    @Test
    public void testAll() {
        Observable.just(1, 2, 3, 4, 5)
                .all(integer -> integer < 10)
                .subscribe(System.out::println);
    }

    // 2.contains：判断是否发射特定值
    @Test
    public void testContains() {
        Observable.just(1, 2, 3)
                .contains(2)
                .subscribe(System.out::println);
    }

    // 3.amb：只发射首先发射数据的Observable的所有数据
    @Test
    public void testAmb() {
        Observable.ambArray(Observable.just(1, 2, 3).delay(1, TimeUnit.SECONDS),
                Observable.just(4, 5, 6))
                .subscribe(System.out::println);
    }

    // 4.defaultEmpty：如果原始Observable没有发射任何值，则发送一个默认值
    @Test
    public void testDefaultEmpty() {
        Observable.empty()
                .defaultIfEmpty(1010)
                .subscribe(System.out::println);
    }

    // 5.sequenceEqual：判断两个Observable是否发射相同的序列
    @Test
    public void testSequenceEqual() {
        Observable.sequenceEqual(
                Observable.just(1, 2, 3),
                Observable.just(1, 2, 3)
        ).subscribe(System.out::println);
    }

    // 6.skipUntil：丢弃原始Observable发射的数据，直到第二个Observable发射了一项数据
    @Test
    public void testSkipUntil() {
        Observable.intervalRange(1, 9, 0, 1, TimeUnit.MILLISECONDS)
                .skipUntil(Observable.timer(4, TimeUnit.MILLISECONDS))
                .subscribe(System.out::println);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 7.skipWhile：丢弃Observable发射的数据，直到条件不成立
    @Test
    public void testSkipWhile() {
        Observable.just(1, 2, 3, 4, 5)
                .skipWhile(integer -> integer <= 3)
                .subscribe(System.out::println);
    }

    // 8.takeUntil：当下一个Observable发射一项数据满足某个条件时，丢弃原始Observable发射的任何数据
    @Test
    public void testTakeUntil() {
        Observable.just(1, 2, 3, 4, 5)
                .takeUntil(integer -> integer == 3)
                .subscribe(System.out::println);
    }

    // 9.发射原始Observable的数据，直到条件不成立
    @Test
    public void testTakeWhile() {
        Observable.just(1, 2, 3, 4, 5)
                .takeWhile(integer -> integer < 3)
                .subscribe(System.out::println);
    }

}
