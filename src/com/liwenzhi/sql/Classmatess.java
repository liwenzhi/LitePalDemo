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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isTeacher() {
        return isTeacher;
    }

    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }

    @Override
    public String toString() {
        return
                " name=" + name +
                        ", height=" + height +
                        ", sex=" + sex +
                        ", isTeacher=" + isTeacher;
    }
}
