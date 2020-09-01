package com.xxc.rxjava2test.utils

import io.reactivex.MaybeTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Create By xxc
 * Date: 2020/8/20 15:02
 * Desc:
 */
class RxUtils {

    fun <T> maybeToMain(): MaybeTransformer<T, T> {
        return MaybeTransformer {
            upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

}