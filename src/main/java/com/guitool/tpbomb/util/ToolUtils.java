package com.guitool.tpbomb.util;

import com.guitool.tpbomb.payload.*;

public class ToolUtils {

    public static PayloadTemplate getPayload(String vulnerability) {
        PayloadTemplate payloadTemplate = null;
        if (vulnerability.equals("ThinkPHP2.x RCE")) {
            payloadTemplate = new TP2Rce();
        } else if (vulnerability.equals("ThinkPHP3.2.x 日志泄露")) {
            payloadTemplate = new TP3LogLeak();
        } else if (vulnerability.equals("ThinkPHP3.2.x Debug未开启RCE")) {
            payloadTemplate = new TP3LogCloseDebugRce();
        } else if (vulnerability.equals("ThinkPHP3.2.x Debug开启RCE")) {
            payloadTemplate = new TP3LogOpenDebugRce();
        } else if (vulnerability.equals("ThinkPHP5 日志泄露")) {
            payloadTemplate = new TP5LogLeak();
        } else if (vulnerability.equals("ThinkPHP5 Debug无关RCE")) {
            payloadTemplate = new TP5WithoutDebugRce();
        } else if (vulnerability.equals("ThinkPHP5 Debug开启RCE")) {
            payloadTemplate = new TP5DebugRce();
        } else if (vulnerability.equals("ThinkPHP5 Captcha路由RCE")) {
            payloadTemplate = new TP5CaptchaRce();
        } else if (vulnerability.equals("ThinkPHP5 未开启强制路由RCE")) {
            payloadTemplate = new TP5WithoutForcedRoutingRce();
        } else if (vulnerability.equals("ThinkPHP 多语言RCE")) {
            payloadTemplate = new TPMultipleLanguageRce();
        } else if (vulnerability.equals("ThinkPHP5 数据库信息泄露")) {
            payloadTemplate = new TP5DatabaseLeak();
        } else if (vulnerability.equals("ThinkPHP6 日志泄露")) {
            payloadTemplate = new TP6LogLeak();
        }
        return payloadTemplate;
    }
}
