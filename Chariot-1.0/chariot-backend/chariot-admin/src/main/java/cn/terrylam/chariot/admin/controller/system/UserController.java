package cn.terrylam.chariot.admin.controller.system;

import cn.terrylam.chariot.admin.controller.BaseController;
import cn.terrylam.chariot.admin.controller.system.form.UserPageForm;
import cn.terrylam.chariot.admin.service.RoleService;
import cn.terrylam.chariot.admin.service.UserService;
import cn.terrylam.chariot.base.entity.system.Role;
import cn.terrylam.chariot.base.entity.system.User;
import cn.terrylam.framework.enums.ResponseCodeEnum;
import cn.terrylam.framework.enums.XAdminCodeEnum;
import cn.terrylam.framework.exception.AccessForbiddenException;
import cn.terrylam.framework.exception.AppException;
import cn.terrylam.framework.exception.ObjectNotFoundException;
import cn.terrylam.framework.util.MD5Utils;
import cn.terrylam.framework.util.Pager;
import cn.terrylam.framework.util.ResponseResult;
import cn.terrylam.framework.util.XAdmindResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	@RequestMapping(value = { "/", "/index" })
	public String index() {
		return "admin/user/index";
	}

	@RequestMapping(value = { "/list" })
	@ResponseBody
	public XAdmindResult list(UserPageForm userPageForm) {
		Pager<User> page = userService.pagerUser(userPageForm);
		return XAdmindResult.build(XAdminCodeEnum.SUCCESS, page.getResult(), page.getTotalRow());
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		List<Role> roles = roleService.findAll();
		model.addAttribute("keyWord","新增");
		model.addAttribute("roles",roles);
		model.addAttribute("roleIds",new ArrayList<Long>());
		return "admin/user/form";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable Long id, ModelMap map) {
		User user = userService.findById(id);

		List<Role> roles = roleService.findAll();
		List<Long> roleIds = new ArrayList<>();
		for (Role role : user.getRoles()) {
			roleIds.add(role.getId());
		}
		map.put("user", user);
		map.put("roles",roles);
		map.put("roleIds",roleIds);
		map.put("keyWord","修改");
		return "admin/user/form";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = { "/edit" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult edit(User user, ModelMap map) {
		try {
			String[] ids = user.getRolesStr().split(",");
			userService.saveOrUpdate(user,ids);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseResult.build(ResponseCodeEnum.ERROR, e.getMessage());
		}
		return ResponseResult.success();
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public XAdmindResult delete(@PathVariable long id, ModelMap map) {
		try {
			User user = userService.findById(id);
			if (user == null) {
				throw new ObjectNotFoundException("对象不存在");
			}
			String userName = user.getUserName();
			if ("admin".equals(userName)) {
				throw new AccessForbiddenException("不允许修改admin");
			}
			userService.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return XAdmindResult.build(XAdminCodeEnum.ERROR, e.getMessage());
		}
		return XAdmindResult.success();
	}

	@RequestMapping(value = "/grant/{id}", method = RequestMethod.GET)
	public String grant(@PathVariable Integer id, ModelMap map) {
		User user = userService.findById(id);
		map.put("user", user);

		Set<Role> set = user.getRoles();
		List<Long> roleIds = new ArrayList<Long>();
		for (Role role : set) {
			roleIds.add(role.getId());
		}
		map.put("roleIds", roleIds);

		List<Role> roles = roleService.findAll();
		map.put("roles", roles);
		return "admin/user/grant";
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/grant/{id}", method = RequestMethod.POST)
	public ResponseResult grant(@PathVariable long id, String[] roleIds, ModelMap map) {
		try {
			userService.grant(id, roleIds);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseResult.build(ResponseCodeEnum.ERROR, e.getMessage());
		}
		return ResponseResult.success();
	}

	@ResponseBody
	@RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
	public ResponseResult updateStatus(@PathVariable long id, @RequestParam(value = "deleteStatus",required = false)Boolean deleteStatusBool,
	@RequestParam(value = "locked",required = false)Boolean lockedBool){
		User user = userService.findById(id);
		if(deleteStatusBool!=null){
			Integer deleteStatus = deleteStatusBool ? 1 : 0;
			user.setDeleteStatus(deleteStatus);
		}
		if(lockedBool!=null){
			Integer locked = lockedBool ? 1 : 0;
			user.setLocked(locked);
		}
		user.setUpdateTime(new Date());
		try {
			userService.update(user);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseResult.build(ResponseCodeEnum.ERROR, e.getMessage());
		}
		return ResponseResult.success();
	}

	@RequestMapping(value = "/updatePassword/{id}",method = RequestMethod.GET)
	public String updatePassword(@PathVariable("id")long userId,Model model){
		model.addAttribute("userId",userId);
		return "admin/user/resetPassword";
	}

	@RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult updatePasswordOp(@RequestParam("id")long userId,
								   @RequestParam("newPassword") String password) throws AppException {
		User user = userService.findById(userId);
		user.setPassword(MD5Utils.md5(password));
		userService.update(user);
		return ResponseResult.build(ResponseCodeEnum.SUCCESS);
	}
}
