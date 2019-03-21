package com.mhdss.ggs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.google.common.collect.Maps;
import com.google.common.io.ByteStreams;

public class HtmlUnitDemo {

    private WebClient webClient = null;

    private int timeout = 50000;

    public HtmlUnitDemo() {
        this(null);
    }

    /**
     * Get请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public byte[] sendGetRequest(String url) throws Exception {
        WebRequest webRequest = new WebRequest(new URL(url));
        webRequest.setHttpMethod(HttpMethod.GET);
        return sendRequest(webRequest);
    }

    /**
     * Post 请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public byte[] sendPostRequest(String url, Map<String, String> params) throws Exception {
        WebRequest webRequest = new WebRequest(new URL(url));
        webRequest.setHttpMethod(HttpMethod.POST);
        if (params != null && params.size() > 0) {
            for (Entry<String, String> param : params.entrySet()) {
                webRequest.getRequestParameters().add(new NameValuePair(param.getKey(), param.getValue()));
            }
        }
        return sendRequest(webRequest);
    }

    // 底层请求
    private byte[] sendRequest(WebRequest webRequest) throws Exception {
        byte[] responseContent = null;
        Page page = webClient.getPage(webRequest);

        WebResponse webResponse = page.getWebResponse();

        int status = webResponse.getStatusCode();

        System.out.println("Charset : " + webResponse.getContentCharset());

        System.out.println("ContentType : " + webResponse.getContentType());

        // 读取数据内容
        if (status == 200) {
            if (page.isHtmlPage()) {
                // 等待JS执行完成
                webClient.waitForBackgroundJavaScript(100000);
                responseContent = ((HtmlPage) page).asXml().getBytes();
            } else {
                InputStream bodyStream = webResponse.getContentAsStream();
                responseContent = ByteStreams.toByteArray(bodyStream);
                bodyStream.close();
            }
        }
        // 关闭响应流
        webResponse.cleanUp();

        return responseContent;
    }

    public HtmlUnitDemo(HttpProxy proxy) {
        webClient = new WebClient();
        configWebClient();
        // 设置代理
        if (proxy != null) {
            setProxy(proxy);
        }
    }

    private void configWebClient() {
        // 设置webClient的相关参数
        // 1 启动JS
        webClient.getOptions().setJavaScriptEnabled(true);
        // 2 禁用Css，可避免自动二次请求CSS进行渲染
        webClient.getOptions().setCssEnabled(false);
        // 3 启动客户端重定向
        webClient.getOptions().setRedirectEnabled(true);

        // 4 js运行错误时，是否抛出异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        // 5 设置超时
        webClient.getOptions().setTimeout(timeout);
    }

    private void setProxy(HttpProxy proxy) {
        ProxyConfig proxyConfig = webClient.getOptions().getProxyConfig();
        proxyConfig.setProxyHost(proxy.getHost());
        proxyConfig.setProxyPort(proxy.getPort());

        DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient
                .getCredentialsProvider();
        credentialsProvider.addCredentials(proxy.getUser(), proxy.getPassword());
    }

    @SuppressWarnings("unused")
    private Map<String, String> getResponseCookies() {
        Set<Cookie> cookies = webClient.getCookieManager().getCookies();
        Map<String, String> responseCookies = Maps.newHashMap();
        for (Cookie c : cookies) {
            responseCookies.put(c.getName(), c.getValue());
        }
        return responseCookies;
    }

    @SuppressWarnings("unused")
    private void setCookies(String domain, Map<String, String> cookies) {
        if (cookies != null && cookies.size() > 0) {
            webClient.getCookieManager().setCookiesEnabled(true);// enable
            // cookies
            for (Entry<String, String> c : cookies.entrySet()) {
                Cookie cookie = new Cookie(domain, c.getKey(), c.getValue());
                webClient.getCookieManager().addCookie(cookie);

                System.out.println("Set Cookies : " + c.getKey() + " | " + c.getValue());
            }
        }
    }

    /**
     * 清除所有cookie
     */
    public void clearCookies() {
        webClient.getCookieManager().clearCookies();
    }

    public void shutdown() throws IOException {
        webClient.close();
    }

    /**
     * 打开google 搜索百度
     *
     * @param
     * @throws Exception
     */
    public void demo() throws Exception{
        String url = "http://www.google.com.hk";

        final WebClient webClient = new WebClient();
        HtmlPage htmlPage = webClient.getPage(url);

        // HtmlUnit dom模型
        // 获取表单 ,获得form标签name属性=f
        HtmlForm form = htmlPage.getFormByName("f");
        // 获取输入框, 获取 input标签 ，name属性=q
        HtmlTextInput text = form.getInputByName("q");
        // 搜索百度
        text.setText("baidu");
        // 获取提交按钮
        HtmlSubmitInput button = form.getInputByName("btnG");
        // 提交表单
        HtmlPage listPage = button.click();

        System.out.println(listPage.asXml());
        webClient.close();
    }
    /**
     * 打开google 搜索百度
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String url = "http://dota2.uuu9.com/201901/586576.shtml";

        HtmlUnitDemo htmlUnit = new HtmlUnitDemo();
        byte[] getResponse = htmlUnit.sendGetRequest(url);
        System.out.println("Get Body : " + new String(getResponse, "utf-8"));
        byte[] postResponse = htmlUnit.sendPostRequest(url, null);
        System.out.println("Get Body : " + new String(postResponse, "utf-8"));

        htmlUnit.shutdown();
    }
}