package com.guitool.tpbomb.payload;

import com.guitool.tpbomb.util.ResultUtils;

public interface PayloadTemplate {
    ResultUtils checkVulnerability(String url);

    ResultUtils executeVulnerability(String url, String command);

    ResultUtils getBehinderShell(String url);

    ResultUtils getAntswordShell(String url);
}
