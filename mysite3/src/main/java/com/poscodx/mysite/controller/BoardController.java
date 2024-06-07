package com.poscodx.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.poscodx.mysite.service.BoardService;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String index(Model model) {
		Map map = boardService.getContentsList(1);
		model.addAllAttributes(map);
		model.addAttribute("p", 1);
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
