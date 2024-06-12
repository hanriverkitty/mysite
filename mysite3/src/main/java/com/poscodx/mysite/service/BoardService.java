package com.poscodx.mysite.service;

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
	
	public void addContents(BoardVo vo) {
		if(Integer.valueOf(vo.getgNo()) != null) {
			boardRepository.updateOrderNo(vo);
		}
		boardRepository.insert(vo);
	}
	
	public BoardVo getContents(int no) {
		BoardVo vo = boardRepository.findByNo(no);
		if(vo!=null) {
			boardRepository.updateHit(no);
		}
		return vo;
	}
	
//	public BoardVo getContents(int boardNo, int userNo) {
//		return boardRepository.findByNo(boardNo);
//	}
	
	public void updateContents(BoardVo vo) {
		boardRepository.update(vo);
	}
	
	public void deleteContents(int boardNo, int userNo) {
		boardRepository.delete(boardNo);
	}
	
	public Map<String,Object> getContentsList(String keyword, int p) {
		System.out.println("getContentsList");
		List<BoardVo> list;
		int total;
		int limitIndex=(p-1)*5;
		System.out.println(keyword+":::::");
		if(!"".equals(keyword)) {
			list = boardRepository.findAll("%"+keyword+"%", limitIndex);
			total = boardRepository.searchCount("%"+keyword+"%");
			System.out.println("getContentsList null");
		} else {
			list = boardRepository.findAll(limitIndex);
			total = boardRepository.countTotal();
			if(p>total) p=1;
			System.out.println("getContentsList else");
		}
		int block = (int)Math.ceil((double)total/5);
		int currentBlock = (int)Math.ceil((double)p/5);
		int startNo = (currentBlock-1)*5+1;
		int endNo = currentBlock*5;

		Map<String, Object> map = new HashMap<String,Object>();
		map.put("list", list);
		map.put("currentBlock", currentBlock);
		map.put("startNo", startNo);
		map.put("block", block);
		map.put("endNo", endNo);
		map.put("total", total);
		return map;
	}
}
