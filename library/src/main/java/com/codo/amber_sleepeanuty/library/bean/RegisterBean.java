package com.codo.amber_sleepeanuty.library.bean;

/**
 * Created by amber_sleepeanuty on 2017/4/24.
 */

public class RegisterBean {
    public ServerBean server;
    public DBBean db;

    public ServerBean getServer() {
        return server;
    }

    public void setServer(ServerBean server) {
        this.server = server;
    }

    public DBBean getDb() {
        return db;
    }

    public void setDb(DBBean db) {
        this.db = db;
    }

    private class ServerBean{
        int errno;
        DataBean data;
        String error;

        public int getErrno() {
            return errno;
        }

        public void setErrno(int errno) {
            this.errno = errno;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }

    private class DBBean{
        int errno;
        int sqlstate;
        String error;

        public int getErrno() {
            return errno;
        }

        public void setErrno(int errno) {
            this.errno = errno;
        }

        public int getSqlstate() {
            return sqlstate;
        }

        public void setSqlstate(int sqlstate) {
            this.sqlstate = sqlstate;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }

    private class DataBean{
        int data;

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }
    }
}
