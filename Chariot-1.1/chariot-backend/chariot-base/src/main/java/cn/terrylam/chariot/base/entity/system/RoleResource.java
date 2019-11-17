package cn.terrylam.chariot.base.entity.system;


import cn.terrylam.framework.util.SpringCtxUtils;
import cn.terrylam.chariot.base.dao.system.ResourceDao;
import cn.terrylam.chariot.base.dao.system.RoleDao;

import javax.persistence.Table;
import java.io.Serializable;

@Table(name="tb_role_resource")
public class RoleResource implements Serializable {

	private Long roleId;

	private Long resourceId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Role getRole() {
		return SpringCtxUtils.getBean(RoleDao.class).selectByPrimaryKey(roleId);
	}
	
	public Resource getResource() {
		return SpringCtxUtils.getBean(ResourceDao.class).selectByPrimaryKey(resourceId);
	}
}
