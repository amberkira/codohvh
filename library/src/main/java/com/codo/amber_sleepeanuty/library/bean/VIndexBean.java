package com.codo.amber_sleepeanuty.library.bean;

import java.util.List;

/**
 * Created by amber_sleepeanuty on 2017/8/28.
 */

public class VIndexBean {

    /**
     * version : 1.0
     * banner : {"url":["bannerUrl_1","bannerUrl_2"]}
     * service : {"txt":["ServiceText_1","ServiceText_2","ServiceText_3"],"url":["serviceUrl_1","serviceUrl_2","serviceUrl_3"],"imgUrl":["serviceImageUrl_1","serviceImageUrl_2","serviceImageUrl_3"]}
     * item : {"txt":["itemText_1","itemText_2","itemText_3"],"url":["itemUrl_1","itemUrl_2","itemUrl_3"],"imgUrl":["itemImageUrl_1","itemImageUrl_2","itemImageUrl_3"]}
     */

    private String version;
    private BannerBean banner;
    private ServiceBean service;
    private ItemBean item;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public BannerBean getBanner() {
        return banner;
    }

    public void setBanner(BannerBean banner) {
        this.banner = banner;
    }

    public ServiceBean getService() {
        return service;
    }

    public void setService(ServiceBean service) {
        this.service = service;
    }

    public ItemBean getItem() {
        return item;
    }

    public void setItem(ItemBean item) {
        this.item = item;
    }

    public static class BannerBean {
        private List<String> url;

        public List<String> getUrl() {
            return url;
        }

        public void setUrl(List<String> url) {
            this.url = url;
        }
    }

    public static class ServiceBean {
        private List<String> txt;
        private List<String> url;
        private List<String> imgUrl;

        public List<String> getTxt() {
            return txt;
        }

        public void setTxt(List<String> txt) {
            this.txt = txt;
        }

        public List<String> getUrl() {
            return url;
        }

        public void setUrl(List<String> url) {
            this.url = url;
        }

        public List<String> getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(List<String> imgUrl) {
            this.imgUrl = imgUrl;
        }
    }

    public static class ItemBean {
        private List<String> txt;
        private List<String> url;
        private List<String> imgUrl;

        public List<String> getTxt() {
            return txt;
        }

        public void setTxt(List<String> txt) {
            this.txt = txt;
        }

        public List<String> getUrl() {
            return url;
        }

        public void setUrl(List<String> url) {
            this.url = url;
        }

        public List<String> getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(List<String> imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
