package com.xxc.rxjava2test;

import android.os.Environment;

import com.xxc.rxjava2test.utils.LogUtils;
import com.xxc.rxjava2test.utils.MD5;

import org.junit.Test;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static final String TAG = ExampleUnitTest.class.getSimpleName();

    @Test
    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
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

    @Test
    public void doOperator() {
        Observable.just("Hello")
                // doNext之前执行
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtils.d(TAG, "doOnNext:" + s);
                    }
                })
                // doNext之后执行
                .doAfterNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtils.d(TAG, "doAfterNext:" + s);
                    }
                })
                // 正常调用onComplete时调用
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        LogUtils.d(TAG, "doOnComplete:");
                    }
                })
                // 订阅之后回调的方法
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        LogUtils.d(TAG, "doOnSubscribe:");
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        LogUtils.d(TAG, "doAfterTerminate:");
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        LogUtils.d(TAG, "doFinally:");
                    }
                })
                // 每次发射数据都会触发的回调
                .doOnEach(new Consumer<Notification<String>>() {
                    @Override
                    public void accept(Notification<String> stringNotification) throws Exception {
                        LogUtils.d(TAG, "doFinally:");
                    }
                })
                // 订阅和取消订阅触发的回调
                .doOnLifecycle(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        LogUtils.d(TAG, "doOnLifecycle:" + disposable.isDisposed());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        LogUtils.d(TAG, "doOnLifecycle: run");
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtils.d(TAG, "收到消息:" + s);
                    }
                });
    }

    @Test
    public void timestamp() {
        System.out.println(System.currentTimeMillis() / 1000);
    }

    @Test
    public void md5() {
        byte[] bytes = new byte[1024];
        System.out.println(MD5.getMessageDigest(bytes));
    }

}