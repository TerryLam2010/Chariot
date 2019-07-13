package cn.terrylam.chariot.admin.controller.system.form;


import cn.terrylam.chariot.base.entity.system.User;
import cn.terrylam.chariot.base.form.XAdminPageBase;

public class UserPageForm extends XAdminPageBase<User> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected UserPageForm() {
		super(User.class);
	}

	private String nickName;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


}
