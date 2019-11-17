package cn.terrylam.chariot.base.dao.system;

import cn.terrylam.chariot.base.dao.BaseDao;
import cn.terrylam.chariot.base.entity.system.RoleResource;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleResourceDao extends BaseDao<RoleResource> {


    public void deleteRoleResource(Long roleId);

    public void createRoleResource(Long roleId, String[] resourceIds);
}
