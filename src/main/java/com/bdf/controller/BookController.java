package com.bdf.controller;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bdf.common.FileUtils;
import com.bdf.controller.base.BaseController;
import com.bdf.entity.Book;
import com.bdf.entity.mix.Page;
import com.bdf.service.BookService;
import com.bdf.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 */
@RestController
@RequestMapping({"/bookDataFinder/api/"})
@Slf4j
@RequiredArgsConstructor
public class BookController extends BaseController {
	
	@Autowired
    private BookService bookService;
	@Autowired
	private UserService userService;
	
	@GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks() {
		List<Book> book = this.bookService.list();
    	return new ResponseEntity<List<Book>>(book, HttpStatus.OK);
    }
	
	@GetMapping("/booksBycategory/{categoryId}")
	public ResponseEntity<List<Book>> getBooksByCategory(@PathVariable("categoryId") Long categoryId) {
		List<Book> bookList = this.bookService.listByCategory(categoryId);
		return new ResponseEntity<List<Book>>(bookList, HttpStatus.OK);
	}
	
	@GetMapping("/booksBycategoryPage")
	public ResponseEntity<Page> getBooksByCategoryPage(@RequestParam Map<String, String> requestParams) throws Exception {
		Page page = new Page();
		Long categoryId = Long.parseLong(requestParams.get("categoryid"));
		page.page = Integer.parseInt(requestParams.get("page"));
		page.rowsPerPage = Integer.parseInt(requestParams.get("rowsPerPage"));
		if(categoryId==null) {
			throw new Exception("category must not be null");
		}
		Page bookList = this.bookService.listByCategoryPage(categoryId, page);
		return new ResponseEntity<Page>(bookList, HttpStatus.OK);
	}
	
	@GetMapping("/booksBytitle")
	public ResponseEntity<List<Book>> getBooksByTitle(@RequestParam Map<String, String> searchParams) {
		String strTitle = searchParams.get("title");
		List<Book> bookList = this.bookService.searchByTitle(strTitle);
		return new ResponseEntity<List<Book>>(bookList, HttpStatus.OK);
	}
	
	@GetMapping("/download")
	public ResponseEntity<Resource> download(@RequestParam Map<String, String> requestParams) throws Exception {
		long bookId = Long.parseLong(requestParams.get("bookId"));
		long userId = Long.parseLong(requestParams.get("userId"));
		if(userService.isAllowDownload(userId)) {
			File file = FileUtils.getPdfFilePath(bookId);
//			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			Path path = Paths.get(file.getPath());			
			byte[] data = Files.readAllBytes(path);
	        ByteArrayResource resource = new ByteArrayResource(data);
			HttpHeaders headers = new HttpHeaders();
			
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			headers.add("Access-Control-Allow-Origin", "*");
			headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
			headers.add("Access-Control-Allow-Headers", "Content-Type");
			headers.add("Content-Disposition", "filename=" + "data.pdf");
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			
			return ResponseEntity.ok()
					.headers(headers)
					.contentLength(file.length())
					.body(resource);
		}
		else {
			throw new Exception("You have to pay to download pdf files");
		}
	}
	
    @GetMapping(value = "/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
    	Book book = this.bookService.findById(id);
    	if(book == null){
    		return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
    	}else{
    		return new ResponseEntity<Book>(book, HttpStatus.OK);
    	}    	
    }
    
    @PostMapping(value = "/book")
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
    	book.setId(null);
    	this.bookService.saveBook(book);
		return new ResponseEntity<Book>(book, HttpStatus.CREATED);
	}
    
    @PutMapping(value = "/book/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
    	book.setId(id);
    	this.bookService.updateBook(book);
		return new ResponseEntity<Book>(book, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/book/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
		this.bookService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(value = "/bookupload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) throws Exception {
		if(id!=null && id > 0) {
			this.bookService.saveBookToFile(file, id);
			return "success";
		}
		else {
			return "failed";
		}
	}
}