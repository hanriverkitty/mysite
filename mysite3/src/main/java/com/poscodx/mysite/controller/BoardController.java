package com.poscodx.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.service.BoardService;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping("")
	public String index(HttpSession session, @RequestParam(value = "p", required = true, defaultValue = "1") int p,
			@RequestParam(value = "keyword", required = true, defaultValue = "") String keyword, Model model) {
		System.out.println("keyword:" + keyword);
		Map<String, Object> map = boardService.getContentsList(keyword, p);
		model.addAttribute("map", map);
		model.addAttribute("keyword", keyword);
		model.addAttribute("p", p);
		model.addAttribute("authUser", session.getAttribute("authUser"));
		return "board/list";
	}

	@RequestMapping("/view/{no}")
	public String view(
			HttpSession session, 
			@PathVariable("no") int no, 
			Model model) {
		boolean writer = true;
		boolean login = true;
		// 유저인지 확인과 작성자인지 확인
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		BoardVo vo = boardService.getContents(no);
		if (authUser == null) {
			login = false;
			writer = false;
		} else {
			if (vo.getUserNo() != authUser.getNo()) {
				writer = false;
			}
		}
		model.addAttribute("vo", vo);
		model.addAttribute("writer",writer);
		model.addAttribute("login",login);
		return "board/view";
	}
	
	@RequestMapping("/update/{no}")
	public String update(HttpSession session,@PathVariable("no") int no,Model model) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/board";
		}
		BoardVo vo = boardService.getContents(no);
		System.out.println(vo);
		model.addAttribute("vo",vo);
		return "board/modify";
	}
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(HttpSession session, BoardVo vo) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/board";
		}
		System.out.println(vo);
		boardService.updateContents(vo);
		return "redirect:/board/view/"+vo.getNo();
	}
	
	@RequestMapping("/delete/{no}")
	public String view(HttpSession session, @PathVariable("no") int no) {
		// access control
				UserVo authUser = (UserVo) session.getAttribute("authUser");
				if(authUser==null) {
					return "redirect:/";
				}
//				if(authUser.getNo()!=Ser)
				return "redirect:/board";
				///////////////////////////
	}
}
