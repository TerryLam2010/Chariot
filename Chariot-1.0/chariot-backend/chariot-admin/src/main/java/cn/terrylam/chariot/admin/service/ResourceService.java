package cn.terrylam.chariot.admin.service;

import cn.terrylam.chariot.admin.controller.system.form.ResourcePageForm;

import java.util.List;

import cn.terrylam.chariot.admin.vo.ZtreeView;
import cn.terrylam.chariot.base.entity.system.Resource;
import cn.terrylam.chariot.base.service.BaseService;
import cn.terrylam.framework.util.Pager;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;


@Service
public interface ResourceService extends BaseService<Resource> {



	public Pager<Resource> pageByName(ResourcePageForm resourcePageForm) ;

	public List<ZtreeView> tree(int roleId);

	/**
	 *
	 * @param resource
	 * @return
	 */
	public long createOrUpdate(Resource resource) ;


	public List<Resource> getAllResource();

	public void modifyRoleResource(Long roleId, String resourceIdsStr);

	public List<JSONObject> treeResource(List<Resource> resources,List<Long> resourceIds);

	public List<Resource> list();

	public void delete(Resource resource);

	public int deleteBatch(String[] ids);

	public void updateIsHide(long id ,boolean isHide);
}
