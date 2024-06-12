package com.poscodx.mysite3_kickscar.service;


import org.springframework.stereotype.Service;

import com.poscodx.mysite3_kickscar.repository.SiteRepository;
import com.poscodx.mysite3_kickscar.vo.SiteVo;

@Service
public class SiteService {
	private SiteRepository siteRepository;
	
	public SiteService(SiteRepository siteRepository) {
		this.siteRepository = siteRepository;
	}
	
	public SiteVo getSite() {
		return siteRepository.find();
	}
	
	public void updateSite(SiteVo vo) {
		siteRepository.update(vo);
	}
}
