package cn.terrylam.chariot.base.service;


import cn.terrylam.framework.util.Pager;

import java.io.Serializable;

public interface BaseService<T> {

    public Integer insert(T entity);//增加

    public int delete(Serializable id);//删除

    public int update(T entity);//修改

    public int updateNotNull(T entity);  //修改非空

    public T findById(Serializable id);//查询

    public Pager<T> pager(Integer start, Integer pageSize, T t);//分页查询

}
