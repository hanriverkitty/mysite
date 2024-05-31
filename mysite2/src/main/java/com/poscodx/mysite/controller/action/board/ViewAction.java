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

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		boolean login = true;
		boolean writer = true;
		// Access Control
		if (session == null) {
			login=false;
		}
		
		String no = request.getParameter("no");
		int board_no = Integer.parseInt(no);
		BoardVo vo = new BoardDao().findByNo(board_no);
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");		
		if (authUser == null) {
			login=false;
			writer=false;
		} else {
			if(vo.getUserNo()!=authUser.getNo()) {
				writer=false;
			}
		}
		
		request.setAttribute("vo", vo);
		request.setAttribute("login", login);
		request.setAttribute("writer", writer);
		request
			.getRequestDispatcher("/WEB-INF/views/board/view.jsp")
			.forward(request, response);

	}

}
