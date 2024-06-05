package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.GuestbookRepository;
import com.poscodx.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	
	@Autowired
	private GuestbookRepository guestbookRepository;
	
	public List<GuestbookVo> getContentsList(){
		return guestbookRepository.findAll();
	}
	public int deleteContents(Long no, String password) {
		return guestbookRepository.deleteByNo(no, password);
	}
	public void addContents(GuestbookVo vo) {
		System.out.println(vo);
		guestbookRepository.insert(vo);
		System.out.println(vo);
	}
}
