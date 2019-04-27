package com.example.mybox.activity;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.example.mybox.transform.GlideRoundTransform;
import com.zxning.library.tool.UIUtils;

import java.io.File;

import static com.example.mybox.R.id.btn1;
import static com.example.mybox.R.id.btn2;
import static com.example.mybox.R.id.btn3;

/**
 * Glide图片加载库
 * 一个专注于平滑滚动的图片加载和缓存库
 * Glide在缓存策略和加载GIF方面略胜一筹
 * 在泰国举行的谷歌开发者论坛上，谷歌为我们介绍了一个名叫 Glide 的图片加载库，作者是bumptech。
 * 这个库被广泛的运用在google的开源项目中，包括2014年google I/O大会上发布的官方app。
 * Glide 是 Google 员工的开源项目， Google I/O 上被推荐使用，一个高效、开源、Android设备上的媒体管理框架，
 * 它遵循BSD、MIT以及Apache 2.0协议发布。Glide具有获取、解码和展示视频剧照、图片、动画等功能，
 * 它还有灵活的API，这些API使开发者能够将Glide应用在几乎任何网络协议栈里。创建Glide的主要目的有两个，
 * 一个是实现平滑的图片列表滚动效果，另一个是支持远程图片的获取、大小调整和展示。
 * gitHub地址：https://github.com/bumptech/glide
 * Glide特点
 * 使用简单，可配置度高，自适应程度高，支持常见图片格式 Jpg png gif webp，网络、本地、资源、Assets 等，
 * 支持Memory和Disk图片缓存 默认Bitmap格式采用RGB_565内存使用至少减少一半
 * 根据Activity/Fragment生命周期自动管理请求
 * 使用Bitmap Pool使Bitmap复用，主动调用recycle回收需要回收的Bitmap，减小系统回收压力
 * Glide简单使用
 * 1，添加引用 build.gradle 中添加配置
 * compile 'com.github.bumptech.glide:glide:3.8.0'
 * compile 'com.android.support:support-v4:22.0.0'
 * Glide需要依赖Support Library v4
 * 2,设置绑定生命周期
 * 我们可以更加高效的使用Glide提供的方式进行绑定，这样可以更好的让加载图片的请求的生命周期动态管理起来
 * Glide.with(Context context);// 绑定Context
 * Glide.with(Activity activity);// 绑定Activity
 * Glide.with(FragmentActivity activity);// 绑定FragmentActivity
 * Glide.with(Fragment fragment);// 绑定Fragment
 * 3,简单的加载图片实例
 * Glide.with(this).load(imageUrl).into(imageView);
 * 4,设置加载中以及加载失败图片
 * Glide.with(this).load(imageUrl).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageView);
 * 5,设置跳过内存缓存
 * Glide.with(this).load(imageUrl).skipMemoryCache(true).into(imageView);
 * 6,设置下载优先级
 * Glide.with(this).load(imageUrl).priority(Priority.NORMAL).into(imageView);
 * 7,设置缓存策略
 * Glide.with(this).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
 * 策略解说：
 * all:缓存源资源和转换后的资源
 * none:不作任何磁盘缓存
 * source:缓存源资源
 * result：缓存转换后的资源
 * 8,设置加载动画
 * api也提供了几个常用的动画：比如crossFade()
 * R.anim.iv_an_alpha_in
 * <?xml version="1.0" encoding="utf-8"?>
 * <set xmlns:android="http://schemas.android.com/apk/res/android">
 * <alpha
 * android:duration="500"
 * android:fromAlpha="0.0"
 * android:toAlpha="1.0"/>
 * </set>
 * <p>
 * Glide.with(this).load(imageUrl).animate(R.anim.iv_an_alpha_in).into(imageView);
 * 9.）设置缩略图支持
 * 这样会先加载缩略图 然后在加载全图
 * Glide.with(this).load(imageUrl).thumbnail(0.1f).into(imageView);
 * 10.）设置加载尺寸
 * Glide.with(this).load(imageUrl).override(800, 800).into(imageView);
 * 11.）设置动态转换
 * Glide.with(this).load(imageUrl).centerCrop().into(imageView);
 * api提供了比如：centerCrop()、fitCenter()等函数也可以通过自定义Transformation，举例说明：比如一个人圆角转化器
 * public class GlideRoundTransform extends BitmapTransformation {
 * private float radius = 0f;
 * public GlideRoundTransform(Context context) {
 * this(context, 4);
 * }
 * <p>
 * public GlideRoundTransform(Context context, int dp) {
 * super(context);
 * this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
 * }
 *
 * @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
 * return roundCrop(pool, toTransform);
 * }
 * <p>
 * private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
 * if (source == null) return null;
 * <p>
 * Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
 * if (result == null) {
 * result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
 * }
 * Canvas canvas = new Canvas(result);
 * Paint paint = new Paint();
 * paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
 * paint.setAntiAlias(true);
 * RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
 * canvas.drawRoundRect(rectF, radius, radius, paint);
 * return result;
 * }
 * @Override public String getId() {
 * return getClass().getName() + Math.round(radius);
 * }
 * }
 * <p>
 * 12.）设置要加载的内容
 * 项目中有很多需要先下载图片然后再做一些合成的功能，比如项目中出现的图文混排，该如何实现目标下
 * Glide.with(this).load(imageUrl).centerCrop().into(new SimpleTarget<GlideDrawable>() {
 * @Override public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
 * imageView.setImageDrawable(resource);
 * }
 * });
 * 13 .）设置监听请求接口
 * Glide.with(this).load(imageUrl).listener(new RequestListener<String, GlideDrawable>() {
 * @Override public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
 * return false;
 * }
 * @Override public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
 * //imageView.setImageDrawable(resource);
 * return false;
 * }
 * }).into(imageView);
 * 15.)设置动态GIF加载方式
 * Glide.with(this).load(imageUrl).asBitmap().into(imageView);//显示gif静态图片
 * Glide.with(this).load(imageUrl).asGif().into(imageView);//显示gif动态图片
 * 16.）缓存的动态清理
 * Glide.get(this).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
 * Glide.get(this).clearMemory();//清理内存缓存  可以在UI主线程中进行
 * 干我们这行，啥时候懈怠，就意味着长进的停止，长进的停止就意味着被淘汰，只能往前冲，直到凤凰涅槃的一天！
 */
