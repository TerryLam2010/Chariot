package cn.terrylam.chariot.admin.service;

import cn.terrylam.chariot.admin.controller.system.form.UserPageForm;
import cn.terrylam.chariot.base.entity.system.User;
import cn.terrylam.chariot.base.service.BaseService;
import cn.terrylam.framework.exception.ObjectNotFoundException;
import cn.terrylam.framework.util.Pager;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED)
@Service
public interface UserService extends BaseService<User> {


	public User findByUserName(String userName);

	public Pager<User> pagerUserByNickName(String nickName, int pageNo, int pageSize) ;

	public Pager<User> pagerUser(UserPageForm userPageForm) ;

	public long saveOrUpdate(User user,String[] roleIds);

	public void deleteById(long id) throws ObjectNotFoundException;

	public void grant(long userId, String[] roleIds);

}
