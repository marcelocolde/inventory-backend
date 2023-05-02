package com.marcelo.inventory.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.marcelo.inventory.model.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseRest {
	
	private List<Map<String,String>> metadata = new ArrayList<>();
	private List<Product> products;
	
	public void setMetadata(String code, String type, String date) {
		Map<String,String> map = new HashMap<>();
		map.put("type", type);
		map.put("code", code);
		map.put("date", date);
		metadata.add(map);
	}
	
}
