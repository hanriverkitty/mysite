package com.poscodx.mysite.controller;

import java.util.Map;

import com.poscodx.mysite.controller.action.board.AddAction;
import com.poscodx.mysite.controller.action.board.AddFormAction;
import com.poscodx.mysite.controller.action.board.DeleteAction;
import com.poscodx.mysite.controller.action.board.ListAction;
import com.poscodx.mysite.controller.action.board.ReplyAction;
import com.poscodx.mysite.controller.action.board.ReplyFormAction;
import com.poscodx.mysite.controller.action.board.UpdateAction;
import com.poscodx.mysite.controller.action.board.UpdateFormAction;
import com.poscodx.mysite.controller.action.board.ViewAction;

public class BoardServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;

	private Map<String,Action> mapAction = Map.of(
			"board", new ListAction(),
			"write", new AddAction(),
			"addform",new AddFormAction(),
			"delete",new DeleteAction(),
			"updateform", new UpdateFormAction(),
			"update",new UpdateAction(),
			"view",new ViewAction(),
			"replyform", new ReplyFormAction(),
			"reply",new ReplyAction()
			);
	
	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new ListAction());
	}
}
