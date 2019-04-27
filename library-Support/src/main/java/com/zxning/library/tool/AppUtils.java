package com.zxning.library.tool;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

/**
 * 用于调用系统方法.
 */
public class AppUtils {

    public static void startPhotoZoom(Uri uri, Activity activity,int requestCode) {
        Intent intent = getIntentForPhotoZoom(uri);
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * 调用系统裁剪图片
     *
     * @param uri
     */
    public static void startPhotoZoom(Uri uri, android.support.v4.app.Fragment fragment,int requestCode) {
        Intent intent = getIntentForPhotoZoom(uri);
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 打开系统裁剪的意图.
     *
     * @param uri
     * @return
     */
    public static Intent getIntentForPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", UIUtils.px2dip(100));
        intent.putExtra("outputY", UIUtils.px2dip(100));
        intent.putExtra("return-data", true);
        return intent;
    }


    // 打开手机照相机
    public static void openCamera(android.support.v4.app.Fragment fragment, int requestCode, File file) {
        // 打开相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用,存储缓存图片
        if (AppUtils.hasSdcard()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        }
        fragment.startActivityForResult(intent, requestCode);
    }

    // 打开手机照相机,
    public static void openCamera(Activity activity, int requestCode, File file) {
        // 打开相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用,存储缓存图片
        if (AppUtils.hasSdcard()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        }
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * @return true表示 有sdcard, false表示没有sdcard
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    //打开手机系统的相册
    public static void openMobilePhotoAlbumOn(android.support.v4.app.Fragment fragment, int requestCode) {// openMobilePhotoAlbum
        Intent intentFromGallery = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentFromGallery.setType("image/*"); // 设置文件类型
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        fragment.startActivityForResult(intentFromGallery, requestCode);
    }

    public static void openMobilePhotoAlbum(Activity activity, int requestCode) {// openMobilePhotoAlbum,
        Intent intentFromGallery = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentFromGallery.setType("image/*"); // 设置文件类型
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(intentFromGallery, requestCode);
    }

}