public class GlideDemoActivity extends BaseActivity {

    private String imageUrl;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private String imageUrl4;
    private String url;
    private String url2;
    private String strategy1;
    private String strategy2;
    private String strategy3;
    private String strategy4;
    private String gifUrl2;
    private String textImgUrl;
    private ImageView imageView2,imageView3,iv_tran,iv_glide_p1,iv_glide_p2,iv_glide_p3,iv_glide_p4,imageView,
    iv_glide_strategy1,iv_glide_strategy2,iv_glide_strategy3,iv_glide_strategy4,iv_glide_am,iv_glide_th,iv_cut1,
            iv_cut2, iv_img_text, iv_lis, iv_gif, iv_gif_times, iv_gif_location, ivVideo,iv_location_img;
    private boolean isAsGif;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(R.layout.activity_g);
        //设置绑定生命周期，Context，Activity,Fragment
        //Glide.with(context);

        view.findViewById(R.id.btn_easy).setOnClickListener(this);
        imageView = (ImageView) view.findViewById(R.id.iv_glide);

        imageUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503416868501&di=b9eac80cf49dbd589fb743a7c1a64ce7&imgtype=0&src=http%3A%2F%2Fimg1.3lian.com%2F2015%2Fw7%2F78%2Fd%2F92.jpg";

        imageView2 = (ImageView) view.findViewById(R.id.iv_glide2);
        view.findViewById(R.id.btn_error_load).setOnClickListener(this);
        url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503416868498&di=f1a3f78e1936a047575d36244766e6db&imgtype=0&src=http%3A%2F%2Fpic19.nipic.com%2F20120326%2F9508449_111033616381_2.jpg";

        imageView3 = (ImageView) view.findViewById(R.id.iv_glide3);
        view.findViewById(R.id.btn_skip).setOnClickListener(this);
        url2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503416868498&di=1252b307afed754e74ea6caa56cc6425&imgtype=0&src=http%3A%2F%2Fimg1.3lian.com%2F2015%2Fa2%2F244%2Fd%2F277.jpg";

        view.findViewById(btn1).setOnClickListener(this);
        iv_glide_p1 = (ImageView) view.findViewById(R.id.iv_glide_p1);
        iv_glide_p2 = (ImageView) view.findViewById(R.id.iv_glide_p2);
        iv_glide_p3 = (ImageView) view.findViewById(R.id.iv_glide_p3);
        iv_glide_p4 = (ImageView) view.findViewById(R.id.iv_glide_p4);

