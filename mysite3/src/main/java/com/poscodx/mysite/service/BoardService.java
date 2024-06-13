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
			boardRepository.reply(vo);
			return;
		}
		boardRepository.insert(vo);
	}
	
	public BoardVo getContents(int no) {
		BoardVo vo = boardRepository.findByNo(no);
		System.out.println(vo);
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
	
	public BoardVo findByNo(int no) {
		return boardRepository.findByNo(no);
	}
	
	public void deleteContents(int boardNo) {
		boardRepository.delete(boardNo);
	}
	
	public Map<String,Object> getContentsList(String keyword, int p) {

		List<BoardVo> list;
		int total;
		int limitIndex=(p-1)*5;

		if(!"".equals(keyword)) {
			list = boardRepository.findAll("%"+keyword+"%", limitIndex);
			total = boardRepository.searchCount("%"+keyword+"%");
		} else {
			list = boardRepository.findAll(limitIndex);
			total = (int)boardRepository.countTotal();
			if(p>total) p=1;
		}
		
		// 페이지 계산
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
