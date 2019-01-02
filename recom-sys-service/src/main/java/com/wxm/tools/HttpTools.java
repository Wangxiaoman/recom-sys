package com.wxm.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import com.wxm.constants.Constants;
import com.wxm.log.CommonLogger;


public class HttpTools {
	private static IOReactorConfig ioReactorConfig;
	private static CloseableHttpAsyncClient httpAsyncClient = null;
	static {
		try {
		    CommonLogger.info("http async client init ~");
			ioReactorConfig = IOReactorConfig.custom().setSoKeepAlive(true)
					.setIoThreadCount(Runtime.getRuntime().availableProcessors()).setSelectInterval(100)
					.setConnectTimeout(200).setSoTimeout(200).build();
			ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
			PoolingNHttpClientConnectionManager cm = new PoolingNHttpClientConnectionManager(ioReactor);
			cm.setDefaultMaxPerRoute(60);

			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200).setConnectionRequestTimeout(200)
					.setSocketTimeout(500).build();

			httpAsyncClient = HttpAsyncClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig)
					.build();
			httpAsyncClient.start();
			CommonLogger.info("http async client end ~");
		} catch (Exception ex) {
			CommonLogger.error("http async client start error:", ex);
		}
	}

	public static CloseableHttpAsyncClient getHttpSyncClient() {
		return httpAsyncClient;
	}
	
    public static String get(String url, int timeoutMs) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(timeoutMs)
                .setConnectTimeout(timeoutMs)
                .setSocketTimeout(timeoutMs)
                .build();
        get.setConfig(requestConfig);
        get.setHeader("Content-type", "application/json;charset=utf-8");
        HttpResponse response = client.execute(get);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    public static HttpResponse postForResponse(String url, String jsonContent, int timeoutMs) throws Exception {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(timeoutMs)
                    .setConnectTimeout(timeoutMs)
                    .setSocketTimeout(timeoutMs)
                    .build();
            post.setConfig(requestConfig);
            post.setHeader("Content-type", "application/json;charset=utf-8");
            StringEntity se = new StringEntity(jsonContent, ContentType.APPLICATION_JSON);
            post.setEntity(se);
            HttpResponse response = client.execute(post);
            return response;
        } catch (Exception e) {
        	CommonLogger.error("postForResponse error, url:"+url+", content:"+jsonContent, e);
        }
        return null;
    }
    
    public static String post(String url, String jsonContent, int timeoutMs) throws Exception {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(timeoutMs)
                    .setConnectTimeout(timeoutMs)
                    .setSocketTimeout(timeoutMs)
                    .build();
            post.setConfig(requestConfig);
            post.setHeader("Content-type", "application/json;charset=utf-8");
            StringEntity se = new StringEntity(jsonContent, ContentType.APPLICATION_JSON);
            post.setEntity(se);
            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (Exception e) {
        	CommonLogger.error("post error, url:"+url, e);
        }
        return null;
    }
    
    public static String postPreview(String url, String jsonContent,String innerToken, int timeoutMs) throws Exception {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(timeoutMs)
                    .setConnectTimeout(timeoutMs)
                    .setSocketTimeout(timeoutMs)
                    .build();
            post.setConfig(requestConfig);
            post.addHeader("Content-type", "application/json;charset=utf-8");
            post.addHeader("X-INNER-TOKEN", innerToken);
            StringEntity se = new StringEntity(jsonContent, ContentType.APPLICATION_JSON);
            post.setEntity(se);
            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (Exception e) {
        	CommonLogger.error("post error, url:"+url, e);
        }
        return null;
    }

    public static String post(String url, JSONObject jsonContent, int timeoutMs) throws Exception {
        return post(url, jsonContent.toJSONString(), timeoutMs);
    }
    
    public static String parseCookieUserId(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        String cookieUserId = null;
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (Constants.LOG_NAME_USERID.equals(cookie.getName())) {
                    cookieUserId = cookie.getValue();
                    return cookieUserId;
                }
            }
        }
        return null;
    }
    
    public static String[] getRemoteHost(HttpServletRequest request) {
        String[] result = new String[2];
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            List<String> ips = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(ip); 
            if (!CollectionUtils.isEmpty(ips)) {
                result[0] = ips.get(0);
                result[1] = ips.get(ips.size()-1);
                return result;
            } 
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            result[0] = ip;
            result[1] = ip;
            return result;
        }
        ip = request.getRemoteAddr().trim();
        ip = ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
        ip = Objects.isNull(ip) ? StringUtils.EMPTY : ip;
        result[0] = ip;
        result[1] = ip;
        return result;
    }
}