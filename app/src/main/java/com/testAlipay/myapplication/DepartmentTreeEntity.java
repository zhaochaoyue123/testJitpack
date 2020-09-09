package com.testAlipay.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class DepartmentTreeEntity implements Serializable {

    private String id;

    private String title;

    private String parentId;

    private boolean deptFlag;

    private String deptName;

    private String deptId;

    private int listLevel;

    private boolean isLeaf;

    private ArrayList<DepartmentTreeEntity> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isDeptFlag() {
        return deptFlag;
    }

    public void setDeptFlag(boolean deptFlag) {
        this.deptFlag = deptFlag;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public ArrayList<DepartmentTreeEntity> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<DepartmentTreeEntity> children) {
        this.children = children;
    }

    public int getListLevel() {
        return listLevel;
    }

    public void setListLevel(int listLevel) {
        this.listLevel = listLevel;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }
}
