package cn.terrylam.chariot.admin.service;

import java.util.List;

import cn.terrylam.chariot.admin.controller.system.form.RolePageForm;
import cn.terrylam.chariot.base.entity.system.Role;
import cn.terrylam.chariot.base.service.BaseService;
import cn.terrylam.framework.util.Pager;
import org.springframework.stereotype.Service;


@Service
public interface RoleService extends BaseService<Role> {

	public long create(Role obj);

	public List<Role> findAll();

	public Pager<Role> pager(RolePageForm rolePageForm);

	public void delete(Role role);

}
