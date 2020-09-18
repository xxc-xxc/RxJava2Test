package com.xxc.rxjava2test;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

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

}
