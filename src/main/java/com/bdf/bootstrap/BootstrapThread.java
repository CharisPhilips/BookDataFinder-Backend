package com.bdf.bootstrap;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bdf.common.FileUtils;
import com.bdf.common.Global;
import com.bdf.entity.Bookcategory;
import com.bdf.service._BootstrapService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BootstrapThread extends Thread{
	
	private _BootstrapService service;

	public static final String JSON_KEY_CATEGORY="category";
	public static final String JSON_KEY_NAME="name";
	
	public void setService(_BootstrapService apiService) {
		this.service = apiService;
	}
	
	@Override
	public void run() {
		File file = FileUtils.getPdfFilePath(1);
		System.out.println(file.getPath());
		readDBWriteJSon();
		//readJSonWriteDB();
	}
	
	private void readDBWriteJSon() {
		System.out.println("======================= start read JSon ==================================");
		List<Bookcategory> rootList = this.service.getCategoryService().findByLevel(1);
		if(rootList!=null && rootList.size() == 1) {
			Bookcategory root = rootList.get(0);
			putJsonFromDB(root);
			Global.g_category = root;
		}
		System.out.println("======================= finsh read JSon ==================================");
	}

	private void putJsonFromDB(Bookcategory root) {
		long rootId = root.getId();
		List<Bookcategory> subCategoryList = this.service.getCategoryService().findByParent(root.getId());
		if(subCategoryList!=null && subCategoryList.size() > 0) {
			int length = subCategoryList.size();
			for(int i = 0; i < length; i++) {
				Bookcategory category = subCategoryList.get(i);
				if(category!=null) {
					putJsonFromDB(category);
				}
				root.addCategory(category);
			}
		}
	}
	
	private void readJSonWriteDB() {
		
		try {
			String path = System.getProperty("user.dir") + File.separator + "dataCategories.json";
			byte[] mapData = Files.readAllBytes(Paths.get(path));
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String,String> jsonMap = null;
			jsonMap = objectMapper.readValue(mapData, HashMap.class);
			System.out.println("======================= start parse JSon ==================================");
			parseCategories(jsonMap, null, 1, 1);
			System.out.println("======================= finsh parse JSon ==================================");
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
	}
	private void parseCategories(Map root, Long parentId, int level, int order) {
		
		List categories = (List) root.get(JSON_KEY_CATEGORY);
		String name = (String) root.get(JSON_KEY_NAME);
		Long id = null;
		if(parentId==null) {
			Bookcategory category = new Bookcategory();
			category.setCategorylevel(level);
			category.setCategoryname("pdf books");
			id = this.service.getCategoryService().saveBookcategory(category);
		}
		else {
			Bookcategory category = new Bookcategory();
			category.setCategoryname(name);
			category.setCategoryparentid(parentId);
			category.setCategorylevel(level);
			category.setCategoryorder(order);
			id = this.service.getCategoryService().saveBookcategory(category);
		}
		if(categories!=null && categories.size() > 0) {
			int nLength = categories.size();
			for(int i = 0; i < nLength; i++) {
				Map map = (Map) categories.get(i);
				parseCategories(map, id, level + 1, i + 1);
			}
		}
	}
}
