package com.poscodx.mysite3_kickscar.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.mysite3_kickscar.service.SiteService;
import com.poscodx.mysite3_kickscar.vo.SiteVo;

public class SiteInterceptor implements HandlerInterceptor {
	private SiteService  siteService;

	public SiteInterceptor(SiteService  siteService) {
		this.siteService = siteService;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		SiteVo siteVo = (SiteVo)request.getServletContext().getAttribute("sitevo");
		if(siteVo == null) {
			siteVo = siteService.getSite();
			request.getServletContext().setAttribute("sitevo", siteVo);
		}
		
		return true;
	}

}
