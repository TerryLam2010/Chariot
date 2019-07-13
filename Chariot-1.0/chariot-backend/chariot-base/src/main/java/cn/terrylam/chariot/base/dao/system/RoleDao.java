package cn.terrylam.chariot.base.dao.system;


import cn.terrylam.framework.util.Pager;
import cn.terrylam.chariot.base.dao.BaseDao;
import cn.terrylam.chariot.base.entity.system.Role;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleDao extends BaseDao<Role> {

	public Pager<Role> pager(String name);

    public Set<Role> getAllRoleByUserId(Long userId);
}
