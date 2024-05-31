package com.poscodx.mysite.controller.action.board;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

public class ListAction implements Action{

	private BoardDao dao = new BoardDao();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		boolean login = true;
		// Access Control
		if (session == null) {
			login=false;
		}
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			login=false;
		}
		
		String strPage = Optional.ofNullable(request.getParameter("p")).orElse("1");
		int p = Integer.parseInt(strPage);
		int total;
		List<BoardVo> list;
		
		// 검색
		String keyword = request.getParameter("kwd");
		if(keyword!=null) {
			list = dao.searchAll(keyword, p);
			total = dao.searchCount(keyword);
		} else {
			list = new BoardDao().findAll(p);
			total = new BoardDao().countTotal();
			if(p>total) p=1;
		}
		
		int block = (int)Math.ceil((double)total/5);
		int currentBlock = (int)Math.ceil((double)p/5);
		int startNo = (currentBlock-1)*5+1;
		int endNo = currentBlock*5;
		
		
		request.setAttribute("login", login);
		request.setAttribute("list", list);
		request.setAttribute("authUser", authUser);
		request.setAttribute("p", p);
		request.setAttribute("block", block);
		request.setAttribute("currentBlock", currentBlock);
		request.setAttribute("startNo", startNo);
		request.setAttribute("endNo", endNo);
		request.setAttribute("total", total);
		request.setAttribute("kwd",keyword);
		request
			.getRequestDispatcher("/WEB-INF/views/board/list.jsp")
			.forward(request, response);
	}

}
