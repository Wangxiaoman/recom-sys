package com.wxm.tools;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

/**
 * all log should be write as bean
 * @author zhaowei
 */
@Data
public class LogInfo {
    private String businessToken;
    private String businessId;
    private String sceneName;
    private String serviceName;
    private String logType;
    private long logWriteTime;
    private String logWriteTimeHuman;//without collect, just for grep
    private JSONObject content;

    public LogInfo(String businessToken,String businessId, String sceneName, String serviceName, String logType,
            long logWriteTime, JSONObject content){
        this.businessToken = businessToken;
        this.businessId = businessId;
        this.sceneName = sceneName;
        this.serviceName = serviceName;
        this.logType = logType;
        this.logWriteTime = logWriteTime;
        this.logWriteTimeHuman = DateTools.formatDate(logWriteTime);
        this.content = content;
    }

    @Override
    public String toString() {
        content.put("businessToken", businessToken);
        content.put("businessId", businessId);
        content.put("sceneName", sceneName);
        content.put("serviceName", serviceName);
        content.put("logType", logType);
        content.put("logWriteTime", logWriteTime);
        content.put("logWriteTimeHuman", logWriteTimeHuman);
        return content.toJSONString();
    }
}