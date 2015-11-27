package com.venustech.jccp.doclibs.controller.validator;

import com.jfinal.core.Controller;
import com.jfinal.i18n.I18n;
import com.jfinal.i18n.Res;
import com.jfinal.validate.Validator;

public class LoginValidator extends Validator {

	@Override
	protected void handleError(Controller paramController) {
		paramController.redirect("admin-index.html");
	}

	@Override
	protected void validate(Controller paramController) {
		Res res = I18n.use("errors");
		validateToken("loginToken", "errmsg", res.get("common.token.error"));
		validateRequired("user", "errmsg", res.get("login.user.error"));
		validateRequired("pass", "errmsg", res.get("login.pass.error"));
		
	}

}
