package com.poscodx.mysite.controller;

//import java.util.Enumeration;
//
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
