package cn.terrylam.chariot.admin.controller.system.form;


import cn.terrylam.chariot.base.entity.system.Resource;
import cn.terrylam.chariot.base.form.XAdminPageBase;

public class ResourcePageForm extends XAdminPageBase<Resource> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected ResourcePageForm() {
		super(Resource.class);
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
