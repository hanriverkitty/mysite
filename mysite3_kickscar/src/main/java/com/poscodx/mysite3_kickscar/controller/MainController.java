package com.poscodx.mysite3_kickscar.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.mysite3_kickscar.service.SiteService;

@Controller
public class MainController {
	@RequestMapping({"/", "/main"})
	public String index() {
		return "main/index";
	}
}