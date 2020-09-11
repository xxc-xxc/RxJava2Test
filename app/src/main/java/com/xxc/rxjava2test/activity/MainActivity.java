package com.xxc.rxjava2test.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;
import com.xxc.rxjava2test.R;
import com.xxc.rxjava2test.utils.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.zoom_image)
    PhotoView zoomImage;
    @BindView(R.id.btn_rv)
    Button btnRv;
    private Button mButton;
    private static final int REQ_CROP = 873;
    private static final int REQ_PIC = 434;
    private Uri mImageUri;
    private ImageView mImageView;
    private Uri mSmallUri;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mButton = findViewById(R.id.button);//点击从相册选取照片
        mImageView = findViewById(R.id.imageview);//显示剪裁后的图片
        mButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, REQ_PIC);
        });

        zoomImage.setImageResource(R.drawable.ic_launcher_background);
        // /data/data/com.xxc.rxjava2test/files
        LogUtils.d(TAG, "getFilesDir ===> " + getFilesDir().getAbsolutePath());

        // /data/data/com.xxc.rxjava2test/files
        LogUtils.d(TAG, "getFileStreamPath ===> " + getFileStreamPath(""));

        // /storage/emulated/0/Android/data/com.xxc.rxjava2test/files
        LogUtils.d(TAG, "getExternalFilesDir ===> " + getExternalFilesDir(""));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            LogUtils.d(TAG, "getExternalFilesDirs ===> " + getExternalFilesDirs(""));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // /data/data/com.xxc.rxjava2test/no_backup
            LogUtils.d(TAG, "getNoBackupFilesDir ===> " + getNoBackupFilesDir());
        }
        LogUtils.d(TAG, "========================================");
        // /data/data/com.xxc.rxjava2test/cache
        LogUtils.d(TAG, "getCacheDir ===> " + getCacheDir().getAbsolutePath());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // /data/data/com.xxc.rxjava2test/code_cache
            LogUtils.d(TAG, "getCodeCacheDir ===> " + getCodeCacheDir().getAbsolutePath());
        }

        // /storage/emulated/0/Android/data/com.xxc.rxjava2test/cache
        LogUtils.d(TAG, "getExternalCacheDir ===> " + getExternalCacheDir());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            LogUtils.d(TAG, "getExternalCacheDirs ===> " + getExternalCacheDirs());
        }

        LogUtils.d(TAG, "====================================");
        // 获取外部存储的状态
        LogUtils.d(TAG, "Environment.getExternalStorageState ===> " + Environment.getExternalStorageState());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            LogUtils.d(TAG, "Environment.getExternalStorageState ===> " + Environment.getExternalStorageState(new File("")));
        }
        // 获取内部存储的状态
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            LogUtils.d(TAG, "Environment.getStorageState ===> " + Environment.getStorageState(new File("")));
        }

        // /data
        LogUtils.d(TAG, "Environment.getDataDirectory ===> " + Environment.getDataDirectory());

        // /system
        LogUtils.d(TAG, "Environment.getRootDirectory ===> " + Environment.getRootDirectory());

        // /cache
        LogUtils.d(TAG, "Environment.getDownloadCacheDirectory ===> " + Environment.getDownloadCacheDirectory());

        // /storage/emulated/0/Movies
        LogUtils.d(TAG, "Environment.getExternalStoragePublicDirectory ===> " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES));

        // /storage/emulated/0
        LogUtils.d(TAG, "Environment.getExternalStorageDirectory ===> " + Environment.getExternalStorageDirectory());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /**
             * NotificationManager.IMPORTANCE_UNSPECIFIED：
             * NotificationManager.IMPORTANCE_NONE：关闭通知
             * NotificationManager.IMPORTANCE_MIN：开启通知，不会弹出，没有提示音，状态栏无显示
             * NotificationManager.IMPORTANCE_LOW：开启通知，不会弹出，没有提示音，状态栏显示
             * NotificationManager.IMPORTANCE_DEFAULT：开启通知，不会弹出，有提示音，状态栏显示
             * NotificationManager.IMPORTANCE_HIGH：开启通知，会弹出，有提示音，状态栏显示
             * NotificationManager.IMPORTANCE_MAX：
             */
            Intent intent = new Intent(this, NotificationActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            NotificationChannel notificationChannel = new NotificationChannel("channel_1", "notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
            Notification notification = new Notification.Builder(this, "channel_1")
                    .setContentTitle("通知title")
                    .setContentText("通知内容部分")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setTicker("收到一条新消息")
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent)
                    .build();
            if (notificationManager != null) {
                notificationManager.notify(1, notification);
            }
        } else {
            Intent intent = new Intent(this, NotificationActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = new Notification.Builder(this)
                    .setContentTitle("这是标题") // 通知标题
//                    .setContentText("这是内容") // 通知内容
                    .setSmallIcon(R.mipmap.ic_launcher_round) // 通知小图标
//                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round)) // 通知大图标
                    .setAutoCancel(true) // 点击后自动取消
                    .setWhen(System.currentTimeMillis()) // 通知创建时间
//                    .setSound(Uri.fromFile(new File(""))) // 通知声音
//                    .setVibrate(new long[]{0, 1000, 1000, 1000}) // 设置震动
//                    .setLights(Color.GREEN, 1000, 1000) // 设置通知灯光
//                    .setDefaults(Notification.DEFAULT_ALL) // 所有内容默认
//                    .setPriority(Notification.PRIORITY_MAX) // 通知的优先级
                    .setStyle(new Notification.BigTextStyle().bigText("好多的文本好多的文本好多的文本" +
                            "好多的文本好多的文本好多的文本好多的文本好多的文本好多的文本好多的文本" +
                            "好多的文本好多的文本好多的文本好多的文本好多的文本好多的文本好多的文本"))
//                    .setStyle(new Notification.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)))
                    .setContentIntent(pendingIntent) // 指明一个意图，用于启动activity，service or 发送广播
                    .build();
            notificationManager.notify(1, notification);
//            notificationManager.cancel(1); // 取消通知
        }

//        new NotificationUtils(this).sendNotificationFullScreen("这是title", "这是内容", "这是类型");

    }

    private void crop() {

        /*新建用于存剪裁后图片的文件，并转化为Uri*/
        File imageFile = createImageFile();
        mSmallUri = Uri.fromFile(imageFile);
        Log.i(TAG, "crop: " + mSmallUri);

        /*File image = new File(getExternalCacheDir() + "/demo.jpg");
        Log.i(TAG, "crop: path " + image.getAbsolutePath());
        mSmallUri = Uri.fromFile(image);*/

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(mImageUri, "image/*");
        intent.putExtra("aspectX", 768);
        intent.putExtra("aspectY", 1024);
        intent.putExtra("outputX", 768);
        intent.putExtra("outputY", 1024);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);//设置为不返回缩略图
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mSmallUri);//设置大图保存到文件
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//保存的图片格式
        intent.putExtra("noFaceDetection", false);

        startActivityForResult(intent, REQ_CROP);
    }

    /*缩略图*/
    private void cropAndThumbnail() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(mImageUri, "image/*");//设置要缩放的图片Uri和类型
        intent.putExtra("aspectX", 768);//宽度比
        intent.putExtra("aspectY", 1024);//高度比
        intent.putExtra("outputX", 768);//输出图片的宽度
        intent.putExtra("outputY", 1024);//输出图片的高度
        intent.putExtra("scale", true);//缩放
        intent.putExtra("return-data", false);//当为true的时候就返回缩略图，false就不返回，需要通过Uri
        intent.putExtra("noFaceDetection", false);//前置摄像头

        startActivityForResult(intent, REQ_CROP);
    }

    /*用时间创建图片文件，防重名*/
    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = null;
        try {
            imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQ_PIC) {//选取图片
            mImageUri = data.getData();
            crop();
        } else if (requestCode == REQ_CROP) {//剪裁
            /*缩略图*/
            /*if(data!=null){
                Bundle bundle = data.getExtras();
                Bitmap bitmap = bundle.getParcelable("data");
                mImageView.setImageBitmap(bitmap);
                Log.i(TAG, "onActivityResult: smallUri "+mSmallUri);
            }*/
            /*原图*/
            try {
                if (mSmallUri != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mSmallUri));
                    mImageView.setImageBitmap(bitmap);
                } else {
                    Log.i(TAG, "onActivityResult: Uri is null");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick({R.id.btn_rv, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_rv:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.button:
                break;
        }
    }
}

