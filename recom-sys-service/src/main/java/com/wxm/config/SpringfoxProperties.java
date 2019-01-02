package com.wxm.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("4paradigm.recom.springfox.swagger")
@Data
@Component
public class SpringfoxProperties {

    private String title = "推荐系统免费版后端系统API文档";

    private String desciption = "本文档的内容由后端开发提供，主要面向前端开发, 系统交互开发和QA测试人员,作为各开发团队互相沟通的参考。";

    private String version ="v1.0.0";


    private String contact = "推荐系统后端";







}
