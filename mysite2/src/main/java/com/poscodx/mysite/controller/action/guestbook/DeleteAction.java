package com.poscodx.mysite.controller.action.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.GuestbookDao;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");
		Long no1 = Long.parseLong(no);
		String password = request.getParameter("password");
		int result = new GuestbookDao().deleteByNo(no1, password);
		if (result==0) 
		{
			request
				.getRequestDispatcher("/WEB-INF/views/guestbook/passwordfail.jsp")
			    .forward(request, response);
			
		}
		else response.sendRedirect(request.getContextPath()+"/guestbook");
	}
}
