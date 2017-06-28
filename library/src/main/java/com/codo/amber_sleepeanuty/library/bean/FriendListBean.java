/**
 * Copyright 2017 bejson.com
 */
package com.codo.amber_sleepeanuty.library.bean;

import java.util.List;

/**
 * Auto-generated: 2017-06-28 14:15:14
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class FriendListBean {

    private Server server;
    private Db db;
    public void setServer(Server server) {
        this.server = server;
    }
    public Server getServer() {
        return server;
    }

    public void setDb(Db db) {
        this.db = db;
    }
    public Db getDb() {
        return db;
    }


    public class Infolist {

        private String userid;
        private String mobile;
        private String name;
        private String nickname;
        private String portrait;

        public void setUserid(String userid) {
            this.userid = userid;
        }
        public String getUserid() {
            return userid;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
        public String getMobile() {
            return mobile;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
        public String getNickname() {
            return nickname;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }
        public String getPortrait() {
            return portrait;
        }

    }

    public class Info {

        private String initial;
        private List<Infolist> infolist;
        public void setInitial(String initial) {
            this.initial = initial;
        }
        public String getInitial() {
            return initial;
        }

        public void setInfolist(List<Infolist> infolist) {
            this.infolist = infolist;
        }
        public List<Infolist> getInfolist() {
            return infolist;
        }
    }


    public class Server {

        private int errno;
        private List<Info> info;
        private String error;
        public void setErrno(int errno) {
            this.errno = errno;
        }
        public int getErrno() {
            return errno;
        }

        public void setInfo(List<Info> info) {
            this.info = info;
        }
        public List<Info> getInfo() {
            return info;
        }

        public void setError(String error) {
            this.error = error;
        }
        public String getError() {
            return error;
        }
    }

}