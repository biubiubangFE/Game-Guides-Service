package com.mhdss.ggs.Spider;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.mhdss.ggs.constant.NewsShowType;
import com.mhdss.ggs.constant.ParseStatus;
import com.mhdss.ggs.dataobject.NewsDO;
import com.mhdss.ggs.dataobject.ResourceDO;
import com.mhdss.ggs.service.NewsService;
import com.mhdss.ggs.service.ResourceService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XiaoHeiHeAnalysis {

    private static final Logger logger = LoggerFactory.getLogger(XiaoHeiHeAnalysis.class);


    final WebClient webClient = new WebClient(BrowserVersion.CHROME);//新建一个模拟谷歌Chrome浏览器的浏览器客户端对象

    public String startAnalysis(ResourceDO resourceDO) {


        if (null == resourceDO || StringUtils.isBlank(resourceDO.getShareUrl())) {
            return null;
        }

        // 修改执行状态


        String shareUrl = resourceDO.getShareUrl();
        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(true);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX

        HtmlPage page = null;
        try {
            page = webClient.getPage(shareUrl);//尝试加载上面图片例子给出的网页
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("xiao hei he 解析失败 resource id = {}", resourceDO.getId());
            return null;
        } finally {
            webClient.close();
        }

        webClient.waitForBackgroundJavaScript(30000);//异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束

        String pageXml = page.asXml();//直接将加载完成的页面转换成xml格式的字符串

        Document document = Jsoup.parse(pageXml);//获取html文档


        StringBuffer head = new StringBuffer();
        Elements articleContent = document.getElementsByClass("article-content");


        StringBuffer content = new StringBuffer();
        for (Element article : articleContent) {
            Elements allElements = article.getAllElements();

            for (Element element : allElements) {
                String className = element.className();
                String elementContent = element.toString();

                if (StringUtils.isNoneBlank(className) && ("news-source".equals(className) || "article-content".equals(className))) {
                    continue;
                }
                content.append(elementContent);
            }
        }

        String article = content.toString().replace("data-original", "src").replaceAll("/onclick=\\\".*?\\\"/gi","");

        //1.更新状态

        if (StringUtils.isBlank(article)) {
            logger.error("xiao hei he 解析内容为null", resourceDO.getId());
            return null;
        }

        return article;
    }

}
