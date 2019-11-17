package cn.terrylam.chariot.admin.controller;

import cn.terrylam.chariot.admin.service.UserService;
import cn.terrylam.chariot.base.entity.system.User;
import cn.terrylam.framework.enums.ResponseCodeEnum;
import cn.terrylam.framework.exception.AppException;
import cn.terrylam.framework.util.MD5Utils;
import cn.terrylam.framework.util.ResponseResult;
import cn.terrylam.framework.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;


@Controller
public class AccountController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/admin/user/password/update"}, method = RequestMethod.GET)
    public String showPpdatePassWord() {
        return "admin/loginUser/updatePassword";
    }

    @RequestMapping(value = {"/admin/user/password/update"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult updatePassWord(String userName, String oldPassword, String newPassword, String rePassword) throws AppException {

        Subject currentuser = SecurityUtils.getSubject();
        if (!currentuser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(userName, oldPassword);

            currentuser.login(token);
            if (currentuser.isAuthenticated()) {

                User user = (User) currentuser.getPrincipal();
                String tips = checkPassword(newPassword, rePassword, user);

                if (ResponseCodeEnum.SUCCESS.getDesc().equals(tips)) {
                    currentuser.logout();
                    return ResponseResult.success();
                } else {

                    return ResponseResult.build(ResponseCodeEnum.BAD_REQUEST, tips);
                }

            } else {
                return ResponseResult.build(ResponseCodeEnum.BAD_REQUEST, "原密码不正确");
            }
        } else {
            User user = (User) currentuser.getPrincipal();
            String tips = checkPassword(newPassword, rePassword, user);
            if (ResponseCodeEnum.SUCCESS.getDesc().equals(tips)) {
                currentuser.logout();
                return ResponseResult.success();
            } else {

                return ResponseResult.build(ResponseCodeEnum.BAD_REQUEST, tips);
            }
        }
    }

    private String checkPassword(String newPassword, String rePassword, User user) throws AppException {
        if (StringUtils.isBlank(newPassword)) {

            return "新密码不能为空";
        } else if (newPassword.length() < 6 || newPassword.length() > 12) {

            return "新密码6-12位";
        } else if (!newPassword.equals(rePassword)) {

            return "两次输入不相同";
        } else {

            user.setPassword(MD5Utils.md5(newPassword));
            user.setUpdateTime(new Date());
            userService.update(user);
            return ResponseCodeEnum.SUCCESS.getDesc();
        }
    }

    @RequestMapping(value = {"/admin/usermsg/update"}, method = RequestMethod.GET)
    public String showUpdateUsermsg() {
        return "admin/loginUser/updateUsermsg";
    }

    @RequestMapping(value = {"/admin/usermsg/update"}, method = RequestMethod.POST)
    public String updateUsermsg() {
        // TODO 提交保存
        return "admin/loginUser/updateUsermsg";
    }

}
