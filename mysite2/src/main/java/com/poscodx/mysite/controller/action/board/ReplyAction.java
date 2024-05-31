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

public class ReplyAction implements Action {

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

		String groupNo = request.getParameter("groupNo");
		String orderNo = request.getParameter("orderNo");
		String depth = request.getParameter("depth");
		String no = request.getParameter("no");
		
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		int gNo = Integer.parseInt(groupNo);
		int oNo = Integer.parseInt(orderNo);
		int intDepth = Integer.parseInt(depth);
		int userNo = authUser.getNo().intValue();
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setgNo(gNo);
		vo.setoNo(oNo);
		vo.setDepth(intDepth);
		vo.setUserNo(userNo);
		
		new BoardDao().reply(vo);
		response.sendRedirect(request.getContextPath()+"/board");

	}

}
