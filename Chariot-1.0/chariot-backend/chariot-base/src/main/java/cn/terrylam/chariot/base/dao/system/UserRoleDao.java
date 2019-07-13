package cn.terrylam.chariot.base.dao.system;

import cn.terrylam.chariot.base.dao.BaseDao;
import cn.terrylam.chariot.base.entity.system.UserRole;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDao extends BaseDao<UserRole> {


    void createUserRole(Long userId, String[] roleIds);

    void deleteRoleByUser(Long userId);
}
