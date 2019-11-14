package cn.terrylam.chariot.base.dao.system;

import cn.terrylam.framework.util.Pager;
import cn.terrylam.chariot.base.dao.BaseDao;
import cn.terrylam.chariot.base.entity.system.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ResourceDao extends BaseDao<Resource> {


	public Pager<Resource> pageByName(String name);

	public Set<Resource> getAllResourceByRoleId(Long roleId);

	public List<Resource> getByType(Integer type);

	public List<Resource> getResourceByParentId(Long resourceId);

	public List<Resource> findAll();
}
