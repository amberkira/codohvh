/**
 * Copyright 2017 bejson.com
 */
package com.codo.amber_sleepeanuty.library.bean;

/**
 * Auto-generated: 2017-06-28 16:42:41
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class SMSBean {

    private Sms sms;
    public void setSms(Sms sms) {
        this.sms = sms;
    }
    public Sms getSms() {
        return sms;
    }


    public class Mt {

        private String status;
        public void setStatus(String status) {
            this.status = status;
        }
        public String getStatus() {
            return status;
        }

    }

    public class Sms {

        private Mt mt;
        public void setMt(Mt mt) {
            this.mt = mt;
        }
        public Mt getMt() {
            return mt;
        }

    }

}