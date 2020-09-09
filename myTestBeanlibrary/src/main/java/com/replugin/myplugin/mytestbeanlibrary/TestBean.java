package com.replugin.myplugin.mytestbeanlibrary;

import com.replugin.myplugin.mytestgithublibrary.AbcdBean;

public class TestBean {

    private AbcdBean bean;

    public AbcdBean getBean() {
        bean = new AbcdBean();
        bean.setAaa("1234");
        bean.setBbb("5678");
        return bean;
    }
}
