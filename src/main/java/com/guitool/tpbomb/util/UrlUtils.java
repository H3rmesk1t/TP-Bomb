package com.guitool.tpbomb.util;

import com.github.kevinsawicki.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class UrlUtils {

    public static String doGet(String url) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        UserAgentUtils userAgent = new UserAgentUtils();
        httpGet.addHeader("User-Agent", userAgent.randomUserAgent());
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String response = EntityUtils.toString(httpResponse.getEntity());
                return response;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doGetSpecial(String url) {
        // 用于解决必须包含特殊字符进行请求的Payload
        try {
            HttpRequest httpRequest = HttpRequest.get(url).followRedirects(true);
            httpRequest.trustAllCerts();
            httpRequest.trustAllHosts();
            if (httpRequest.code() == 200) {
                return httpRequest.body();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doPost(String url, StringBuffer stringBuffer) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(300 * 1000).setConnectTimeout(300 * 1000).build();
        HttpPost httpPost = new HttpPost(url);
        UserAgentUtils userAgent = new UserAgentUtils();
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("User-Agent", userAgent.randomUserAgent());

        StringEntity stringEntity = new StringEntity(stringBuffer.toString(), "UTF-8");
        stringEntity.setContentType("application/x-www-form-urlencoded");
        httpPost.setEntity(stringEntity);
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String response = EntityUtils.toString(httpResponse.getEntity());
                return response;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doPostSpecial(String url, StringBuffer stringBuffer) {
        // 用于解决必须包含特殊字符进行请求的Payload
        try {
            HttpRequest httpRequest = HttpRequest.post(url).send(stringBuffer);
            httpRequest.trustAllCerts();
            httpRequest.trustAllHosts();
            if (httpRequest.code() == 200) {
                return httpRequest.body();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String urlStructureCheck(String url) {
        String pattern = "(ht|f)tp(s?)\\:\\/\\/[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*(:(0-9)*)*(\\/?)([a-zA-Z0-9\\-\\.\\?\\,\\'\\/\\\\\\+&amp;%\\$#_]*)?";
        if (url.matches(pattern)) {
            if (url.charAt(url.length() - 1) != '/' && !url.endsWith("php")) {
                url += "/";
            }
            return url;
        }
        return null;
    }

    public static String urlRequestCheck(String url) {
        String requestUrl = urlStructureCheck(url);
        if (requestUrl != null) {
            if (doGet(url) != null) {
                return requestUrl;
            }
        }
        return null;
    }

}