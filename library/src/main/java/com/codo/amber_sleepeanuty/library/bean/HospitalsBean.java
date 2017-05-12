package com.codo.amber_sleepeanuty.library.bean;

import java.util.List;

/**
 * Created by amber_sleepeanuty on 2017/5/3.
 */

public class HospitalsBean {

    List<SubjectsBean> subjects;
    PageBean page;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<SubjectsBean> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectsBean> subjects) {
        this.subjects = subjects;
    }

    public class SubjectsBean{
        String name;
        String addr;
        String rank;
        String summary;
        ImgBean img;
        List<String> departments;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String lv) {
            this.rank = lv;
        }

        public ImgBean getImgBean() {
            return img;
        }

        public void setImgBean(ImgBean imgBean) {
            this.img = imgBean;
        }

        public List<String> getDepartments() {
            return departments;
        }

        public void setDepartments(List<String> departments) {
            this.departments = departments;
        }

        public ImgBean getImg() {
            return img;
        }

        public void setImg(ImgBean img) {
            this.img = img;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }
    }

    public class ImgBean{
        String s;
        String m;
        String l;

        public String getSmall() {
            return s;
        }

        public void setSmall(String small) {
            this.s = small;
        }

        public String getMiddle() {
            return m;
        }

        public void setMiddle(String middle) {
            this.m = middle;
        }

        public String getLarge() {
            return l;
        }

        public void setLarge(String large) {
            this.l = large;
        }
    }

    public class PageBean{
        int count;
        int total;
        int start;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public boolean hasNextPage(int total,int start,int count){
            return total-start>count?true:false;
        }

        public int lastRemainCount(int total,int start){
                return total-start;
        }
    }
}
