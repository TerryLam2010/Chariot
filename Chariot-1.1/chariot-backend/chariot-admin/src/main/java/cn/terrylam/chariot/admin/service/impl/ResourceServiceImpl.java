package cn.terrylam.chariot.admin.service.impl;

import cn.terrylam.chariot.admin.controller.system.form.ResourcePageForm;
import cn.terrylam.chariot.admin.service.ResourceService;
import cn.terrylam.chariot.admin.vo.ZtreeView;
import cn.terrylam.chariot.base.dao.system.ResourceDao;
import cn.terrylam.chariot.base.dao.system.RoleDao;
import cn.terrylam.chariot.base.dao.system.RoleResourceDao;
import cn.terrylam.chariot.base.entity.system.Resource;
import cn.terrylam.chariot.base.entity.system.Role;
import cn.terrylam.chariot.base.entity.system.RoleResource;
import cn.terrylam.chariot.base.service.impl.BaseServiceImpl;
import cn.terrylam.framework.util.Pager;
import cn.terrylam.framework.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class ResourceServiceImpl extends BaseServiceImpl<ResourceDao, Resource> implements ResourceService {

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private RoleResourceDao roleResourceDao;

	@Override
	public Pager<Resource> pageByName(ResourcePageForm resourcePageForm) {
		Example example = new Example(Role.class);
		if(StringUtils.isNotBlank(resourcePageForm.getName())){
			example.createCriteria().andLike("name", "%"+resourcePageForm.getName()+"%");
		}
		Page<Resource> objects = PageHelper.startPage(resourcePageForm.getPage(), 300, true);
		List<Resource> resources = baseDao.selectByExample(example);

		Pager<Resource> pager = new Pager<Resource>(resourcePageForm.getPage(), resourcePageForm.getLimit(),(int)objects.getTotal());
		pager.setResult(resources);
		return pager;
	}

	@Override
	public List<ZtreeView> tree(int roleId) {
		List<ZtreeView> resulTreeNodes = new ArrayList<ZtreeView>();
		Role role = roleDao.selectByPrimaryKey(roleId);
		Set<Resource> roleResources = role.getResources();
		resulTreeNodes.add(new ZtreeView(0L, null, "系统菜单", true));
		ZtreeView node;
		List<Resource> all = baseDao.selectAll();
		for (Resource resource : all) {
			node = new ZtreeView();
			node.setId(Long.valueOf(resource.getId()));
			if (resource.getParent() == null) {
				node.setpId(0L);
			} else {
				node.setpId(Long.valueOf(resource.getParent().getId()));
			}
			node.setName(resource.getName());
			if (roleResources != null && roleResources.contains(resource)) {
				node.setChecked(true);
			}
			resulTreeNodes.add(node);
		}
		return resulTreeNodes;
	}

	/**
	 *
	 * @param resource
	 * @return
	 */
	@Override
	public long createOrUpdate(Resource resource) {
		resource.setUpdateTime(new Date());
		if(resource.getId()>0){
			baseDao.updateByPrimaryKey(resource);
			return resource.getId();
		}else {
			resource.setCreateTime(new Date());
			return baseDao.insert(resource);
		}
	}

	@Override
	public List<Resource> getAllResource(){
		List<Resource> resources = baseDao.getByType(0);
		for (Resource resource : resources) {
			resource.setChildResourses(baseDao.getResourceByParentId(resource.getId()));
		}
		return resources;
	}

	@Override
	public void modifyRoleResource(Long roleId, String resourceIdsStr) {
		String[] resourceIds = resourceIdsStr.split(",");
		Example example = new Example(RoleResource.class);
		example.createCriteria().andEqualTo("roleId",roleId);
		roleResourceDao.deleteByExample(example);
		this.createRoleResource(roleId,resourceIds);
	}

	@Override
	public List<JSONObject> treeResource(List<Resource> resources,List<Long> resourceIds){
		List<JSONObject> result = new ArrayList<>();
		if(resources!=null && !resources.isEmpty()){
			for (Resource resource : resources) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id",resource.getId());
				jsonObject.put("title",resource.getName());
				if(resourceIds!=null && !resourceIds.isEmpty()){
					if((resource.getChildResourses()==null || resource.getChildResourses().isEmpty()) && resourceIds.contains(resource.getId())){
						jsonObject.put("checked", true);
					}

				}
				result.add(jsonObject);
				if(resource.getChildResourses()!=null && !resource.getChildResourses().isEmpty()){
					jsonObject.put("children",treeResource(resource.getChildResourses(),resourceIds));
				}
			}
		}
		return result;
	}

	@Override
	public List<Resource> list(){
		return baseDao.selectAll();
	}

	@Override
	public void delete(Resource resource) {
		baseDao.delete(resource);
	}

	@Override
	public int deleteBatch(String[] ids){
		for (String id : ids) {
			baseDao.deleteByPrimaryKey(Long.parseLong(id));
		}
		return 1;
	}

	@Override
	public void updateIsHide(long id ,boolean isHide){
		Resource resource=new Resource();
		resource.setId(id);
		resource.setIsHide(isHide?1:0);
		baseDao.updateByPrimaryKeySelective(resource);
	}

	public void createRoleResource(Long roleId,String[] resourceIds){
		List<RoleResource> roleResources = new ArrayList<>();
		for (String resourceId : resourceIds) {
			RoleResource roleResource = new RoleResource();
			roleResource.setResourceId(Long.parseLong(resourceId));
			roleResource.setRoleId(roleId);
			roleResources.add(roleResource);
		}
		roleResourceDao.insertList(roleResources);
	}
}
