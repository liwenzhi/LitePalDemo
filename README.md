#Android强大的数据库开源框架LitePal

LitePal使用起来非常方便，完全不用数据库语句就可以很方便实现数据库的增删查改功能。操作面向的是对象，只需要对这个对象进行操作就可以了。

LitePal的GitHub网址：https://github.com/LitePalFramework/LitePal

#使用LitePal的基本步骤：

##（一）导入jar包，或依赖库

官网都有，jar包也有提供下载

```
dependencies {
//AndroidStudio依赖
compile 'org.litepal.android:core:1.5.1'

}
```

##(二)在asstes中，新建litepal.xml文件

框架用来创建数据库用，我们不用操心底层是怎么实现的

```
<?xml version="1.0" encoding="utf-8"?>

<litepal>

    <dbname value="database1" ></dbname>//这里是数据库名称

    <version value="1" ></version>

    <list>
        <!-- 所有原型：包名+类名，类继承DataSupport，这时不用声明表名和列名，
        它会帮你创建，生成的表名是类名的小写，列名是各属性的值，但是要注意这里不能搞内部类，否则它不能识别！ -->

        <mapping class="com.liwenzhi.sql.Classmatess"></mapping>//这里是一张表
		//可以建立多张表

    </list>

</litepal>

```

##(三)使用自定义Application在onCreate中初始化

```
LitePal.initialize(this);

```
记得在AndroidManifest中注册Application的名字

##(四)定义数据原型Bean，要继承DataSupper
```
package com.liwenzhi.sql;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * 定义数据库中的表和属性，要继承DataSupper.
 * 这时不用声明表名和列名，
 * 它会帮你创建，生成的表名是类名的小写，列名是各属性的值，
 * 但是要注意这里不能搞内部类，否则它不能识别！
 * 这里属性也可以是一个类对象，但是这个类对象也是必须要继承了DataSupport才行
 * <p/>
 * 这个类虽然框架要调调用，但是也是可以当普通的Bean类来使用的
 */
public class Classmatess extends DataSupport {
    @Column(ignore = true)//忽略 不存储在数据库
    private int age;
    @Column(unique = true, nullable = true, defaultValue = "unknown")//唯一，可空，并且，默认值是unkonwn
    private String name;
    private float height;
    @Column(defaultValue = "女")//默认值女
    private String sex;
    private boolean isTeacher = false;

   
	get、set、toString。。。

}


```

##（四）使用数据库时的操作方法，增删改查

###1.增加

```
   		Classmatess mates = new Classmatess();
        mates.setName("name");
        mates.setHeight(160.0f);
        mates.setSex("男");
        mates.setTeacher(true);
        mates.save();//执行

```
这个数据库框架，比较强大的就是可以插入类，这里不演示，可以查看GitHub中的介绍。



###2.删除
```
  DataSupport.deleteAll(Classmatess.class, "name like ?", name + "%");//根据姓名删除数据
	
```


###3.修改

```
  		Classmatess mates = new Classmatess();
        String name = "xx";       
        mates.setHeight(160.0f);
        mates.setSex("女");
        mates.setTeacher(true);
        int i = mates.updateAll("name = ?", name);
		 if (i < 1) {
            Toast.makeText(this, "没有修改数据", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "成功修改了 " + i + " 个数据", Toast.LENGTH_SHORT).show();
        }

```


###4.查询
####1）根据id查询
```
Classmatess song = DataSupport.find(Classmatess.class, id);
```
####2）查询所有
```
 List<Classmatess> listClassmatess = DataSupport.findAll(Classmatess.class);
```  
####3）根据条件查询
```
  List<Classmatess> listClassmatess = DataSupport.where("name like ?", name + "%").order("height").find(Classmatess.class);
```
整个过程感觉是没有用到什么数据库知识，就可以操作数据库了，里面还有数据库的其他操作，比如清除数据库的数据、删除数据库等等。


这是我的一个程序示例：
效果：
![1](http://i.imgur.com/YCHvZMg.gif)
代码：
主要看下Activity中的代码：
```
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


```
我的项目中有jar包和参考代码：


这个框架使用起来还是蛮简单的。
但是我觉得比较坑的一点，就是使用id来操作增删改查，会出现点问题。
这个id应该是保存数据时候的序列号，而不是position，
因为我试过一直删id为1的对象，只能删一次，其他的都没成功！



#共勉：只有让自己更加强大，再次遇到难题时，才不会感到那么困难。
