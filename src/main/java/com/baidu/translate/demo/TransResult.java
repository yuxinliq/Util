package com.baidu.translate.demo;

import java.util.List;

public class TransResult {
    String from;
    String to;
    List<Result> trans_result;

    @Override
    public String toString() {
        return trans_result.get(0) + "";
    }

    static class Result {
        String src;
        String dst;

        @Override
        public String toString() {
            return dst;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getDst() {
            return dst;
        }

        public void setDst(String dst) {
            this.dst = dst;
        }
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<Result> getTrans_result() {
        return trans_result;
    }

    public void setTrans_result(List<Result> trans_result) {
        this.trans_result = trans_result;
    }
}
