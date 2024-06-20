package com.poscodx.mysite.controller;

//import java.util.Enumeration;
//
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poscodx.mysite.security.AuthUser;
import com.poscodx.mysite.vo.UserVo;

@Controller
public class MainController {
	
	@RequestMapping({"/","/main"})
	public String index(
			// HttpServletRequest request
			) {
//		ServletContext sc = request.getServletContext();
//		Enumeration<String> names = (Enumeration<String>)sc.getAttributeNames();
//		while(names.hasMoreElements()){
//			String name = names.nextElement();
//			System.out.println(name);
//		}
		return "main/index";
	}
	
	@ResponseBody
	@RequestMapping("/msg01")
	public String message01() {
		return "Hello World";
	}
	
	@ResponseBody
	@RequestMapping("/msg02")
	public String message02(String name) {
		return "안녕~~" + name;
	}
	
	@ResponseBody
	@RequestMapping("/msg03")
	public Object message03() {
		UserVo vo = new UserVo();
		vo.setNo(1L);
		vo.setName("둘리");
		vo.setEmail("dadw@awd");
		return vo;
	}
}
