package com.springboot.config.annotation;

import java.lang.reflect.Field;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.config.exception.ValidatorException;

/**
 * @author seven sins
 * @date 2017年5月8日 下午10:57:44
 */
public class Validator {
    private static Rule rule;
    private JSONArray result = new JSONArray();

    public Validator() {
    	super();
    }

    public Validator(Object object) {
        super();
        this.init(object);
        if(this.result.size() > 0){
        	throw new ValidatorException(2, this.result);
        }
    }

	private void init(Object object) {
		// 获取object的类型
		Class<? extends Object> clazz = object.getClass();
		// 获取该类型声明的成员
		Field[] fields = clazz.getDeclaredFields();
		// 遍历属性
		for (Field field : fields) {
			// 禁用安全检查，提升性能
			field.setAccessible(true);
			this.validate(field, object);
			// 恢复状态
			field.setAccessible(false);
		}
	}

    private void validate(Field field, Object object) {
        String name = field.getName();
        Object value = null;

        rule = field.getAnnotation(Rule.class);
        if (rule == null) return;
        
        name = !this.isEmpty(rule.desc()) ? rule.desc() : name;
        
        try {
            value = field.get(object);
        } catch (IllegalAccessException e) {
            this.addResult(name, "获取value出错");
            return;
        }
        
        if (!rule.nullable()) {
            if (this.isEmpty(value)) {
            	this.addResult(name, "不能为空");
            	return;
            }
        }else{
        	if(this.isEmpty(value)){
        		return;
        	}
        }

        if (value.toString().length() > rule.max() && rule.max() != 0) {
        	this.addResult(name, "长度不能超过" + rule.max());
        	return;
        }

        if (value.toString().length() < rule.min() && rule.min() != 0) {
        	this.addResult(name, "长度不能小于" + rule.min());
        	return;
        }

        if (!rule.regex().equals("")) {
            if (value.toString().matches(rule.regex())) {
            	this.addResult(name, "格式不正确");
            	return;
            }
        }
    }
    
    private boolean isEmpty(Object obj){
    	return obj == null || "".equals(obj.toString());
    }
    
    private void addResult(String field, String error){
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put("field", field);
    	jsonObject.put("error", error);
    	
    	this.result.add(jsonObject);
    }
}