        imageUrl1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503416868501&di=07fea3d9fc344b120781975f7f4757a0&imgtype=0&src=http%3A%2F%2Fpic30.nipic.com%2F20130614%2F12251844_092330611150_2.jpg";
        imageUrl2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503416868499&di=f02f68a0b06a19a6ea67ed0e0537f4b5&imgtype=0&src=http%3A%2F%2Fpic14.nipic.com%2F20110503%2F6517206_154402089519_2.jpg";
        imageUrl3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503416868498&di=794090ad7dbb2a3875a43ef86c4f5794&imgtype=0&src=http%3A%2F%2Fimg5q.duitang.com%2Fuploads%2Fitem%2F201503%2F22%2F20150322171845_hvQYR.thumb.700_0.jpeg";
        imageUrl4 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503416868498&di=5c79ae4fa19f82acf3bb8db65153be35&imgtype=0&src=http%3A%2F%2Fpic.nipic.com%2F2008-03-22%2F200832216521779_2.jpg";

        iv_glide_strategy1 = (ImageView) view.findViewById(R.id.iv_glide_strategy1);
        iv_glide_strategy2 = (ImageView) view.findViewById(R.id.iv_glide_strategy2);
        iv_glide_strategy3 = (ImageView) view.findViewById(R.id.iv_glide_strategy3);
        iv_glide_strategy4 = (ImageView) view.findViewById(R.id.iv_glide_strategy4);

        strategy1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503416868501&di=07fea3d9fc344b120781975f7f4757a0&imgtype=0&src=http%3A%2F%2Fpic30.nipic.com%2F20130614%2F12251844_092330611150_2.jpg";
        strategy2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503416868499&di=f02f68a0b06a19a6ea67ed0e0537f4b5&imgtype=0&src=http%3A%2F%2Fpic14.nipic.com%2F20110503%2F6517206_154402089519_2.jpg";
        strategy3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503416868498&di=794090ad7dbb2a3875a43ef86c4f5794&imgtype=0&src=http%3A%2F%2Fimg5q.duitang.com%2Fuploads%2Fitem%2F201503%2F22%2F20150322171845_hvQYR.thumb.700_0.jpeg";
        strategy4 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503416868498&di=5c79ae4fa19f82acf3bb8db65153be35&imgtype=0&src=http%3A%2F%2Fpic.nipic.com%2F2008-03-22%2F200832216521779_2.jpg";
        view.findViewById(btn2).setOnClickListener(this);
        ;

        view.findViewById(btn3).setOnClickListener(this);
        iv_glide_am = (ImageView) view.findViewById(R.id.iv_glide_am);

        view.findViewById(R.id.btn_thu).setOnClickListener(this);
        iv_glide_th = (ImageView) view.findViewById(R.id.iv_glide_th);

        iv_cut1 = (ImageView) view.findViewById(R.id.iv_cut1);
        iv_cut2 = (ImageView) view.findViewById(R.id.iv_cut2);
        view.findViewById(R.id.btn_cut).setOnClickListener(this);


        iv_tran = (ImageView) view.findViewById(R.id.iv_tran);
        view.findViewById(R.id.btn_tran).setOnClickListener(this);

        textImgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503509784615&di=f4f7182b6e3da26faefee0da3ee90d08&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D3622443005%2C1347645608%26fm%3D214%26gp%3D0.jpg";
        view.findViewById(R.id.btn_img_text).setOnClickListener(this);
        iv_img_text = (ImageView) view.findViewById(R.id.iv_text);

        view.findViewById(R.id.btn_glide_lis).setOnClickListener(this);
        iv_lis = (ImageView) view.findViewById(R.id.iv_lis);

        view.findViewById(R.id.btn_gif).setOnClickListener(this);
        iv_gif = (ImageView) view.findViewById(R.id.iv_gif);
        gifUrl2 = "http://image.haha.mx/2014/02/02/middle/1115779_c221d1fc47b97bb1605cddc9c8aec0a7_1391347675.gif";

        iv_gif_times = (ImageView) view.findViewById(R.id.iv_gif_times);
        view.findViewById(R.id.btn_gif_times).setOnClickListener(this);

        iv_gif_location = (ImageView) view.findViewById(R.id.iv_gif_location);
        view.findViewById(R.id.btn_gif_location).setOnClickListener(this);


        ivVideo = (ImageView) view.findViewById(R.id.iv_video);
        view.findViewById(R.id.btn_video).setOnClickListener(this);

        iv_location_img = (ImageView) view.findViewById(R.id.iv_location_img);
        view.findViewById(R.id.btn_location_img).setOnClickListener(this);

