package com.zxning.library.manager;

import java.io.IOException;

/**
 * Created by zhy on 15/8/17.
 */
public class MyOkHttpClientManager {

    /*private static MyOkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private Gson mGson;


    private static final String TAG = "OkHttpClientManager";

    private MyOkHttpClientManager() {
        mOkHttpClient = new OkHttpClient();
        //cookie enabled
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mDelivery = new Handler(Looper.getMainLooper());
        mGson = new Gson();
    }

    public static MyOkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (MyOkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new MyOkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    *//**
     * 同步的Get请求
     *
     * @param url
     * @return Response
     *//*
    private Response getSynchronous(String url) throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        Response execute = call.execute();
        return execute;
    }

    *//**
     * 同步的Get请求
     *
     * @param url
     * @return 字符串
     *//*
    private String getStringSyn(String url) throws IOException {
        Response execute = getSynchronous(url);
        return execute.body().string();
    }


    *//**
     * 异步的get请求
     *
     * @param url
     * @param callback
     *//*
    private void getAsynchronous(String url, final ResultCallback callback) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        deliveryResult(callback, request);
    }


    *//**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return
     *//*
    private Response postWay(String url, Param... params) throws IOException {
        Request request = buildPostRequest(url, params);
        Response response = mOkHttpClient.newCall(request).execute();
        return response;
    }


    *//**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return 字符串
     *//*
    private String postWayReceiveString(String url, Param... params) throws IOException {
        Response response = postWay(url, params);
        return response.body().string();
    }

    *//**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     *//*
    private void postWayAsyn(String url, final ResultCallback callback, Param... params) {
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request);
    }

    *//**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     *//*
    private void postWayAsyn(String url, final ResultCallback callback, Map<String, String> params) {
        Param[] paramsArr = map2Params(params);
        Request request = buildPostRequest(url, paramsArr);
        deliveryResult(callback, request);
    }

    *//**
     * 同步基于post的文件上传,多个上传.
     *
     * @param params
     * @return
     *//*
    private Response postFileSyn(String url, File[] files, String[] fileKeys, Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        return mOkHttpClient.newCall(request).execute();
    }

    *//**
     * 同步基于post的文件上传,单个上传.
     * @param url
     * @param file
     * @param fileKey
     * @return
     * @throws IOException
     *//*
    private Response postFileSyn(String url, File file, String fileKey) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        return mOkHttpClient.newCall(request).execute();
    }

    private Response postFileSyn(String url, File file, String fileKey, Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        return mOkHttpClient.newCall(request).execute();
    }

    *//**
     * 异步基于post的文件上传
     *
     * @param url
     * @param callback
     * @param files
     * @param fileKeys
     * @throws IOException
     *//*
    private void postFileAsyn(String url, ResultCallback callback, File[] files, String[] fileKeys, Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        deliveryResult(callback, request);
    }

    *//**
     * 异步基于post的文件上传，单文件不带参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @throws IOException
     *//*
    private void postFileAsyn(String url, ResultCallback callback, File file, String fileKey) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        deliveryResult(callback, request);
    }

    *//**
     * 异步基于post的文件上传，单文件且携带其他form参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @param params
     * @throws IOException
     *//*
    private void postFileAsyn(String url, ResultCallback callback, File file, String fileKey, Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        deliveryResult(callback, request);
    }

    *//**
     * 异步下载文件
     *
     * @param url
     * @param destFileDir 本地文件存储的文件夹
     * @param callback
     *//*
    private void downloadImageAsyn(final String url, final String destFileDir, final ResultCallback callback) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(Response response) {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File file = new File(destFileDir, getFileName(url));
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    //如果下载文件成功，第一个参数为文件的绝对路径
                    sendSuccessResultCallback(file.getAbsolutePath(), callback);
                } catch (IOException e) {
                    sendFailedStringCallback(response.request(), e, callback);
                } finally {
                    try {
                        if (is != null) is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null) fos.close();
                    } catch (IOException e) {
                    }
                }

            }
        });
    }

    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    *//**
     * 加载图片
     *
     * @param view
     * @param url
     * @throws IOException
     *//*
    private void displayImageFromNet(final ImageView view, final String url, final int errorResId) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                setErrorResId(view, errorResId);
            }

            @Override
            public void onResponse(Response response) {
                InputStream is = null;
                try {
                    is = response.body().byteStream();
                    ImageUtils.ImageSize actualImageSize = ImageUtils.getImageSize(is);
                    ImageUtils.ImageSize imageViewSize = ImageUtils.getImageViewSize(view);
                    int inSampleSize = ImageUtils.calculateInSampleSize(actualImageSize, imageViewSize);
                    try {
                        is.reset();
                    } catch (IOException e) {
                        response = getSynchronous(url);
                        is = response.body().byteStream();
                    }

                    BitmapFactory.Options ops = new BitmapFactory.Options();
                    ops.inJustDecodeBounds = false;
                    ops.inSampleSize = inSampleSize;
                    final Bitmap bm = BitmapFactory.decodeStream(is, null, ops);
                    mDelivery.post(new Runnable() {
                        @Override
                        public void run() {
                            view.setImageBitmap(bm);
                        }
                    });
                } catch (Exception e) {
                    setErrorResId(view, errorResId);

                } finally {
                    if (is != null) try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    private void setErrorResId(final ImageView view, final int errorResId) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                view.setImageResource(errorResId);
            }
        });
    }


    /*//*************对外公布的方法************

    *//**
     * 同步get请求获取相应体.
     * @param url
     * @return
     * @throws IOException
     *//*
    public static Response getResponseSyn(String url) throws IOException {
        return getInstance().getSynchronous(url);
    }

    *//**
     *同步get请求获取字符串.
     * @param url
     * @return
     * @throws IOException
     *//*
    public static String getAsStringSyn(String url) throws IOException {
        return getInstance().getStringSyn(url);*//*getAsStringSyn(url);*//*
    }

    *//**
     * 异步的get请求.
     * @param url
     * @param callback
     *//*
    public static void getAsyn(String url, ResultCallback callback) {
        getInstance().getAsynchronous(url, callback);
    }

    *//**
     * 同步post发出,获取响应体.
     * @param url
     * @param params
     * @return
     * @throws IOException
     *//*
    public static Response postSend(String url, Param... params) throws IOException {
        return getInstance().postWay(url, params);
    }

    *//**
     * 同步post发出,获取String.
     * @param url
     * @param params
     * @return
     * @throws IOException
     *//*
    public static String postAsString(String url, Param... params) throws IOException {
        return getInstance().postWayReceiveString(url, params);
    }

    *//**
     *post异步方式发出,Param传入参数.
     * @param url
     * @param callback
     * @param params
     *//*
    public static void postAsynSend(String url, final ResultCallback callback, Param... params) {
        getInstance().postWayAsyn(url, callback, params);
    }

    *//**
     *post异步方式发出.map传入参数.
     * @param url
     * @param callback
     * @param params
     *//*
    public static void postAsynSend(String url, final ResultCallback callback, Map<String, String> params) {
        getInstance().postWayAsyn(url, callback, params);
    }

    *//**
     * 同步基于post的文件上传,多个上传.
     * @param url
     * @param files
     * @param fileKeys
     * @param params
     * @return
     * @throws IOException
     *//*
    public static Response post(String url, File[] files, String[] fileKeys, Param... params) throws IOException {
        return getInstance().postFileSyn(url, files, fileKeys, params);
    }

    *//**
     *同步基于post的文件上传,单个上传.
     * @param url
     * @param file
     * @param fileKey
     * @return
     * @throws IOException
     *//*
    public static Response post(String url, File file, String fileKey) throws IOException {
        return getInstance().postFileSyn(url, file, fileKey);
    }

    //同步基于post的文件上传,单个上传.
    public static Response post(String url, File file, String fileKey, Param... params) throws IOException {
        return getInstance().postFileSyn(url, file, fileKey, params);
    }

    //异步多个上传.
    public static void postAsyn(String url, ResultCallback callback, File[] files, String[] fileKeys, Param... params) throws IOException {
        getInstance().postFileAsyn(url, callback, files, fileKeys, params);
    }

    //异步单独个上传.
    public static void postAsyn(String url, ResultCallback callback, File file, String fileKey) throws IOException {
        getInstance().postFileAsyn(url, callback, file, fileKey);
    }

    //异步单独个上传.带有参数.
    public static void postAsyn(String url, ResultCallback callback, File file, String fileKey, Param... params) throws IOException {
        getInstance().postFileAsyn(url, callback, file, fileKey, params);
    }

    *//**
     *ImageView异步加载图片.
     * @param view
     * @param url
     * @param errorResId  加载出错时候的图片.
     * @throws IOException
     *//*
    public static void displayImage(final ImageView view, String url, int errorResId) throws IOException {
        getInstance().displayImageFromNet(view, url, errorResId);
    }

    //ImageView异步加载图片.
    public static void displayImage(final ImageView view, String url) {
        getInstance().displayImageFromNet(view, url, -1);
    }

    *//**
     * 图片异步下载.
     * @param url
     * @param destDir
     * @param callback
     *//*
    public static void downloadAsyn(String url, String destDir, ResultCallback callback) {
        getInstance().downloadImageAsyn(url, destDir, callback);
    }

    /*//**********以下为一些参数的封装.************

    private Request buildMultipartFormRequest(String url, File[] files,
                                              String[] fileKeys, Param[] params) {
        params = validateParam(params);

        MultipartBuilder builder = new MultipartBuilder()
                .type(MultipartBuilder.FORM);

        for (Param param : params) {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
                    RequestBody.create(null, param.value));
        }
        if (files != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //TODO 根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


    private Param[] validateParam(Param[] params) {
        if (params == null)
            return new Param[0];
        else return params;
    }

    private Param[] map2Params(Map<String, String> params) {
        if (params == null) return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries) {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }

    private static final String SESSION_KEY = "Set-Cookie";
    private static final String mSessionKey = "JSESSIONID";

    private Map<String, String> mSessions = new HashMap<String, String>();

    private void deliveryResult(final ResultCallback callback, Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(final Response response) {
                try {
                    final String string = response.body().string();
                    if (callback.mType == String.class) {
                        sendSuccessResultCallback(string, callback);
                    } else {
                        Object o = mGson.fromJson(string, callback.mType);
                        sendSuccessResultCallback(o, callback);
                    }


                } catch (IOException e) {
                    sendFailedStringCallback(response.request(), e, callback);
                } catch (com.google.gson.JsonParseException e)//Json解析的错误
                {
                    sendFailedStringCallback(response.request(), e, callback);
                }

            }
        });
    }

    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onError(request, e);
            }
        });
    }

    private void sendSuccessResultCallback(final Object object, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResponse(object);
                }
            }
        });
    }

    private Request buildPostRequest(String url, Param[] params) {
        if (params == null) {
            params = new Param[0];
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }


    public static abstract class ResultCallback<T> {
        Type mType;

        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response);
    }

    public static class Param {
        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

        String key;
        String value;
    }*/

}