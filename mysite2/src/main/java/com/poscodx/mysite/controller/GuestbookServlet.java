package com.poscodx.mysite.controller;

import java.util.Map;
import com.poscodx.mysite.controller.action.guestbook.DeleteAction;
import com.poscodx.mysite.controller.action.guestbook.DeleteFormAction;
import com.poscodx.mysite.controller.action.guestbook.GuestbookAction;
import com.poscodx.mysite.controller.action.guestbook.InsertAction;


public class GuestbookServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;

	private Map<String,Action> mapAction = Map.of(
			"guestbook", new GuestbookAction(),
			"deleteform", new DeleteFormAction(),
			"delete",new DeleteAction(),
			"insert",new InsertAction()
			);
	
	
	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new GuestbookAction());
	}
}
