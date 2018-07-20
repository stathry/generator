/**
 * Copyright 2016-2100 free Co., Ltd.
 */
package org.stathry.generator.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 获取spring容器中的bean
 * @author stathry
 *
 * 2016年8月18日
 */
public class ApplicationContextUtils implements ApplicationContextAware {
	
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationContextUtils.context = applicationContext;
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return context.getBean(clazz);
	}
	
	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

	public static <T> T getBean(String beanName, Class<T> clazz) {
		return (T)context.getBean(beanName);
	}
	
	public static ApplicationContext getContext() {
		return context;
	}

}
