package com.xxc.rxjava2test.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding3.view.RxView;
import com.xxc.rxjava2test.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RxBindingActivity extends AppCompatActivity {

    private static final String TAG = RxBindingActivity.class.getSimpleName();
    @BindView(R.id.rx_binding)
    Button rxBinding;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_binding);
        ButterKnife.bind(this);
        RxView.clicks(rxBinding)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(unit -> {
                    /** 倒计时60秒，一次1秒 */
                    CountDownTimer timer = new CountDownTimer(60*1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            rxBinding.setClickable(false);
                            rxBinding.setText("还剩"+millisUntilFinished/1000+"秒");
                        }
                        @Override
                        public void onFinish() {
                            rxBinding.setClickable(true);
                            rxBinding.setText("重新获取验证码");
                        }
                    }.start();
                });
    }
}