package cn.terrylam.chariot.admin.controller;

import cn.terrylam.framework.enums.ResponseCodeEnum;
import cn.terrylam.framework.util.ResponseResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class LoginController extends BaseController {

	@RequestMapping(value = { "/admin/login" }, method = RequestMethod.GET)
	public String login() {

		return "admin/login";
	}

	@RequestMapping(value = { "/admin/login" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult<?> login(@RequestParam("username") String username, @RequestParam("password") String password,
								   ModelMap model) {

		try {
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);
			return ResponseResult.build(ResponseCodeEnum.SUCCESS);

		} catch (AuthenticationException e) {
			return ResponseResult.build(ResponseCodeEnum.ERROR, e.getMessage());
		}
	}

	@RequestMapping(value = { "/admin/logout" }, method = RequestMethod.GET)
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return redirect("/admin/login");
	}

}
