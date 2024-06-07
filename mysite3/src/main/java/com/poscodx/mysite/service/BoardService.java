package com.poscodx.mysite.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.BoardRepository;
import com.poscodx.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
//	public void addContents(BoardVo vo) {
//		if(Integer.valueOf(vo.getgNo()) != null) {
//			boardRepository.updateOrderNo(...);
//		}
//		boardRepository.insert(vo);
//	}
//	
//	public BoardVo getContents(Long no) {
//		BoardVo vo = boardRepository.findByNo(no);
//		if(vo!=null) {
//			boardRepository.updateHit();
//		}
//		return null;
//	}
//	
//	public BoardVo getContents(Long boardNo, Long userNo) {
//		
//	}
//	
//	public void updateContents(BoardVo vo) {
//		
//	}
//	
//	public void deleteContents(Long boardNo, Long userNo) {
//		
//	}
	
	public Map<String,Object> getContentsList(int p) {
		
		List<BoardVo> list = boardRepository.findAll(Long.valueOf(p));
		Map<String,Object> map = new HashMap<>();
		map.put("list", list);
		return map;
	}
}
