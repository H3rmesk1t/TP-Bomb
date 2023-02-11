package com.guitool.tpbomb.util;

public class ResultUtils {
    boolean result;
    String payload;
    String vulnerability;

    public boolean isResult() {
        return result;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getVulnerability() {
        return vulnerability;
    }

    public void setVulnerability(String vulnerability) {
        this.vulnerability = vulnerability;
    }

    public ResultUtils(boolean result, String payload, String vulnerability) {
        this.result = result;
        this.payload = payload;
        this.vulnerability = vulnerability;
    }
}
