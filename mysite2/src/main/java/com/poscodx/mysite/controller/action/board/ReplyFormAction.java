package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;

public class ReplyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("groupNo", request.getParameter("groupNo"));
		request.setAttribute("orderNo", request.getParameter("orderNo"));
		request.setAttribute("depth", request.getParameter("depth"));
		request.setAttribute("no", request.getParameter("no"));
		
		request
			.getRequestDispatcher("/WEB-INF/views/board/replyform.jsp")
			.forward(request, response);

	}

}
