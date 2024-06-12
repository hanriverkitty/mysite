package com.poscodx.mysite.controller;

import javax.servlet.ServletContext;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.mysite.security.Auth;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
//	@Autowired
//	private ServletContext servletContext;
//	@Autowired
//	private ApplicationContext applicationContext;
	
	@Auth(role = "ADMIN")
	@RequestMapping("")
	public String main() {
		return "admin/main";
	}

	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}

	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}

	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
