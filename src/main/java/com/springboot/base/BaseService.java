package com.springboot.base;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T> {
	/**
	 * 带条件查询，条件可以为null，既没有条件；返回list对象集合
	 * @param entity
	 * @return
	 */
	public List<T> find(T entity); 
	/**
	 * 只查询一个
	 * @param id
	 * @return
	 */
	public T get(Serializable id); 
	/**
	 * 插入，用实体作为参数
	 * @param entity
	 */
	public void insert(T entity); 
	/**
	 * 修改，用实体作为参数
	 * @param entity
	 */
	public void update(T entity); 
	/**
	 * 按id删除，删除一条；支持整数型和字符串类型ID
	 * @param id
	 */
	public void deleteById(Serializable id); 
	/**
	 * 批量删除；支持整数型和字符串类型ID
	 * @param ids
	 */
	public void delete(Serializable[] ids); 
}
