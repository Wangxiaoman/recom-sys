package com.wxm.tools;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextHolder.applicationContext = applicationContext;
	}

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		if (applicationContext.containsBean(name)) {
			return (T) applicationContext.getBean(name);
		}
		return null;
	}

	/**
     * 根据bean的class来查找对象
     * @param c
     * @return
     */
    public static <T> T getBean(Class<T> c){
        return applicationContext.getBean(c);
    }
    
	/**
	 * 根据bean的class来查找所有的对象(包括子类)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String,Object> getBeansOfType(Class clazz) {
		checkApplicationContext();
		return  applicationContext.getBeansOfType(clazz);
	}

	private static void checkApplicationContext() {
		if (applicationContext == null){
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextUtil");
		}
	}
}
