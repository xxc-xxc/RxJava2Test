package com.xxc.rxjava2test;

import org.junit.Test;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

/**
 * Create By xxc
 * Date: 2020/8/10 15:21
 * Desc:
 */
public class MainTest {

    private static final String TAG = MainTest.class.getSimpleName();

    // 0.起始demo
    @Test
    public void emmit1() {
        Observable<Integer> observable = Observable.just(1);
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
    }

    // 1.do操作符
    // 给Observable的生命周期各个阶段加上一系列的回调监听
    @Test
    public void doOperator() {
        Observable.just("Hello")
                // doNext之前执行
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("doOnNext:" + s);
//                        LogUtils.d(TAG, "doOnNext:" + s);
                    }
                })
                // doNext之后执行
                .doAfterNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("doAfterNext:" + s);
//                        LogUtils.d(TAG, "doAfterNext:" + s);
                    }
                })
                // 正常调用onComplete时调用
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("doOnComplete:");
//                        LogUtils.d(TAG, "doOnComplete:");
                    }
                })
                // 订阅之后回调的方法
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        System.out.println("doOnSubscribe:");
//                        LogUtils.d(TAG, "doOnSubscribe:");
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("doAfterTerminate:");
//                        LogUtils.d(TAG, "doAfterTerminate:");
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("doFinally:");
//                        LogUtils.d(TAG, "doFinally:");
                    }
                })
                // 每次发射数据都会触发的回调
                .doOnEach(new Consumer<Notification<String>>() {
                    @Override
                    public void accept(Notification<String> stringNotification) throws Exception {
                        System.out.println("doFinally:");
//                        LogUtils.d(TAG, "doFinally:");
                    }
                })
                // 订阅和取消订阅触发的回调
                .doOnLifecycle(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        System.out.println("doOnLifecycle:" + disposable.isDisposed());
//                        LogUtils.d(TAG, "doOnLifecycle:" + disposable.isDisposed());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("doOnLifecycle: run");
//                        LogUtils.d(TAG, "doOnLifecycle: run");
                    }
                })
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        LogUtils.d(TAG, "onNext收到消息:" + s);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("收到消息:" + s);
//                        LogUtils.d(TAG, "收到消息:" + s);
                    }
                });
    }

    // 2.Cold Observable:只有观察者订阅了，才开始发射数据流
    @Test
    public void coldObservable() {
        Consumer<Long> consumer1 = l -> System.out.println("consumer1:" + l);

        Consumer<Long> consumer2 = l -> System.out.println("consumer2:" + l);

        Observable<Long> observable = Observable.create((ObservableOnSubscribe<Long>) emitter
                -> Observable.interval(10, TimeUnit.MILLISECONDS, Schedulers.computation())
                .take(Integer.MAX_VALUE)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        emitter.onNext(aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }))
                .observeOn(Schedulers.newThread());
        observable.subscribe(consumer1);
        observable.subscribe(consumer2);

        try {
            Thread.sleep(100L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3.Cold Observable借助Subject转为Hot Observable
    @Test
    public void testSubject() {
        Consumer<Long> consumer1 = aLong -> {
            System.out.println("consumer1 ==> " + aLong);
        };
        Consumer<Long> consumer2 = aLong -> {
            System.out.println("    consumer2 ==> " + aLong);
        };
        Consumer<Long> consumer3 = aLong -> {
            System.out.println("        consumer3 ==> " + aLong);
        };

        // 被观察者(生产者)
        Observable<Long> observable = Observable.create((ObservableOnSubscribe<Long>) emitter ->
                Observable.interval(10, TimeUnit.MILLISECONDS, Schedulers.computation())
                .take(Integer.MAX_VALUE)
                .subscribe(emitter::onNext))
                .observeOn(Schedulers.newThread());
        PublishSubject<Long> subject = PublishSubject.create();
        observable.subscribe(subject);

        subject.subscribe(consumer1);
        subject.subscribe(consumer2);

        try {
            Thread.sleep(20L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        subject.subscribe(consumer3);
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 4.Hot Observable ==> Cold Observable
    // 所有订阅者/观察者都取消订阅，数据流停止；重新订阅，则重新开始数据流
    @Test
    public void hotObservable1() {
        Consumer<Long> consumer1 = aLong -> System.out.println("consumer1 ==> " + aLong);
        Consumer<Long> consumer2 = aLong -> System.out.println("consumer2 ==> " + aLong);

        ConnectableObservable<Long> connectableObservable =
                Observable.create((ObservableOnSubscribe<Long>) emitter -> {
                    Observable.interval(10, TimeUnit.MILLISECONDS,
                            Schedulers.computation())
                            .take(Integer.MAX_VALUE)
                            .subscribe(emitter::onNext);
                }).observeOn(Schedulers.newThread()).publish();

        // ConnectableObservable需要调用connect()才能真正执行
        connectableObservable.connect();
        // Hot Observable ==> Cold Observable
        Observable<Long> observable = connectableObservable.refCount();
        Disposable disposable1 = observable.subscribe(consumer1);
        Disposable disposable2 = observable.subscribe(consumer2);
        try {
            Thread.sleep(25L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        disposable1.dispose();
        disposable2.dispose();

        System.out.println("重新开始数据流");
        disposable1 = observable.subscribe(consumer1);
        disposable2 = observable.subscribe(consumer2);

        try {
            Thread.sleep(20L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 5.Hot Observable ==> Cold Observable
    // 不是所有订阅者/观察者都取消订阅，则部分订阅者/观察者重新订阅时，不会从头开始数据流
    @Test
    public void hotObservable2() {
        Consumer<Long> consumer1 = aLong -> System.out.println("consumer1 ==> " + aLong);
        Consumer<Long> consumer2 = aLong -> System.out.println("    consumer2 ==> " + aLong);
        Consumer<Long> consumer3 = aLong -> System.out.println("        consumer3 ==> " + aLong);

        ConnectableObservable<Long> connectableObservable =
                Observable.create((ObservableOnSubscribe<Long>) emitter -> {
                    Observable.interval(10, TimeUnit.MILLISECONDS,
                            Schedulers.computation())
                            .take(Integer.MAX_VALUE)
                            .subscribe(emitter::onNext);
                }).observeOn(Schedulers.newThread()).publish();

        // ConnectableObservable需要调用connect()才能真正执行
        connectableObservable.connect();
        // Hot Observable ==> Cold Observable
        Observable<Long> observable = connectableObservable.refCount();
        Disposable disposable1 = observable.subscribe(consumer1);
        Disposable disposable2 = observable.subscribe(consumer2);
        observable.subscribe(consumer3);
        try {
            Thread.sleep(25L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        disposable1.dispose();
        disposable2.dispose();

        System.out.println("consumer1, consumer2从新订阅");
        disposable1 = observable.subscribe(consumer1);
        disposable2 = observable.subscribe(consumer2);

        try {
            Thread.sleep(20L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 6.被观察者：Single
    @Test
    public void single() {
        Single.create((SingleOnSubscribe<String>) emitter -> {
            // 发射数据，只能发射一个数据
            emitter.onSuccess("success");
//            emitter.onError(new Throwable());
        }).subscribe(
                s -> System.out.println(s),
                throwable -> {
                    throwable.printStackTrace();
                });

        Single.create((SingleOnSubscribe<String>) emitter ->
                emitter.onSuccess("success === >"))
                .subscribe((s, throwable) -> System.out.println(s));
    }

    // 7.被观察者：Completable
    // 不发射任何数据
    @Test
    public void completable() {
        Completable.fromAction(() ->
                System.out.println("Hello completable")).subscribe();

        // 结合andThen使用
        Completable.create(emitter -> {
            TimeUnit.SECONDS.sleep(1);
            emitter.onComplete();
        }).andThen(Observable.range(1, 10))
        .subscribe(integer -> System.out.println(integer));
    }

    // 8.被观察者：Maybe
    // 可以看做是Single和Completable的结合体
    // 只能发射0个或1个数据，发送多个也不会处理
    @Test
    public void maybe() {
        Maybe.create((MaybeOnSubscribe<String>) emitter -> {
            emitter.onSuccess("onSuccess");
//            emitter.onSuccess("onSuccess1");
        }).subscribe(s -> System.out.println(s));

        // Maybe没有发射数据时，subscribe调用MaybeObserver的onComplete()
        // Maybe发射了数据或调用了onError()，则不会执行MaybeObserver的onComplete()
        Maybe.create((MaybeOnSubscribe<String>) emitter -> {
            emitter.onComplete();
//            emitter.onSuccess("onSuccess");
        }).subscribe(
                s -> System.out.println(s),
                throwable -> {

                }, () -> {
                    System.out.println("Maybe onComplete");
                });
    }

    /**
     * 9.AsyncSubject
     * 接收onComplete之前的最后一个数据
     */
    @Test
    public void asyncSubject() {
//        AsyncSubject.create((ObservableOnSubscribe<String>) emitter -> {
//            emitter.onNext("1");
//            emitter.onNext("2");
//            emitter.onComplete();
//        }).subscribe(
//                s -> System.out.println(s),
//                throwable -> System.out.println(throwable.getMessage()),
//                () -> System.out.println("complete"));

        AsyncSubject<Object> subject = AsyncSubject.create();
        subject.onNext("1");
        subject.onNext("2");
        subject.subscribe(
                System.out::println,
                throwable -> System.out.println(throwable.getMessage()),
                () -> System.out.println("complete"));
        subject.onNext("3");
        subject.onNext("4");
        subject.onComplete();
    }

    /**
     * 10.BehaviorSubject
     * 接收subscribe之前的最后一个数据及之后的所有数据
     */
    @Test
    public void behaviorSubject() {
        BehaviorSubject<Object> subject = BehaviorSubject.createDefault("1");
//        subject.onNext("101010");
        subject.subscribe(
                System.out::println,
                throwable -> System.out.println(throwable.getMessage()),
                () -> System.out.println("onComplete")
        );
        subject.onNext("2");
        subject.onNext("3");
        subject.onComplete();
    }

    /**
     * 11.ReplaySubject
     * 无论何时订阅都发送所有事件
     */
    @Test
    public void replaySubject() {
//        ReplaySubject<Object> subject = ReplaySubject.create();
        // 只缓存订阅前的最后一条数据
        ReplaySubject<Object> subject = ReplaySubject.createWithSize(1);
        subject.onNext("1");
        subject.onNext("2");
        subject.subscribe(
                System.out::println,
                throwable -> System.out.println(throwable.getMessage()),
                () -> System.out.println("onComplete")
        );
        subject.onNext("3");
        subject.onNext("4");
        subject.onComplete();
    }

    /**
     * 12.PublishSubject
     * 只接收订阅后发送的事件
     */
    @Test
    public void publishSubject() {
        PublishSubject<Object> subject = PublishSubject.create();
        subject.onNext("1");
        subject.onNext("2");
//        subject.onComplete(); // 由于订阅前已经complete，将接收不到任何数据
        subject.subscribe(
                System.out::println,
                throwable -> System.out.println(throwable.getMessage()),
                () -> System.out.println("onComplete")
        );
        subject.onNext("3");
        subject.onNext("4");
        subject.onComplete();
    }

    /**
     * create操作符
     */
    @Test
    public void createObservable() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            if (!emitter.isDisposed()) {
                for (int i = 0; i < 10; i++) {
                    emitter.onNext(i);
                }
            }
        }).subscribe(
                System.out::println,
                throwable -> System.out.println(throwable.getMessage()),
                () -> System.out.println("complete"));
    }

    /**
     * just操作符
     */
    @Test
    public void justObservable() {
        int[] arr = {1, 2, 3, 4, 5, 6}; // 发送地址
        // 如果传入null，抛出空指针异常
        Observable.just(1, 2, 3, 4, 5, 6/*null*/)
                .subscribe(System.out::println);
    }

    /**
     * from操作符
     */
    @Test
    public void fromObservable() {
        int[] arr = {1, 2, 3, 4, 5, 6};
        Observable.fromArray(arr)
                .subscribe(ints -> {
                    for (int anInt : ints) {
                        System.out.println(anInt);
                    }
                });

        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            integers.add(i);
        }
        Observable.fromIterable(integers)
                .subscribe(System.out::println);

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(() -> "Hello World");
        Observable.fromFuture(future, 5, TimeUnit.SECONDS)
                .subscribe(System.out::println);
    }

    /**
     * repeat操作符
     * 重复发射原始Observable的数据序列
     */
    @Test
    public void repeatObservable() {
        Observable.just("Hello World")
                .repeat(5)
                .subscribe(System.out::println);
    }

    /**
     * repeatWhen操作符
     * 重新订阅和发射原来的Observable
     */
    @Test
    public void repeatWhenObservable() {
        Observable.range(0, 9)
                .repeatWhen(objectObservable -> Observable.timer(10, TimeUnit.SECONDS))
                .subscribe(System.out::println);

        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * repeatUntil操作符
     * 直到某个条件不再发射数据
     */
    @Test
    public void repeatUntilObservable() {
        final long start = System.currentTimeMillis();
        Observable.interval(500, TimeUnit.MILLISECONDS)
                .take(5)
                .repeatUntil(() -> System.currentTimeMillis() - start > 8000)
                .subscribe(System.out::println);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * defer操作符
     * 每次订阅都产生新的Observable
     */
    @Test
    public void deferObservable() {
        Observable observable = Observable.defer((Callable<ObservableSource<?>>) () -> Observable.just("Hello World"));
        observable.subscribe((Consumer<String>) System.out::println);
    }

    /**
     * interval操作符
     * 固定时间间隔发射整数序列
     */
    @Test
    public void intervalObservable() {
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(System.out::println);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * timer
     * 延迟一段时间后发射一个简单的数字0
     */
    @Test
    public void timerObservable() {
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(aLong -> {
                    System.out.println(aLong);
                    System.out.println("Hello World");
                });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
