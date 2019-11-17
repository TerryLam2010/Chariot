package cn.terrylam.chariot.admin.service.impl;

import cn.terrylam.chariot.admin.controller.system.form.RolePageForm;
import cn.terrylam.chariot.admin.service.RoleService;
import cn.terrylam.chariot.base.dao.system.RoleDao;
import cn.terrylam.chariot.base.entity.system.Role;
import cn.terrylam.chariot.base.service.impl.BaseServiceImpl;
import cn.terrylam.framework.util.Pager;
import cn.terrylam.framework.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleDao, Role> implements RoleService {

	@Override
	public long create(Role obj) {
		obj.setCreateTime(new Date());
		return baseDao.insert(obj);
	}

	@Override
	public List<Role> findAll() {
		return baseDao.selectAll();
	}

	@Override
	public Pager<Role> pager(RolePageForm rolePageForm) {

		Example example = new Example(Role.class);
		if(StringUtils.isNotBlank(rolePageForm.getName())){
			example.createCriteria().andLike("name", "%"+rolePageForm.getName()+"%");
		}
		Page<Role> objects = PageHelper.startPage(rolePageForm.getPage(), rolePageForm.getLimit(), true);
		List<Role> roles = baseDao.selectByExample(example);

		Pager<Role> pager = new Pager<Role>(rolePageForm.getPage(), rolePageForm.getLimit(),(int)objects.getTotal());
		pager.setResult(roles);
		return pager;
	}

	@Override
	public void delete(Role role) {
		role.setStatus(0);
		role.setUpdateTime(new Date());
		baseDao.updateByPrimaryKeySelective(role);
	}

}
