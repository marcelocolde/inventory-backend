package com.marcelo.inventory.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.marcelo.inventory.model.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponseRest {
	
//	private CategoryResponse categoryResponse = new CategoryResponse();
	private List<Map<String,String>> metadata = new ArrayList<>();
	private List<Category> categoryList;

	public List<Map<String, String>> getMetadata() {
		return metadata;
	}

	public void setMetadata(String type, String code, String date) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("type", type);
		map.put("code", code);
		map.put("date", date);
		metadata.add(map);
	}
}
