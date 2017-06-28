/**
 * Copyright 2017 bejson.com
 */
package com.codo.amber_sleepeanuty.library.bean;

/**
 * Auto-generated: 2017-06-27 16:48:51
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class LoginBean {

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



    public class Info {

        private String sessionid;
        private String userid;
        private String username;
        private String nickname;
        private String portrait;
        public void setSessionid(String sessionid) {
            this.sessionid = sessionid;
        }
        public String getSessionid() {
            return sessionid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
        public String getUserid() {
            return userid;
        }

        public void setUsername(String username) {
            this.username = username;
        }
        public String getUsername() {
            return username;
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


    public class Server {

        private int errno;
        private Info info;
        private String error;
        public void setErrno(int errno) {
            this.errno = errno;
        }
        public int getErrno() {
            return errno;
        }

        public void setInfo(Info info) {
            this.info = info;
        }
        public Info getInfo() {
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