        //加载本地视频缩略图
//        mVideoFile = new File(Environment.getExternalStorageDirectory(), "xiayu.mp4");
//        Glide.with(this).load(mVideoFile).placeholder(R.mipmap.place).error(R.mipmap.icon_photo_error).into(mIv);

        //

        // 16.）缓存的动态清理
        // Glide.get(this).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
        // Glide.get(this).clearMemory();//清理内存缓存  可以在UI主线程中进行

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_easy:
                //加载图片实例,未设置跳过内存缓存.
                if (imageView.getVisibility() != View.VISIBLE) {
                    imageView.setVisibility(View.VISIBLE);
                }
                Glide.with(this).load(imageUrl).into(imageView);
                break;
            case R.id.btn_error_load:
                //设置加载中以及加载失败图片
                if (imageView2.getVisibility() != View.VISIBLE) {
                    imageView2.setVisibility(View.VISIBLE);
                }
                Glide.with(this).load(url).skipMemoryCache(true).placeholder(R.drawable.pb_d_github_loading_outer).error(R.mipmap.iv_m_img_no_data).into(imageView2);
                break;
            case R.id.btn_skip:
                //设置跳过内存缓存skipMemoryCache(true)
                if (imageView3.getVisibility() != View.VISIBLE) {
                    imageView3.setVisibility(View.VISIBLE);
                }
                Glide.with(this).load(url2).skipMemoryCache(true).into(imageView3);
                break;
            case btn1:
                //设置下载优先级
                if (iv_glide_p1.getVisibility() != View.VISIBLE) {
                    iv_glide_p1.setVisibility(View.VISIBLE);
                }
                if (iv_glide_p2.getVisibility() != View.VISIBLE) {
                    iv_glide_p2.setVisibility(View.VISIBLE);
                }
                if (iv_glide_p3.getVisibility() != View.VISIBLE) {
                    iv_glide_p3.setVisibility(View.VISIBLE);
                }
                if (iv_glide_p4.getVisibility() != View.VISIBLE) {
                    iv_glide_p4.setVisibility(View.VISIBLE);
                }
                //NORMAL,HIGH,IMMEDIATE,LOW
                Glide.with(this).load(imageUrl1).skipMemoryCache(true).priority(Priority.NORMAL).into(iv_glide_p1);
                Glide.with(this).load(imageUrl2).skipMemoryCache(true).priority(Priority.HIGH).into(iv_glide_p2);
                Glide.with(this).load(imageUrl3).skipMemoryCache(true).priority(Priority.IMMEDIATE).into(iv_glide_p3);
                Glide.with(this).load(imageUrl4).skipMemoryCache(true).priority(Priority.LOW).into(iv_glide_p4);
                break;
            case btn2:
                if (iv_glide_strategy1.getVisibility() != View.VISIBLE) {
                    iv_glide_strategy1.setVisibility(View.VISIBLE);
                }
                if (iv_glide_strategy2.getVisibility() != View.VISIBLE) {
                    iv_glide_strategy2.setVisibility(View.VISIBLE);
                }
                if (iv_glide_strategy3.getVisibility() != View.VISIBLE) {
                    iv_glide_strategy3.setVisibility(View.VISIBLE);
                }
                if (iv_glide_strategy4.getVisibility() != View.VISIBLE) {
                    iv_glide_strategy4.setVisibility(View.VISIBLE);
                }
                //设置缓存策略
                // 策略解说：
                //  none:不缓存图片
                //  source:缓存图片源文件
                //  result：缓存修改过的图片 (最终的转换后的数据)
                // all:缓存所有的图片,默认.
                Glide.with(this).load(strategy1).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_glide_strategy1);
                Glide.with(this).load(strategy2).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv_glide_strategy2);
                Glide.with(this).load(strategy3).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_glide_strategy3);
                Glide.with(this).load(strategy4).diskCacheStrategy(DiskCacheStrategy.RESULT).into(iv_glide_strategy4);
                break;
            case btn3:
                //设置加载动画
                if (iv_glide_am.getVisibility() != View.VISIBLE) {
                    iv_glide_am.setVisibility(View.VISIBLE);
                }
                Glide.with(this).load(imageUrl).skipMemoryCache(true).animate(R.anim.iv_an_alpha_in).into(iv_glide_am);
                break;
            case R.id.btn_thu:
                //设置加载缩略图,这样会先加载缩略图 然后在加载全图
                if (iv_glide_th.getVisibility() != View.VISIBLE) {
                    iv_glide_th.setVisibility(View.VISIBLE);
                }
                Glide.with(this).load(imageUrl).skipMemoryCache(true).thumbnail(0.1f).into(iv_glide_th);
                break;
            case R.id.btn_cut:
                if (iv_cut1.getVisibility() != View.VISIBLE) {
                    iv_cut1.setVisibility(View.VISIBLE);
                }
                if (iv_cut2.getVisibility() != View.VISIBLE) {
                    iv_cut2.setVisibility(View.VISIBLE);
                }
                Glide.with(this).load(imageUrl1).skipMemoryCache(true).override(400, 400).into(iv_cut1);
                Glide.with(this).load(imageUrl1).skipMemoryCache(true).override(800, 800).into(iv_cut2);
                break;
            case R.id.btn_tran:
                //设置动态转换.
                if (iv_tran.getVisibility() != View.VISIBLE) {
                    iv_tran.setVisibility(View.VISIBLE);
                }
                //Glide.with(this).load(imageUrl).centerCrop().skipMemoryCache(true).into(iv_tran);
                Glide.with(this).load(imageUrl).skipMemoryCache(true).transform(new GlideRoundTransform(this)).into(iv_tran);
                break;
            case R.id.btn_img_text:
                //设置要加载的内容
                // 项目中有很多需要先下载图片然后再做一些合成的功能，比如项目中出现的图文混排，该如何实现目标下
                if (iv_img_text.getVisibility() != View.VISIBLE) {
                    iv_img_text.setVisibility(View.VISIBLE);
                }
                Glide.with(this).load(textImgUrl).centerCrop().into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        iv_img_text.setImageDrawable(resource);
                    }
                });
                break;
            case R.id.btn_glide_lis:
                //设置监听请求接口
                Glide.with(this).load(url2).listener(new RequestListener<String, GlideDrawable>() {
                    //加载异常监听.
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    //加载成功后监听
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        //imageView.setImageDrawable(resource);
                        imageView.setVisibility(View.VISIBLE);
                        return false;
                    }
                }).into(iv_lis);
                break;
            case R.id.btn_gif:
                //设置动态GIF加载方式
                if (iv_gif.getVisibility() != View.VISIBLE) {
                    iv_gif.setVisibility(View.VISIBLE);
                }
                //Glide.with(this).load(gifUrl).asGif().into(iv_gif);//显示gif动态图片

                //String gifUrl2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503512969858&di=f8d6150ff7680ac5cdaad23417ff1725&imgtype=0&src=http%3A%2F%2Fm.qqzhi.com%2Fupload%2Fimg_1_3442429221D2995392056_15.jpg";

                //Glide.with(this).load(gifUrl2).asGif().into(iv_gif);//显示gif动态图片
                if (isAsGif) {
                    Glide.with(this).load(gifUrl2).asGif().into(iv_gif);//显示gif动态图片
                    isAsGif = false;
                } else {
                    Glide.with(this).load(gifUrl2).asBitmap().into(iv_gif);//显示gif静态图片
                    isAsGif = true;
                }
                break;

            case R.id.btn_gif_times:
                //设置gif播放次数：
                //这样写会报错,设置播放次数和播放监听的时候，不应加上.asGif().
                //Glide.with(MainActivity.this).load(url).asGif().into(new GlideDrawableImageViewTarget(imageview, 1));
                //正确写法:GlideDrawableImageViewTarget(ImageView view, int maxLoopCount)
                Glide.with(this).load(gifUrl2).into(new GlideDrawableImageViewTarget(iv_gif_times, 2));
                break;
            case R.id.btn_gif_location:
                //设置本地gif播放.
                Glide.with(this).load(R.drawable.iv_d_mao).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_gif_location);
                break;
            case R.id.btn_video:
//                //设置加载视频的缩略图
                //sd卡根目录下,tmp文件夹下面.
                String filePath = "/storage/emulated/0/tmp/VID0824.mp4";
                Glide.with(this).load(Uri.fromFile(new File(filePath))).into(ivVideo);
                break;
            case R.id.btn_location_img:
//                //设置加载sd卡的图片,从文件中加载图片.
                //sd卡根目录下,tmp文件夹下面.
                if (iv_location_img.getVisibility() != View.VISIBLE){
                    iv_location_img.setVisibility(View.VISIBLE);
                }
                //--->/storage/emulated/0/Pictures/foot.jpg
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"foot.jpg");
                Log.i(this.getClass().getName(),"--->"+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
                Glide.with(this).load(file).into(iv_location_img);
                break;
        }
    }
}
