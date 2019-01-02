package com.wxm.aop;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wxm.constants.CommonStatus;
import com.wxm.constants.ResultJson;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		LoginRequired loginRequired = priorityTokenValid(handler);
//		BusinessService businessService = SpringContextHolder.getBean(BusinessService.class);
		if (loginRequired != null) {
			String token = request.getParameter("token");
			if(StringUtils.isBlank(token)){
				token = request.getHeader("token");
			}
			if(StringUtils.isBlank(token)){
				token = getCookies(request).get("token");
			}
			if (StringUtils.isBlank(token)) {
				flush(CommonStatus.TOCKEN_NOT_CORRECT, response);
				return false;
			}
		}
//			Business business = null;
//			try {
//				business = businessService.queryByAToken(token);
//			} catch (Exception ex) {
//				CommonLogger.error("the business query error,ex:", ex);
//			}
//			if (business == null) {
//				flush(CommonStatus.TOCKEN_NOT_CORRECT, response);
//				return false;
//			}

//			long second = DateTools.getTimesSecond(business.getTokenTime(), new Date());
//			// 如果是测试账号，那么特殊处理
//            if (!Objects.equal(business.getId(), businessService.getDemoAccountId())) {
//    			if (second > Constants.TOKEN_EXPIRE_SECONDS) {
//    				businessService.reset(business.getId());
//    				flush(CommonStatus.TOCKEN_EXPIRE, response);
//    				return false;
//    			} else {
//    				businessService.updateTokenTime(business.getId());
//    			}
//			}
//			request.setAttribute("business", business);
//		}
		return true;
	}

	private Map<String, String> getCookies(HttpServletRequest request) {
		Map<String, String> result = new HashMap<>();
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie c : cookies) {
				result.put(c.getName(), c.getValue());
			}
		}
		return result;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	};

	private LoginRequired priorityTokenValid(Object handler) {
		LoginRequired loginRequired = null;
		if (null != handler && handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			LoginRequired methodAnnotion = method.getMethod().getAnnotation(LoginRequired.class);
			LoginRequired clazzAnnotion = method.getMethod().getDeclaringClass().getAnnotation(LoginRequired.class);
			if (null != methodAnnotion) {
				loginRequired = methodAnnotion;
			} else if (null != clazzAnnotion) {
				loginRequired = clazzAnnotion;
			}
		}
		return loginRequired;
	}

	private boolean flush(CommonStatus commonStatus, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(new ResultJson(commonStatus).toJson().toString());
		printWriter.flush();
		return false;
	}
}
