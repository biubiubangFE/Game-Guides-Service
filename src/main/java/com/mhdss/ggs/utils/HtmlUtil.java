package com.mhdss.ggs.utils;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.BrowserVersion;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HtmlUtil {

    public static void main(String args[]) {
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);//新建一个模拟谷歌Chrome浏览器的浏览器客户端对象

        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(true);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX

        HtmlPage page = null;
        try {
            page = webClient.getPage("https://api.xiaoheihe.cn//maxnews//app//share//detail//1083866");//尝试加载上面图片例子给出的网页
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            webClient.close();
        }

        webClient.waitForBackgroundJavaScript(30000);//异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束

        String pageXml = page.asXml();//直接将加载完成的页面转换成xml格式的字符串

        Document document = Jsoup.parse(pageXml);//获取html文档


        StringBuffer head = new StringBuffer();
        Elements heads = document.getElementsByTag("head");

        for (Element element : heads) {
            head.append(element);
        }

        StringBuffer body = new StringBuffer();

        Elements bodys = document.getElementsByTag("body");

        body.append("<body style>");

        for (Element element : bodys) {

            Elements styles = element.getElementsByTag("link");

            for (Element style : styles) {
                body.append(style);
            }

            Elements containers = element.getElementsByClass("news-container");

            for (Element container : containers) {

                Elements openApp = container.getElementsByClass("btn-open-app primary-grd");

                Elements readAll = container.getElementsByClass("read-all");
                String openAppBtn = openApp.toString();
                body.append(container.toString().replace(openAppBtn, "").replace(readAll.toString(), ""));
            }


        }
        body.append("</body>");

        String htmlBody = body.toString().replace("class=\"lazy\"", "").replace("data-original", "src").replace("href=\"/static", "href=\"https://api.xiaoheihe.cn/static");

        saveArticle(head.toString().replace("href=\"/static", "href=\"https://api.xiaoheihe.cn/static"), htmlBody, "bb.html");

    }

    public static void saveArticle(String header, String content, String blogName) {

        String lujing = "E:\\test\\" + blogName;//保存到本地的路径和文件名

        File file = new File(lujing);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            StringBuffer stringBuffer = new StringBuffer();

            stringBuffer.append(header);
            stringBuffer.append(content);

            bw.write(stringBuffer.toString());

            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
