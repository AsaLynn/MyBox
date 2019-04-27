package com.example.mybox.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mybox.R;
import com.example.mybox.base.BaseRecyclerActivity;
import com.zxning.library.entity.PlateInfo;
import com.zxning.library.tool.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 经常使用的一些对话框.
 */
public class DialogActivity extends BaseRecyclerActivity {

    String[] names = {"确认对话框-->", "单选对话框-->", "多选对话框-->", "列表对话框-->", "自定义对话框-->"};
    private String[] sexList={"男","女"};//单选列表
    private String[] likeList={"篮球","足球","打游戏","听音乐","看电影"};//多选列表
    private String[] itemList={"项目经理","策划","测试","美工","程序员"};


    @Override
    protected List<PlateInfo> getDatas() {
        List<PlateInfo> infos = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            PlateInfo info = new PlateInfo();
            info.name = names[i] + i;
            infos.add(info);
        }
        return infos;
    }

    @Override
    public void onRVItemClick(ViewGroup viewGroup, View view, int i) {
        switch (i) {
            case 0:
                //确认对话框
                showDialog1();
                break;
            case 1:
                //单选对话框
                showDialog2();
                break;
            case 2:
                //多选对话框
                showDialog3();
                break;
            case 3:
                //列表对话框
                showDialog4();
                break;
            case 4:
                //自定义对话框
                showDialog5();
                break;
        }
    }

    /*自定义对话框*/
    private void showDialog5() {
        LayoutInflater inflater=LayoutInflater.from(this);
        View view=inflater.inflate(R.layout.dialog,null);//获取自定义布局
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("自定义对话框");//设置标题
        builder.setIcon(R.drawable.ic_launcher);//设置图标
        builder.setView(view);//设置自定义样式布局到对话框
        AlertDialog dialog=builder.create();//获取dialog
        dialog.show();//显示对话框
    }


    /*列表对话框*/
    private void showDialog4() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("部门列表");//设置标题
        builder.setIcon(R.drawable.ic_launcher);//设置图标
        builder.setItems(itemList,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DialogActivity.this,"我点击了"+itemList[which],Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog=builder.create();//获取dialog
        dialog.show();//显示对话框
    }


    /*多选对话框*/
    private void showDialog3() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("爱好");//设置标题
        builder.setIcon(R.drawable.ic_launcher);//设置图标
/*参数同单选对话框一样，另外第二个参数默认不选中为null，而不是-1*/
        builder.setMultiChoiceItems(likeList,null,new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(DialogActivity.this,"我喜欢"+likeList[which],Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(DialogActivity.this,"我不喜欢"+likeList[which],Toast.LENGTH_SHORT).show();
                }
            }
        });
/*添加对话框中取消按钮点击事件*/
        builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭对话框
            }
        });
        AlertDialog dialog=builder.create();//获取dialog
        dialog.show();//显示对话框
    }


    private void showDialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("性别");//设置标题
        builder.setIcon(R.drawable.ic_launcher);//设置图标
        /*参数一位单选列表文字，参数二为默认第几个选中（-1默认不选中），参数三是创建监听器*/
        builder.setSingleChoiceItems(sexList, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String sex = sexList[which];
                Toast.makeText(DialogActivity.this, "这个人性别为" + sex, Toast.LENGTH_SHORT).show();
            }
        });
        /*添加对话框中取消按钮点击事件*/
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭对话框
            }
        });
        AlertDialog dialog = builder.create();//获取dialog
        dialog.show();//显示对话框
    }

    //创建并弹出一个对话框
    protected void showDialog1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("你确定要离开吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                UIUtils.showMsg("确定");
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                UIUtils.showMsg("取消");
            }
        });
        builder.create().show();
    }
}
