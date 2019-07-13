package cn.terrylam.chariot.admin.controller.system.form;


import cn.terrylam.chariot.base.entity.system.User;
import cn.terrylam.chariot.base.form.XAdminPageBase;

public class RolePageForm extends XAdminPageBase<User> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected RolePageForm() {
		super(User.class);
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
