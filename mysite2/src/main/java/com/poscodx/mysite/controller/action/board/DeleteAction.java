package com.poscodx.mysite.controller.action.board;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.UserVo;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		// Access Control
		if (session == null) {
			response.sendRedirect(request.getContextPath()+"/board");
			return;
		}
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			response.sendRedirect(request.getContextPath()+"/board");
			return;
		}
		///////////////////////////////////////////////////////////
		String num = request.getParameter("no");
		String page = request.getParameter("p");
		String keyword = request.getParameter("kwd");
		
		// url 에 한글을 넘기 때에는 인코딩해줘야된다
		String decodeKwd = URLEncoder.encode(keyword, "utf-8");
		int no = Integer.parseInt(num);
		new BoardDao().delete(no);
		response.sendRedirect(request.getContextPath()+"/board?p="+page+"&kwd="+decodeKwd);

	}

}
