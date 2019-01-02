package com.wxm.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Item {
    private int id;
    private String source;
    private String itemId;
    private String url;
    private String title;
    private String auther;
    private String coverUrl;
    private String tags;
    private String description;// 描述
    private Date publishTime;
    private Date ctime;
    private int type = 1;// 默认文章
    
}
