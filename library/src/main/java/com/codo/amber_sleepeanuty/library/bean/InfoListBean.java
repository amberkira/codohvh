package com.codo.amber_sleepeanuty.library.bean;

import java.util.List;

/**
 * Created by amber_sleepeanuty on 2017/9/1.
 */

public class InfoListBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * title : 什么原因引起慢性肾脏疾病？
         * time : 2017-08-31
         * writer : 雪姨
         * imgUrl : http://192.168.19.146/hvh/bgimg/337.jpg
         * txtUrl : 6
         * avatar : null
         * description : null
         * category : 1
         * likes : 0
         */

        private String title;
        private String time;
        private String writer;
        private String imgUrl;
        private String txtUrl;
        private String avatar;
        private String description;
        private String category;
        private String likes;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getWriter() {
            return writer;
        }

        public void setWriter(String writer) {
            this.writer = writer;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getTxtUrl() {
            return txtUrl;
        }

        public void setTxtUrl(String txtUrl) {
            this.txtUrl = txtUrl;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getLikes() {
            return likes;
        }

        public void setLikes(String likes) {
            this.likes = likes;
        }
    }
}
