package com.xxc.rxjava2test;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

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
                        stringIntegerGroupedObservable.subscribe(System.out::println);
                    }
                });
    }

    // 4.buffer：发射缓存数据集合
    @Test
    public void testBuffer() {
        Observable.range(1, 10)
                .buffer(3)
                .subscribe(System.out::println);
    }

    // 5.window：将数据分解为一个Observable窗口
    @Test
    public void testWindow() {
        Observable.range(1, 20)
                .window(3)
                .subscribe(integerObservable -> {
                    System.out.println("onNext() ===> ");
                    integerObservable.subscribe(System.out::println);
                });
    }

    /*=================过滤操作符==================*/
    // 1.first：只发射第一项数据
    @Test
    public void testFirst() {
        Observable.just(1, 2, 3, 4)
                .first(2) // 如果Observable不发射任何数据，则默认发射2
                .subscribe(System.out::println);
    }

    // 2.last：只发射最后一项数据
    @Test
    public void testLast() {
        Observable.just(1, 2, 3)
                .last(3)
                .subscribe(System.out::println);
    }

    // 3.take：只发射前n项数据
    @Test
    public void testTake() {
        Observable.just(1, 2, 3, 4, 5)
                .take(2)
                .subscribe(System.out::println);

//        Observable.intervalRange(0, 10, 0, 1, TimeUnit.SECONDS)
//                .take(3, TimeUnit.SECONDS)
//                .subscribe(System.out::println);
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    // 4.takeLast：只发射最后n项数据
    @Test
    public void testTakeLast() {
        Observable.just(1, 2, 3, 4)
                .takeLast(2)
                .subscribe(System.out::println);
    }

    // 5.skip：跳过前n项数据
    @Test
    public void testSkip() {
        Observable.just(1, 2, 3, 4, 5)
                .skip(2)
                .subscribe(System.out::println);
    }

    // 6.skipLast：跳过最后n项数据
    @Test
    public void testSkipLast() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .skipLast(3)
                .subscribe(System.out::println);
    }

    // 7.elementAt：只发射第n项数据
    @Test
    public void testElementAt() {
        Observable.just(1, 2, 3, 4, 5)
                .elementAt(3)
                .subscribe(System.out::println);
    }

    // 8.ignoreElements：不发射任何数据，只发射终止通知
    @Test
    public void testIgnoreElements() {
        Observable.just(1, 2, 3, 4)
                .ignoreElements()
                .subscribe(
                        () -> System.out.println("Complete"),
                        throwable -> System.out.println("Error"));
    }

    // 9.distinct：过滤重复数据项
    @Test
    public void testDistinct() {
        Observable.just(1, 2, 1, 3, 5, 2)
                .distinct()
                .subscribe(System.out::println);
    }

    // 9-1.distinctUntilChanged：只判断和它的直接前驱是否不同
    @Test
    public void testDistinctUntilChanged() {
        Observable.just(1, 2, 3, 4, 4, 5, 1, 2)
                .distinctUntilChanged()
                .subscribe(System.out::println);
    }

    // 10.filter：过滤指定条件的数据
    @Test
    public void testFilter() {
        Observable.just(100, 20, 23, 14, 1, 2, 3)
                .filter(integer -> integer > 20)
                .subscribe(System.out::println);
    }

    // 11.debounce：仅在过了指定时间还没发射数据时才发射一个数据，即过滤发射速率过快的数据项
    @Test
    public void testDebounce() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            if (emitter.isDisposed()) return;
            for (int i = 0; i < 10; i++) {
                emitter.onNext(i);
                Thread.sleep(i * 100);
            }
            emitter.onComplete();
        }).debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(System.out::println);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
