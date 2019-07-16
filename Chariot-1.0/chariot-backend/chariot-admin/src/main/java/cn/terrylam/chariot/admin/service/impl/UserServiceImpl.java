package cn.terrylam.chariot.admin.service.impl;

import cn.terrylam.chariot.admin.controller.system.form.UserPageForm;
import cn.terrylam.chariot.admin.service.UserService;
import cn.terrylam.chariot.base.dao.system.UserDao;
import cn.terrylam.chariot.base.dao.system.UserRoleDao;
import cn.terrylam.chariot.base.entity.system.User;
import cn.terrylam.chariot.base.entity.system.UserRole;
import cn.terrylam.chariot.base.service.impl.BaseServiceImpl;
import cn.terrylam.chariot.base.util.IgnorePropertiesUtil;
import cn.terrylam.framework.exception.ObjectNotFoundException;
import cn.terrylam.framework.util.MD5Utils;
import cn.terrylam.framework.util.Pager;
import cn.terrylam.framework.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional(propagation = Propagation.REQUIRED)
@Service
//@EnableCreateCacheAnnotation
public class UserServiceImpl extends BaseServiceImpl<UserDao, User> implements UserService {

   // @CreateCache(name="user.",expire = 100, cacheType = CacheType.REMOTE)
   // private Cache<Long, User> objCache;


    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public User findByUserName(String userName) {
        User user = new User();
        user.setUserName(userName);
        return baseDao.selectOne(user);
    }

    @Override
    public Pager<User> pagerUserByNickName(String nickName, int pageNo, int pageSize) {
        Page<Object> objects = PageHelper.startPage(pageNo, pageSize, true);
        List<User> users = baseDao.pageUserByUserName(nickName);
        Pager<User> pager = new Pager<User>(pageNo, pageSize, (int) objects.getTotal());
        pager.setResult(users);
        return pager;
    }

    @Override
    public Pager<User> pagerUser(UserPageForm userPageForm) {
        Example example = new Example(User.class);
        if (StringUtils.isNotBlank(userPageForm.getNickName())) {
            example.createCriteria().andLike("nickName", "%" + userPageForm.getNickName() + "%");
        }
        Page<Object> objects = PageHelper.startPage(userPageForm.getPage(), userPageForm.getLimit(), true);
        List<User> users = baseDao.selectByExample(example);
        Pager<User> pager = new Pager<User>(userPageForm.getPage(), userPageForm.getLimit(), (int) objects.getTotal());
        pager.setResult(users);
        return pager;
    }

    @Override
    public long saveOrUpdate(User user, String[] roleIds) {

        if (user.getId() <= 0) {
            user.setPassword(MD5Utils.md5(user.getPassword()));
            createUserRole(user.getId(), roleIds);
            return baseDao.insert(user);
        } else {
            User userDO = baseDao.selectByPrimaryKey(user.getId());
            BeanUtils.copyProperties(user, userDO, IgnorePropertiesUtil.getNullPropertyNames(user));
            userDO.setUpdateTime(new Date());
            baseDao.updateByPrimaryKeySelective(userDO);
            userRoleDao.deleteRoleByUser(user.getId());
            createUserRole(user.getId(), roleIds);
            return user.getId();
        }
    }

    @Override
    public void deleteById(long id) throws ObjectNotFoundException {

        User user = baseDao.selectByPrimaryKey(id);
        if (user == null) {
            throw new ObjectNotFoundException(User.class);
        }
        baseDao.delete(user);
    }

    @Override
    public void grant(long userId, String[] roleIds) {
        List<UserRole> list = userRoleDao.selectAll();
        if (list != null && list.size() > 0) {
            for (UserRole userRole : list) {
                userRoleDao.delete(userRole);
            }
        }

        for (String roleIdStr : roleIds) {
            long roleId = Long.parseLong(roleIdStr);
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            userRoleDao.insert(userRole);
        }

    }

    public void createUserRole(long userId, String[] roleIds) {
        List<UserRole> userRoles = new ArrayList<>();
        for (String roleId : roleIds) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(Long.parseLong(roleId));
            userRole.setUserId(userId);
            userRoles.add(userRole);
        }
        userRoleDao.insertList(userRoles);
    }

}
