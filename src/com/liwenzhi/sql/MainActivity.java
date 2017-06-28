package com.liwenzhi.sql;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * 数据库框架LitePal的使用示例
 */
public class MainActivity extends Activity {

    TextView tv_show;
    EditText et_name;
    EditText et_height;
    EditText et_sex;
    EditText et_isTeacher;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv_show = (TextView) findViewById(R.id.tv_show);
        et_name = (EditText) findViewById(R.id.et_name);
        et_height = (EditText) findViewById(R.id.et_height);
        et_sex = (EditText) findViewById(R.id.et_sex);
        et_isTeacher = (EditText) findViewById(R.id.et_isTeacher);
    }

    /**
     * 增加数据
     *
     * @param view
     */
    public void add(View view) {
        Classmatess mates = new Classmatess();
        mates.setName("" + et_name.getText().toString());

        try {  //防止输入不能转换成浮点数的字符串
            mates.setHeight(Float.parseFloat("" + et_height.getText().toString()));
        } catch (Exception e) {
            mates.setHeight(160.0f);
        }
        if (!TextUtils.isEmpty(et_sex.getText().toString())) {
            mates.setSex("" + et_sex.getText().toString());
        }

        mates.setTeacher(Boolean.parseBoolean(et_isTeacher.getText().toString()));
        mates.save();//执行
    }


    /**
     * 删除数据
     *
     * @param view
     */
    public void delete(View view) {
//        DataSupport.delete(Classmatess.class, 1); //只能删一个？第二次就无效了？
        String name = "" + et_name.getText().toString();
        DataSupport.deleteAll(Classmatess.class, "name like ?", name + "%");//根据姓名删除数据
    }

    /**
     * 根据姓名修改数据
     *
     * @param view
     */
    public void update(View view) {
        Classmatess mates = new Classmatess();
        String name = "" + et_name.getText().toString();
        mates.setName("" + et_name.getText().toString());
        try {  //防止输入不能转换成浮点数的字符串
            mates.setHeight(Float.parseFloat("" + et_height.getText().toString()));
        } catch (Exception e) {
            mates.setHeight(160.0f);
        }
        if (!TextUtils.isEmpty(et_sex.getText().toString())) {
            mates.setSex("" + et_sex.getText().toString());
        }
        mates.setTeacher(Boolean.parseBoolean(et_isTeacher.getText().toString()));
        int i = mates.updateAll("name = ?", name);
        if (i < 1) {
            Toast.makeText(this, "没有修改数据", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "成功修改了 " + i + " 个数据", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 查询数据
     *
     * @param view
     */
    public void select(View view) {
        String name = "" + et_name.getText().toString();
        List<Classmatess> listClassmatess = DataSupport.where("name like ?", name + "%").order("height").find(Classmatess.class);
        tv_show.setText("\n查询到的数据：");
        for (int i = 0; i < listClassmatess.size(); i++) {
            tv_show.append("\n" + listClassmatess.get(i).toString());
        }
    }

    /**
     * 查询所有的数据
     *
     * @param view
     */
    public void selectAll(View view) {
        List<Classmatess> listClassmatess = DataSupport.findAll(Classmatess.class);
        tv_show.setText("\n查询到的数据：");
        for (int i = 0; i < listClassmatess.size(); i++) {
            tv_show.append("\n" + listClassmatess.get(i).toString());
        }
    }
}
