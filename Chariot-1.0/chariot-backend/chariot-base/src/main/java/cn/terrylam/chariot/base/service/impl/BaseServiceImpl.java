package cn.terrylam.chariot.base.service.impl;

import cn.terrylam.chariot.base.cache.CommonCache;
import cn.terrylam.framework.util.Pager;
import cn.terrylam.chariot.base.dao.BaseDao;
import cn.terrylam.chariot.base.service.BaseService;

import com.github.pagehelper.PageHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.io.Serializable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Log4j2
public class BaseServiceImpl<M extends BaseDao<T>, T> implements BaseService<T> {

    @Autowired
    protected M baseDao;

    @Autowired
    private RedisTemplate<String,T> redisTemplate;

    @Autowired
    private CommonCache<T> commonCache;

    @Override
    public Integer insert(T entity) {
        return baseDao.insertUseGeneratedKeys(entity);
    }

    @Override
    public int delete(Serializable id) {
        return baseDao.deleteByPrimaryKey(id);
    }

    @Override
    public int update(T entity) {
        try {
            Set<EntityColumn> pkColumns = EntityHelper.getPKColumns(entity.getClass());
            for (EntityColumn pkColumn : pkColumns) {
                Object value = pkColumn.getEntityField().getValue(entity);
                redisTemplate.opsForValue().set(entity.getClass().getSimpleName()+"_"+value,entity);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return baseDao.updateByPrimaryKeySelective(entity);
    }

    @Override
    public T findById(Serializable id) {
        Class<T> actualTypeArgument = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        return commonCache.getDataByCache((key)->{
            return baseDao.selectByPrimaryKey(id);
        },actualTypeArgument.getSimpleName()+"_"+id,60, TimeUnit.MINUTES);
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
