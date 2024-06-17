package com.poscodx.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;

import com.poscodx.mysite.service.SiteService;

public class SiteInterceptor implements HandlerInterceptor {
	
	@Autowired
	private LocaleResolver localeResolver;
	
	@Autowired
	private SiteService siteService;
	
	public SiteInterceptor(SiteService siteService) {
		this.siteService = siteService;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// Locale
		System.out.println("resilver-locale: "+localeResolver.resolveLocale(request).getLanguage());
		request.setAttribute("language", localeResolver.resolveLocale(request).getLanguage());
		return true;
	}

}
