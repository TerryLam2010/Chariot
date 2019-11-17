package cn.terrylam.chariot.base.entity.system;

import cn.terrylam.framework.util.SpringCtxUtils;
import cn.terrylam.chariot.base.dao.system.RoleDao;
import cn.terrylam.chariot.base.dao.system.UserDao;

import javax.persistence.Table;
import java.io.Serializable;

@Table(name="tb_user_role")
public class UserRole implements Serializable {

	private Long userId;

	private Long roleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public User getUser() {
		return SpringCtxUtils.getBean(UserDao.class).selectByPrimaryKey(userId);
	}

	public Role getRole() {
		return SpringCtxUtils.getBean(RoleDao.class).selectByPrimaryKey(roleId);
	}

}
