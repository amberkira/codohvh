package com.codo.amber_sleepeanuty.library.bean;

/**
 * Created by amber_sleepeanuty on 2017/6/28.
 */
public class Db {

    private int errno;
    private String sqlstate;
    private String error;
    public void setErrno(int errno) {
        this.errno = errno;
    }
    public int getErrno() {
        return errno;
    }

    public void setSqlstate(String sqlstate) {
        this.sqlstate = sqlstate;
    }
    public String getSqlstate() {
        return sqlstate;
    }

    public void setError(String error) {
        this.error = error;
    }
    public String getError() {
        return error;
    }

}