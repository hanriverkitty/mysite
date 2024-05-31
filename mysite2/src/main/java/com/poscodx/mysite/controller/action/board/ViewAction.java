package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
		
		// 유저인지 확인과 작성자인지 확인
		UserVo authUser = (UserVo) session.getAttribute("authUser");		
		if (authUser == null) {
			login=false;
			writer=false;
		} else {
			if(vo.getUserNo()!=authUser.getNo()) {
				writer=false;
			}
		}
		
		// 쿠기 읽기
		Cookie[] cookies = request.getCookies();
		int search = 0;
		if(cookies != null && cookies.length > 0) {
			for(Cookie eachCookie : cookies) {
				if(!no.equals(eachCookie.getName())) {
					search++;
				}
			}
		} else {
			// 쿠키들이 비어있는 경우
			Cookie cookie = new Cookie(no, String.valueOf(1));
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(24 * 60 * 60); // 1day
			response.addCookie(cookie);
			new BoardDao().updateHit(board_no);
		}
		if(cookies.length==search) {
			// 해당번호를 가지는 쿠키가 없는 경우 추가
			Cookie cookie = new Cookie(no, String.valueOf(1));
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(24 * 60 * 60); // 1day
			response.addCookie(cookie);
			new BoardDao().updateHit(board_no);
		}
		
		
		request.setAttribute("vo", vo);
		request.setAttribute("login", login);
		request.setAttribute("writer", writer);
		request
			.getRequestDispatcher("/WEB-INF/views/board/view.jsp")
			.forward(request, response);
	}
}
