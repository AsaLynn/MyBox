package com.zxning.library.tool;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.zxning.library.manager.ThreadManager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 保存文件的工具类.
 */
public class FileUtils {


    /**
     * 手机的缓存根目录files
     */
    public static String getFilesDirPath(Context context) {
        return context.getFilesDir().getPath();
    }

    /**
     * 手机的缓存根目录Cache
     */
    public static String getCacheDirPath(Context context) {
        return context.getCacheDir().getPath();
    }

    /**
     * sd卡的根目录
     */
    public static String getSdRootPath() {
        String mSdRootPath = Environment.getExternalStorageDirectory().getPath();
        return mSdRootPath;
    }

    /**
     * 获取储存的目录
     *
     * @return
     */
    private static String getStorageDirectoryPath(String folderName) {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?
                getSdRootPath()+File.separator + folderName : getCacheDirPath(UIUtils.getContext()) +File.separator + folderName;
    }

    public static String getFileDirectory(String folderName, String fileName) {
        return getStorageDirectoryPath(folderName) + File.separator + fileName;
    }

    /**
     * 判断文件是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean isFileExists(String folderName, String fileName) {
        return new File(getStorageDirectoryPath(folderName) + File.separator + fileName).exists();
    }

    /**
     * 将asset目录下的文件保存到sd卡上.
     *
     * @param context    上下文.
     * @param folderName 文件夹名称.
     * @param fileName   文件名称.
     * @throws Exception
     */
    public static void getFileFromAssetToSDcard(Context context, String folderName, String fileName) throws Exception {
        //得到资源
        AssetManager am = context.getAssets();
        //得到数据库的输入流
        InputStream is = am.open(fileName);
        saveFile(folderName, fileName, is);
    }

    /**
     * 将asset目录下的图片保存到sd卡上.
     *
     * @param context
     * @param fileName
     * @throws Exception
     */
    public static void getPicFromAssetToSDcard(Activity context, String fileName) throws Exception {
        //得到资源
        AssetManager am = context.getAssets();
        //得到数据库的输入流
        InputStream is = am.open(fileName);
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        savePic(bitmap, Environment.getExternalStorageDirectory().getPath() + File.separator, fileName);
    }

    /**
     * 保存字符串到SD卡
     *
     * @param filename
     * @param filecontent
     * @throws Exception
     */
    public static void saveToSDCard(String filename, String filecontent) throws Exception {
        File file = new File(Environment.getExternalStorageDirectory(), filename);
        FileOutputStream outStream = new FileOutputStream(file);
        outStream.write(filecontent.getBytes());
        outStream.close();
    }

    /**
     * 创建文件.
     *
     * @param filePath 文件的路径.
     * @param fileName 文件的名称.
     * @return
     */
    public static File createFile(String filePath, String fileName) {
        // File file = new File(KxdPath.DOWNLOADPATH + KxdPath.IMAGE_FILE_NAME_TEMP);
        File file = new File(filePath + fileName);
        return file;
    }

    /**
     * 保存文件到sd卡上.
     *
     * @param folderName 文件夹名称.
     * @param fileName   文件名称.
     * @param is         输入流.
     * @throws IOException
     */
    public static void saveFile(String folderName, String fileName, InputStream is) throws IOException {
        String path = getStorageDirectoryPath(folderName);
        File folderFile = new File(path);
        if (!folderFile.exists()) {
            folderFile.mkdir();
        }

        File fileFile = new File(path + File.separator + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileFile));
        byte[] buffer = new byte[1024];
        int count;
        while ((count = is.read(buffer)) > 0) {
            bos.write(buffer, 0, count);
        }
        bos.flush();
        bos.close();
        is.close();
    }

    /**
     * 保存图片.
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static void savePic(Bitmap bm, String filePath, String fileName) throws IOException {

        File dirFile = new File(filePath);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }

        File myCaptureFile = new File(filePath + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }

    //删除文件.子线程.
    public static void deleteFile(String filePath, String fileName) throws IOException {
        final File tempFile = new File(filePath + fileName);
        if (tempFile.exists()) {
            ThreadManager.getThreadPoolProxy().execute(new Runnable() {
                @Override
                public void run() {
                    tempFile.delete();
                }
            });
        }
    }

    //删除文件.主线程.
    public static void delFile(String filePath, String fileName) throws IOException {
        final File tempFile = new File(filePath + fileName);
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    /**
     * 删除SD卡指定目录下的文件.
     */
    public static void deleteFiles(String folderName) {
        File dirFile = new File(getStorageDirectoryPath(folderName));
        if (!dirFile.exists()) {
            return;
        }
        if (dirFile.isDirectory()) {
            String[] children = dirFile.list();
            for (int i = 0; i < children.length; i++) {
                new File(dirFile, children[i]).delete();
            }
        }
    }

    /**
     * 删除SD卡或者手机的缓存图片和目录
     */
    public static void deleteFile(String folderName) {
        File dirFile = new File(getStorageDirectoryPath(folderName));
        if (!dirFile.exists()) {
            return;
        }
        if (dirFile.isDirectory()) {
            String[] children = dirFile.list();
            for (int i = 0; i < children.length; i++) {
                new File(dirFile, children[i]).delete();
            }
        }

        dirFile.delete();
    }

    /**
     * 获取文件的大小
     *
     * @param fileName
     * @return
     */
    public long getFileSize(String folderName, String fileName) {
        return new File(getStorageDirectoryPath(folderName) + File.separator + fileName).length();
    }

    /**
     * 从手机或者sd卡获取Bitmap
     *
     * @param fileName
     * @return
     */
    public static Bitmap getBitmap(String folderName, String fileName) {
        return BitmapFactory.decodeFile(getStorageDirectoryPath(folderName) + File.separator + fileName);
    }

    /**
     * 保存Image的方法，有sd卡存储到sd卡，没有就存储到手机目录
     *
     * @param fileName
     * @param bitmap
     * @throws IOException
     */
    public static void savaBitmap(String folderName, String fileName, Bitmap bitmap) throws IOException {
        if (bitmap == null) {
            return;
        }
        String path = getStorageDirectoryPath(folderName);
        File folderFile = new File(path);
        if (!folderFile.exists()) {
            folderFile.mkdir();
        }
        File file = new File(path + File.separator + fileName);
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();
    }


}
