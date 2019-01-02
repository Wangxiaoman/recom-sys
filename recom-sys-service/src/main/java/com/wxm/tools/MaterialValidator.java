package com.wxm.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Objects;
import com.wxm.log.CommonLogger;

/**
 *
 * 验证物料字段是否正确：
 * 1. 必填字段是否有
 * 2. 必填、选填、自字义字段如果有的话，类型是否正确，类型只有long, double, string, timestamp4种
 * @author zhaowei
 * @date 2018/1/2
 */
public class MaterialValidator {
    private Map<String, String> requiredMap = new HashMap<>();
    private Map<String, String> couldBeEmpty = new HashMap<>();
    private static final ThreadLocal<SimpleDateFormat> SDF = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public MaterialValidator(JSONArray materialConstrain) {
        for (int i = 0 ; i < materialConstrain.size();i++) {
            try {
                String fieldName = materialConstrain.getJSONObject(i).getString("fieldName");
                String fieldType = materialConstrain.getJSONObject(i).getString("fieldType");
                String isRequired = materialConstrain.getJSONObject(i).getString("isRequired");
                if (Objects.equal(isRequired,"1")) {
                    //这个编码是从接入系统的js过来的，所以写成了硬编码
                    requiredMap.put(fieldName, fieldType);
                } else {
                    couldBeEmpty.put(fieldName, fieldType);
                }
            } catch (Exception e) {
            	CommonLogger.error("", e);
            }
        }
    }

    /**
     * @param item
     * @return
     */
    public JSONObject validate(JSONObject item) {
        JSONObject error = null;
        if (item == null) {
            if (error == null) {
                error = new JSONObject();
            }
            error.put("item", ":is empty");
            return error;
        }
        for (String requiredFieldName: requiredMap.keySet()) {
            if (!item.containsKey(requiredFieldName)) {
                if (error == null) {
                    error = new JSONObject();
                }
                error.put(requiredFieldName, "不存在必填字段");
            } else {
                Object value = item.get(requiredFieldName);
                String type = requiredMap.get(requiredFieldName);
                if (!validateType(type, value)) {
                    if (error == null) {
                        error = new JSONObject();
                    }
                    error.put(requiredFieldName, value + ":field type error");
                }
            }
        }

        for (String couldBeEmptyFieldName: couldBeEmpty.keySet()) {
            if (item.containsKey(couldBeEmptyFieldName)) {
                Object value = item.get(couldBeEmptyFieldName);
                String type = couldBeEmpty.get(couldBeEmptyFieldName);
                if (!validateType(type, value)) {
                    if (error == null) {
                        error = new JSONObject();
                    }
                    error.put(couldBeEmptyFieldName, value + ":type should be " + type);
                }
            }
        }
        return error;
    }

    private boolean validateType(String type, Object value) {
        try {
            if ("string".equals(type.toLowerCase())) {
                if (! (value instanceof String)) {
                    return false;
                }
            } else if ("timestamp".equals(type.toLowerCase())) {
                try {
                    SDF.get().parse(String.valueOf(value));
                } catch (ParseException e) {
                    return false;
                }
            } else if ("bigint".equals(type.toLowerCase())){
                try {
                    return value instanceof Integer || value instanceof Long;
                } catch (Exception e) {
                    return false;
                }
            } else {
                try {
                    return value instanceof Double || value instanceof Float || value instanceof Integer;
                } catch (Exception e) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
