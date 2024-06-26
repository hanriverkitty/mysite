package com.poscodx.mysite.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public String index(
			HttpSession session, 
			@RequestParam(value = "p", required = true, defaultValue = "1") int p,
			@RequestParam(value = "keyword", required = true, defaultValue = "") String keyword, 
			Model model) {
		
		//login control
		boolean login = true;
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			login = false;
		}
		///////////////////////////////////////////////////////////////////////
		Map<String, Object> map = boardService.getContentsList(keyword, p);
		model.addAttribute("map", map);
		model.addAttribute("keyword", keyword);
		model.addAttribute("p", p);
		model.addAttribute("authUser", session.getAttribute("authUser"));
		model.addAttribute("login",login);
		return "board/list";
	}

	@RequestMapping("/view/{no}")
	public String view(HttpSession session, @PathVariable("no") int no, Model model) {
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
		model.addAttribute("writer", writer);
		model.addAttribute("login", login);
		return "board/view";
	}

	@RequestMapping("/update/{no}")
	public String update(HttpSession session, @PathVariable("no") int no, Model model) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/board";
		}
		BoardVo vo = boardService.getContents(no);
		model.addAttribute("vo", vo);
		return "board/modify";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpSession session, BoardVo vo) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/board";
		}
		boardService.updateContents(vo);
		return "redirect:/board/view/" + vo.getNo();
	}

	@RequestMapping("/delete/{no}")
	public String view(Authentication authentication, @PathVariable("no") int no, @RequestParam("p") int p,
			@RequestParam("keyword") String keyword) {
		
		BoardVo vo = boardService.findByNo(no);
		UserVo authUser = (UserVo)authentication.getPrincipal();
		///////////////////////////////////////////
		boardService.deleteContents(no);
		String decodeKwd="";
		try {
			URLEncoder.encode(keyword, "utf-8");
		} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
		}
		return "redirect:/board?p=" + p + "&keyword=" + decodeKwd;

	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add() {
		return "board/write";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(
			Authentication authentication,
			@ModelAttribute BoardVo vo
			) {
		// access control
		UserVo authUser = (UserVo)authentication.getPrincipal();
		vo.setUserNo(authUser.getNo().intValue());
		boardService.addContents(vo);
		return "redirect:/board";
	}
	
	@RequestMapping(value="/reply",method=RequestMethod.GET)
	public String reply(
			HttpSession session,
			@RequestParam("gNo") int gNo,
			@RequestParam("oNo") int oNo,
			@RequestParam("no") int no,
			@RequestParam("depth") int depth,
			Model model
			) {
			Long userNo = ((UserVo) session.getAttribute("authUser")).getNo();
			model.addAttribute("gNo", gNo);
			model.addAttribute("oNo", oNo);
			model.addAttribute("no", no);
			model.addAttribute("depth", depth);
			model.addAttribute("userNo", userNo.intValue());
			return "board/replyform";
	}
	@RequestMapping(value="/reply",method=RequestMethod.POST)
	public String reply(@ModelAttribute BoardVo vo) {
		boardService.addContents(vo);
		return "redirect:/board";
	}
}
