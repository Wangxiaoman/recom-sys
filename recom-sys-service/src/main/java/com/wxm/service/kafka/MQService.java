package com.wxm.service.kafka;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wxm.log.CommonLogger;

@Service
public class MQService {

    private final static int FLUME_COUNT = 9;

    private static int getFlumeNumber(String userId) {
        if (StringUtils.isBlank(userId)) {
            return RandomUtils.nextInt(0, FLUME_COUNT);
        }

        int number = 0;
        for (int i = 0; i < userId.length(); i++) {
            number += userId.charAt(i);
        }

        return (number % FLUME_COUNT);
    }

    public String getPetalRelationTopic() {
        return "petal_relation";
    }

    private String preshowActionMsgTopic(String userId) {
        int userNumber = getFlumeNumber(userId);
        return userNumber + "_action";
    }

    private String itemTopic() {
        return "item_save_message";
    }

    private String getRemoveItemTopic() {
        return "item_remove_message";
    }

    @Autowired
    private KafkaTemplate<String, String> template;

    @Async
    public void sendActionPreshow(String sceneLog, String userId) {
        template.send(preshowActionMsgTopic(userId), sceneLog);
    }

    @Async
    public void sendPetalRelation(String petalLog) {
        template.send(getPetalRelationTopic(), petalLog);
    }

    @Async
    public void sendItem(String itemId, String url, int type) {
        JSONObject jo = new JSONObject();
        jo.put("itemId", itemId);
        jo.put("url", url);
        jo.put("itemSetId", 1);
        jo.put("type", type);
        CommonLogger.info("kafka item send, topic:" + itemTopic() + ",json:" + jo.toJSONString());
        template.send(itemTopic(), jo.toJSONString());
    }

    @Async
    public void sendRemoveItem(String itemId, String url, int itemSetId) {
        JSONObject jo = new JSONObject();
        jo.put("itemId", itemId);
        jo.put("url", url);
        jo.put("itemSetId", String.valueOf(itemSetId));
        CommonLogger.info("kafka remove item send, topic:" + getRemoveItemTopic() + ",json:"
                + jo.toJSONString());
        template.send(getRemoveItemTopic(), jo.toJSONString());
    }
}
