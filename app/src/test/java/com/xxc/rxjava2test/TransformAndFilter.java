package com.xxc.rxjava2test;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;

/**
 * Create By xxc
 * Date: 2020/9/8 9:06
 * Desc: 变换和过滤操作符
 */
public class TransformAndFilter {

    /*===============变换操作符=================*/
    // 1.map操作符：对一项数据进行变换
    @Test
    public void testMap() {
        Observable.just("HELLO")
                .map(String::toLowerCase)
                .map(s -> s + " world")
                .subscribe(System.out::println);

    }

    // 2.flatMap操作符
    @Test
    public void testFlatMap() {
        Observable.just(1)
                .flatMap((Function<Integer, ObservableSource<Integer>>) integer -> Observable.just(integer + 10))
                .subscribe(System.out::println);
    }

    // 3.groupBy
    @Test
    public void testGroupBy() {
        Observable.range(0, 12)
                .groupBy(integer -> (integer % 2 == 0) ? "偶数" : "奇数")
                .subscribe(stringIntegerGroupedObservable -> {
//                    System.out.println(stringIntegerGroupedObservable.getKey());
                    if (stringIntegerGroupedObservable.getKey().equalsIgnoreCase("偶数")) {
                        stringIntegerGroupedObservable.subscribe(integer -> {
                            System.out.println(integer);
                        });
                    }
                });
    }

}
