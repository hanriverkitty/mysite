package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

public class AddAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		// Access Control
		if (session == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		BoardVo vo = new BoardVo();
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		int user_no = authUser.getNo().intValue();
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setUserNo(user_no);
		new BoardDao().insert(vo);
	
		///////////////////////////////////////////////////////////
		response.sendRedirect(request.getContextPath()+"/board");
	}

}
