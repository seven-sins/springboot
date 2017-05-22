package com.springboot.controller.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.base.BaseController;
import com.springboot.config.annotation.Valid;
import com.springboot.po.TopicCategory;
import com.springboot.service.sys.TopicCategoryService;

/**
 * @author seven sins
 * @date 2017年5月8日 下午11:00:05
 */
@RequestMapping("/api")
@RestController
public class TopicCategoryController extends BaseController {

	@Autowired
	TopicCategoryService topicCategoryService;
	
	@GetMapping("/topicCategory")
	public Object list(TopicCategory topicCategory){
		List<TopicCategory> dataList = topicCategoryService.find(topicCategory);
		int total = topicCategoryService.count(topicCategory);
		
		return super.resultMap(200, dataList, total);
	}

	@GetMapping("/topicCategory/{id}")
	public Object get(@PathVariable("id") int id){
		TopicCategory topicCategory = topicCategoryService.get(id);

		return super.resultMap(200, topicCategory);
	}
	
	@PostMapping("/topicCategory")
	public Object create(@Valid @RequestBody TopicCategory topicCategory){
		topicCategoryService.insert(topicCategory);
		
		return SUCCESS;
	}
	
	@PutMapping("/topicCategory/{id}")
	public Object update(@RequestBody TopicCategory topicCategory, @PathVariable("id") int id){
		topicCategory.setId(id);
		topicCategoryService.update(topicCategory);
		
		return SUCCESS;
	}
	
	@DeleteMapping("/topicCategory/{id}")
	public Object remove(@PathVariable("id") int id){
		topicCategoryService.deleteById(id);
		
		return SUCCESS;
	}
}
