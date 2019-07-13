package cn.terrylam.chariot.base.service.impl;

import cn.terrylam.framework.util.Pager;
import cn.terrylam.chariot.base.dao.BaseDao;
import cn.terrylam.chariot.base.service.BaseService;
import com.github.pagehelper.PageHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;

import java.util.List;

@Log4j2
public class BaseServiceImpl<M extends BaseDao<T>, T> implements BaseService<T> {

    @Autowired
    protected M baseDao;

    @Override
    public Integer insert(T entity) {
        return baseDao.insertUseGeneratedKeys(entity);
       // return baseDao.insert(entity);
    }

    @Override
    public int delete(Serializable id) {
        return baseDao.deleteByPrimaryKey(id);
    }

    @Override
    public int update(T entity) {
        return baseDao.updateByPrimaryKeySelective(entity);
    }

    @Override
    public T findById(Serializable id) {
        //Weekend<T> weekend = new Weekend<T>( (Class < T > ) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[ 0 ]);
        //Example.Criteria criteria = weekend.createCriteria();
        return baseDao.selectByPrimaryKey(id);
    }

    @Override
    public Pager<T> pager(Integer start, Integer pageSize, T t) {
        PageHelper.startPage(start, pageSize);
        List<T> list = baseDao.select(t);
        int totalNum = baseDao.selectCount(t);
        Pager<T> pager = new Pager<>(start,pageSize,totalNum);
        pager.setResult(list);
        return pager;
    }

    @Override
    public int updateNotNull(T entity) {
        return baseDao.updateByPrimaryKeySelective(entity);
    }
}
