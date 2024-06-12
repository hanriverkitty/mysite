package com.poscodx.mysite.controller;

//import java.util.Enumeration;
//
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
