package cn.terrylam.chariot.base.dao.system;

import cn.terrylam.chariot.base.dao.BaseDao;
import cn.terrylam.chariot.base.entity.system.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends BaseDao<User> {

    public List<User> pageUserByUserName(@Param("userName") String userName);
}
