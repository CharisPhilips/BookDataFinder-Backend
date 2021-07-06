package com.bdf.entity;

import java.io.File;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.bdf.common.FileUtils;

/**
 * Employee Entity.
 * 
 * @author ali.cavac
 *
 */
@Entity
@Table(name = "tb_book")
//@JsonIdentityInfo(
//		  generator = ObjectIdGenerators.PropertyGenerator.class, 
//		  property = "id", scope = Employee.class)

public class Book implements Serializable {

	@Id
	@Column(name="book_pid", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookId;
	
	@NotEmpty
    @Column(name="bookname", nullable=false)
	private String bookname;
	
    @Column(name="categoryid", nullable=false)
    private Long categoryid;
	
    
//    @Column(name="meta1", nullable=true)
//    private Long meta1;
//	
//    @Column(name="meta2", nullable=true)
//    private Long meta2;
//	
//    @Column(name="meta3", nullable=true)
//    private Long meta3;
//	
//    @Column(name="meta4", nullable=true)
//    private Long meta4;
//	
//    @Column(name="meta5", nullable=true)
//    private Long meta5;
//	
//    @Column(name="meta6", nullable=true)
//    private Long meta6;
//	
//    @Column(name="meta7", nullable=true)
//    private Long meta7;
//	
//    @Column(name="meta8", nullable=true)
//    private Long meta8;
//	
//    @Column(name="meta9", nullable=true)
//    private Long meta9;
//	
//    @Column(name="meta10", nullable=true)
//    private Long meta10;
	
    public Book() {}
    
    //public Book(Long bookid, String bookname, Long categoryid, Integer expmonth, Long meta1, Long meta2, Long meta3, Long meta4, Long meta5, Long meta6, Long meta7, Long meta8, Long meta9, Long meta10) {
    public Book(Long bookid, String bookname, Long categoryid) {
		super();
		this.bookId = bookid;
		this.bookname = bookname;
		this.categoryid = categoryid;
//		this.meta1 = meta1;
//		this.meta2 = meta2;
//		this.meta3 = meta3;
//		this.meta4 = meta4;
//		this.meta5 = meta5;
//		this.meta6 = meta6;
//		this.meta7 = meta7;
//		this.meta8 = meta8;
//		this.meta9 = meta9;
//		this.meta10 = meta10;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
		result = prime * result + ((bookname == null) ? 0 : bookname.hashCode());
		result = prime * result + ((categoryid == null) ? 0 : categoryid.hashCode());
//		result = prime * result + ((meta1 == null) ? 0 : meta1.hashCode());
//		result = prime * result + ((meta2 == null) ? 0 : meta2.hashCode());
//		result = prime * result + ((meta3 == null) ? 0 : meta3.hashCode());
//		result = prime * result + ((meta4 == null) ? 0 : meta4.hashCode());
//		result = prime * result + ((meta5 == null) ? 0 : meta5.hashCode());
//		result = prime * result + ((meta6 == null) ? 0 : meta6.hashCode());
//		result = prime * result + ((meta7 == null) ? 0 : meta7.hashCode());
//		result = prime * result + ((meta8 == null) ? 0 : meta8.hashCode());
//		result = prime * result + ((meta9 == null) ? 0 : meta9.hashCode());
//		result = prime * result + ((meta10 == null) ? 0 : meta10.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return (this == obj);
	}
	

	public Long getId() {
		return bookId;
	}

	public void setId(Long bookId) {
		this.bookId = bookId;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public Long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}

	public boolean isCanDownload() {
		return (FileUtils.getPdfFilePath(this.bookId)).exists();
	}
	
//	public Long getMeta1() {
//		return meta1;
//	}
//
//	public void setMeta1(Number meta1) { 
//		if(meta1!=null) {
//			this.meta1 = meta1.longValue();	
//		}
//	}
//
//	public Long getMeta2() {
//		return meta2;
//	}
//
//	public void setMeta2(Number meta2) {
//		if(meta2!=null) {
//			this.meta2 = meta2.longValue();
//		}
//	}
//
//	public Long getMeta3() {
//		return meta3;
//	}
//
//	public void setMeta3(Number meta3) {
//		if(meta3!=null) {
//			this.meta3 = meta3.longValue();
//		}
//	}
//
//	public Long getMeta4() {
//		return meta4;
//	}
//
//	public void setMeta4(Number meta4) {
//		if(meta4!=null) {
//			this.meta4 = meta4.longValue();
//		}
//	}
//
//	public Long getMeta5() {
//		return meta5;
//	}
//
//	public void setMeta5(Number meta5) {
//		if(meta5!=null) {
//			this.meta5 = meta5.longValue();
//		}
//	}
//
//	public Long getMeta6() {
//		return meta6;
//	}
//
//	public void setMeta6(Number meta6) {
//		if(meta6!=null) {
//			this.meta6 = meta6.longValue();
//		}
//	}
//
//	public Long getMeta7() {
//		return meta7;
//	}
//
//	public void setMeta7(Number meta7) {
//		if(meta7!=null) {
//			this.meta7 = meta7.longValue();
//		}
//	}
//
//	public Long getMeta8() {
//		return meta8;
//	}
//
//	public void setMeta8(Number meta8) {
//		if(meta8!=null) {
//			this.meta8 = meta8.longValue();
//		}
//	}
//	
//
//	public Long getMeta9() {
//		return meta9;
//	}
//
//	
//	public void setMeta9(Number meta9) {
//		if(meta9!=null) {
//			this.meta9 = meta9.longValue();
//		}
//	}
//
//	public Long getMeta10() {
//		return meta10;
//	}
//
//	public void setMeta10(Number meta10) {
//		if(meta10!=null) {
//			this.meta10 = meta10.longValue();
//		}
//	}

}