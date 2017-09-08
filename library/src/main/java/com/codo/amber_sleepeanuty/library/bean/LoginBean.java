/**
 * Copyright 2017 bejson.com
 */
package com.codo.amber_sleepeanuty.library.bean;

public class LoginBean {

    /**
     * server : {"errno":0,"info":{"sessionid":"3374900599bd4353253eeeb36d3042a2","userid":"c3b77748-80b7-ab29-6d71-c77e594b2e76","mobile":"13683514096","username":"4096-c3b77748","nickname":"天空","portrait":"http://192.168.19.146/images/user/d534f4744d155c6489bdbe7328108a87.jpg","lastlogin":1504678406,"lng":"1.2345","lat":"5.6789","devid":"pc","curtime":1504683905,"lastsignin":1504683202,"credits":0},"error":""}
     * db : {"errno":0,"sqlstate":"00000","error":""}
     */

    private Server server;
    private Db db;

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Db getDb() {
        return db;
    }

    public void setDb(Db db) {
        this.db = db;
    }

    public static class Server {
        /**
         * errno : 0
         * info : {"sessionid":"3374900599bd4353253eeeb36d3042a2","userid":"c3b77748-80b7-ab29-6d71-c77e594b2e76","mobile":"13683514096","username":"4096-c3b77748","nickname":"天空","portrait":"http://192.168.19.146/images/user/d534f4744d155c6489bdbe7328108a87.jpg","lastlogin":1504678406,"lng":"1.2345","lat":"5.6789","devid":"pc","curtime":1504683905,"lastsignin":1504683202,"credits":0}
         * error :
         */

        private int errno;
        private Info info;
        private String error;

        public int getErrno() {
            return errno;
        }

        public void setErrno(int errno) {
            this.errno = errno;
        }

        public Info getInfo() {
            return info;
        }

        public void setInfo(Info info) {
            this.info = info;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public static class Info {
            /**
             * sessionid : 3374900599bd4353253eeeb36d3042a2
             * userid : c3b77748-80b7-ab29-6d71-c77e594b2e76
             * mobile : 13683514096
             * username : 4096-c3b77748
             * nickname : 天空
             * portrait : http://192.168.19.146/images/user/d534f4744d155c6489bdbe7328108a87.jpg
             * lastlogin : 1504678406
             * lng : 1.2345
             * lat : 5.6789
             * devid : pc
             * curtime : 1504683905
             * lastsignin : 1504683202
             * credits : 0
             */

            private String sessionid;
            private String userid;
            private String mobile;
            private String username;
            private String nickname;
            private String portrait;
            private Long lastlogin;
            private String lng;
            private String lat;
            private String devid;
            private Long curtime;
            private Long lastsignin;
            private int credits;

            public String getSessionid() {
                return sessionid;
            }

            public void setSessionid(String sessionid) {
                this.sessionid = sessionid;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getPortrait() {
                return portrait;
            }

            public void setPortrait(String portrait) {
                this.portrait = portrait;
            }

            public Long getLastlogin() {
                return lastlogin;
            }

            public void setLastlogin(Long lastlogin) {
                this.lastlogin = lastlogin;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getDevid() {
                return devid;
            }

            public void setDevid(String devid) {
                this.devid = devid;
            }

            public Long getCurtime() {
                return curtime;
            }

            public void setCurtime(Long curtime) {
                this.curtime = curtime;
            }

            public Long getLastsignin() {
                return lastsignin;
            }

            public void setLastsignin(Long lastsignin) {
                this.lastsignin = lastsignin;
            }

            public int getCredits() {
                return credits;
            }

            public void setCredits(int credits) {
                this.credits = credits;
            }
        }
    }

    public static class Db {
        /**
         * errno : 0
         * sqlstate : 00000
         * error :
         */

        private int errno;
        private String sqlstate;
        private String error;

        public int getErrno() {
            return errno;
        }

        public void setErrno(int errno) {
            this.errno = errno;
        }

        public String getSqlstate() {
            return sqlstate;
        }

        public void setSqlstate(String sqlstate) {
            this.sqlstate = sqlstate;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}