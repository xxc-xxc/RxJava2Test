package com.xxc.rxjava2test;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;

/**
 * Create By xxc
 * Date: 2020/9/24 12:00
 * Desc: 合并&连接操作符
 */
public class MergeAndConnect {

    // 1.merge：将多个Observable输出合并
    // 任何一个Observable发射了onError通知，merge操作符生成的Observable也会立即以onError通知终止
    // 若想继续发射数据，可以使用mergeDelayError
    @Test
    public void testMerge() {
        Observable.merge(
                Observable.just(1, 3, 5),
                Observable.just(2, 4, 6))
                .subscribe(System.out::println);
    }

    // 2.zip：结合多个Observable发射的数据项，然后发射函数返回的结果
    @Test
    public void testZip() {
        Observable.zip(Observable.just(1, 3, 5, 7),
                Observable.just(2, 4, 6),
                Integer::sum)
                .subscribe(System.out::println);
    }

    // 3.combineLatest：当原始Observables的任何一个发射了一条数据时，combineLatest使用一个函数结合它们最近发射的数据
    @Test
    public void testCombineLatest() {
        Observable.combineLatest(Observable.just(1, 3, 5, 7),
                Observable.just(2, 4, 6),
                Integer::sum)
                .subscribe(System.out::println); // 9, 11, 13
    }

    // 4.join：结合两个Observable发射的数据，基于特定规则选择待集合的数据项
    @Test
    public void testJoin() {
        // 参数1：目标Observable
        // 参数2：接收源Observable发射来的数据，并返回一个Observable
        // 参数3：接收目标Observable发射来的数据，并返回一个Observable
        // 参数4：接收源Observable和目标Observable发射的数据，组合后返回
        Observable.just(1, 2, 3).join(
                Observable.just(4, 5, 6),
                integer -> Observable.just(String.valueOf(integer)).delay(200, TimeUnit.MILLISECONDS),
                integer -> Observable.just(String.valueOf(integer)).delay(200, TimeUnit.MILLISECONDS),
                (integer, integer2) -> integer + ":" + integer2)
                .subscribe(System.out::println);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 5.startWith：开头插入一项数据
    @Test
    public void testStartWith() {
        Observable.just(1, 2, 3)
                .startWithArray(200, 300)
                .startWith(100)
                .subscribe(System.out::println);
    }

    // 6.connect,push,refCount
    // publish将普通的Observable转换为ConnectableObservable
    // connect触发ConnectableObservable发射数据
    // refCount将ConnectableObservable转为普通的Observable，同时保持Hot Observable的特性
    @Test
    public void testConnect() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Observable<Long> observable = Observable.interval(1, TimeUnit.SECONDS).take(6);
        ConnectableObservable<Long> connectableObservable = observable.publish();
        connectableObservable.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                System.out.println("subscriber1 -> " + aLong + " time -> " + format.format(new Date()));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        connectableObservable.delaySubscription(3, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("subscriber2 -> " + aLong + " time -> " + format.format(new Date()));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        connectableObservable.connect();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
