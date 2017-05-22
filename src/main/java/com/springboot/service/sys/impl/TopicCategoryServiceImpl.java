package com.springboot.service.sys.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.springboot.mapper.sys.TopicCategoryMapper;
import com.springboot.po.TopicCategory;
import com.springboot.service.sys.TopicCategoryService;

@Service
public class TopicCategoryServiceImpl implements TopicCategoryService{

	@Autowired
	TopicCategoryMapper topicCategoryMapper;
	
	@Override
	public List<TopicCategory> find(TopicCategory entity) {
		PageHelper.startPage(entity.getIndex(), entity.getSize());
		return topicCategoryMapper.find(entity);
	}

	@Override
	public int count(TopicCategory entity) {
		return topicCategoryMapper.count(entity);
	}

	@Override
	public TopicCategory get(Serializable id) {
		return topicCategoryMapper.get(id);
	}

	@Override
	public void insert(TopicCategory entity) {
		topicCategoryMapper.insert(entity);		
	}

	@Override
	public void update(TopicCategory entity) {
		topicCategoryMapper.update(entity);		
	}

	@Override
	public void deleteById(Serializable id) {
		topicCategoryMapper.deleteById(id);		
	}

	@Override
	public void delete(Serializable[] ids) {
		topicCategoryMapper.delete(ids);		
	}

}
