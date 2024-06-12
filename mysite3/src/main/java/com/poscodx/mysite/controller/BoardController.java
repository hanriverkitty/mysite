package com.poscodx.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String index(
			@RequestParam(value="p", required=true, defaultValue="1" ) int p,
			@RequestParam(value="keyword",required=true,defaultValue="") String keyword,
			Model model) {
		System.out.println("keyword:"+keyword);
		Map<String,Object> map = boardService.getContentsList(keyword,p);
		model.addAttribute(map);
		model.addAttribute("keyword", keyword);
		model.addAttribute("p",p);
		return  "board/list";
	}
	
//	@RequestMapping("/view/{no}")
//	public String view(@PathVariable("no") int no) {
//		
//	}
//	
//	@RequestMapping("/delete/{no}")
//	public String view(HttpSession session, @PathVariable("no") int no) {
//		// access control
//				UserVo authUser = (UserVo) session.getAttribute("authUser");
//				if(authUser==null) {
//					return "redirect:/";
//				}
//				///////////////////////////
//	}
}
