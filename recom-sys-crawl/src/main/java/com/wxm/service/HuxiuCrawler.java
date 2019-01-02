package com.wxm.service;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wxm.domain.Item;
import com.wxm.tools.DateTools;

public class HuxiuCrawler {

    public static Item parseToItem(String url) throws Exception {
        Item Item = new Item();
        Item.setUrl(url);
        Item.setItemId(getItmeId(url));
        
        Document doc = Jsoup.connect(url).get();
        Element header = doc.select("head").first();
        String title = header.select("title").text();
        Item.setTitle(title);
        
        Elements metas = header.select("meta");
        for (int i = 0; i < metas.size(); i++) {
            Element meta = metas.get(i);
            if (meta.attr("name").equals("author")) {
                String auther = meta.attr("content");
                Item.setAuther(auther);
            }
            if (meta.attr("name").equals("keywords")) {
                String tags = meta.attr("content");
                Item.setTags(tags);
            }
            if (meta.attr("name").equals("description")) {
                String description = meta.attr("content");
                Item.setDescription(description);
            }
            if (meta.attr("property").equals("og:site_name")) {
                String source = meta.attr("content");
                Item.setSource(source);
            }
            if (meta.attr("property").equals("og:image")) {
                String coverUrl = meta.attr("content");
                Item.setCoverUrl(coverUrl);
            }
        }

        Elements elements = doc.getElementsByClass("Item-wrap");
        if (elements.size() > 0) {
            // publishTime
            Elements ps = elements.get(0).getElementsByClass("Item-time pull-left");
            String publishTime = ps.get(0).ownText();
            Item.setPublishTime(DateTools.getDate(publishTime, "yyyy-MM-dd HH:mm"));
        }

        return Item;
    }

    private static String getItmeId(String url) {
        return StringUtils.substringBeforeLast(StringUtils.substringAfterLast(url, "/"), ".html");
    }

    public static void main(String[] args) throws Exception {
        String url = "https://www.huxiu.com/Item/277457.html";
        System.out.println(parseToItem(url));
    }
}
