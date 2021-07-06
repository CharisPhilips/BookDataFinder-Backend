package com.bdf.service;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bdf.common.FileUtils;
import com.bdf.dao.BookDAO;
import com.bdf.entity.Book;
import com.bdf.entity.mix.Page;

/**
 * Book service.
 * 
 */
@Transactional
@Service("bookService")
public class BookService {

	@Autowired
	private BookDAO dao;

	public List<Book> list() {
		return dao.list();
	}    

	public List<Book> listByCategory(long categoryId) {
		return dao.listByCategory(categoryId);
	}

	public Page listByCategoryPage(long categoryId, Page page) {
		return dao.listByCategoryPage(categoryId, page);
	}
	
	public List<Book> searchByTitle(String strTitle) {
		return dao.searchByTitle(strTitle);
	}

	public Book findById(Long id) {
		return dao.findById(id);
	}

	public Boolean saveBook(Book book) {
		Boolean result = dao.save(book);
		return result;
	}

	public Boolean updateBook(Book book) {
		Book entity = dao.findById(book.getId());
		if(entity != null){
			entity.setBookname(book.getBookname());
			entity.setCategoryid(book.getCategoryid());
//			entity.setMeta1(book.getMeta1());
//			entity.setMeta2(book.getMeta2());
//			entity.setMeta3(book.getMeta3());
//			entity.setMeta4(book.getMeta4());
//			entity.setMeta5(book.getMeta5());
//			entity.setMeta6(book.getMeta6());
//			entity.setMeta7(book.getMeta7());
//			entity.setMeta8(book.getMeta8());
//			entity.setMeta9(book.getMeta9());
//			entity.setMeta10(book.getMeta10());
			dao.update(entity);
			return true;
		}else{
			return false;
		}
	}

	public Boolean deleteById(Long id) {
		Book entity = dao.findById(id);
		if(entity != null){
			dao.delete(entity);
			return true;
		}else{
			return false;
		}
	}


	public boolean saveBookToFile(MultipartFile file, long dbId) throws Exception{
		File filePdf = FileUtils.getPdfFilePath(dbId);
		try {
			InputStream inputStream = file.getInputStream();
			Files.copy(inputStream, Paths.get(filePdf.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
			return true;
		}
		catch (Exception e) {
			throw e;
		}
	}

}