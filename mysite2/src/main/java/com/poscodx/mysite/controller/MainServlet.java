package com.poscodx.mysite.controller;

import javax.servlet.ServletException;

import com.poscodx.mysite.controller.action.main.MainAction;


public class MainServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		String config = getServletConfig().getInitParameter("config");
		System.out.println("MainController.init() called:"+config);
		super.init();
	}

	@Override
	protected Action getAction(String actionName) {
		return new MainAction();
	}

//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.getRequestDispatcher("/WEB-INF/views/main/index.jsp").forward(request, response);		
//	}

//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doGet(request, response);
//	}

}
