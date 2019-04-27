package com.zxning.library.db.dao;

import java.util.List;

/**
 * Created by moziqi on 2015/1/18 0018.
 */
public interface BaseDao<T> {
    public boolean save(final T entity);

    public boolean update(final T entity);

    public boolean delete(final T entity);

    public List<T> findAll();

    public T findOneById(final long id);

    public long countAll();

    public List<T> findAllByLimit(final long currentPage, final long size);
}
