package com.bdf.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Employee Entity.
 * 
 * @author ali.cavac
 *
 */
@Entity
@Table(name = "tb_bookcategory")
//@JsonIdentityInfo(
//		  generator = ObjectIdGenerators.PropertyGenerator.class, 
//		  property = "id", scope = Employee.class)

public class Bookcategory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="category_pid", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;
	
	@NotEmpty
    @Column(name="categoryname", nullable=false)
	private String categoryname;
	
	
    @Column(name="categorylevel", nullable=true)
    private Integer categorylevel;
	
    @Column(name="categoryparentid", nullable=true)
    private Long categoryparentid;
	
    @Column(name="categoryorder", nullable=true)
    private Integer categoryorder;
	
    @Transient
    private List<Bookcategory> category = new ArrayList<Bookcategory>(); 
	
    public Bookcategory() {}
    
	public Bookcategory(Long categoryid, String categoryname, Integer categorylevel, Long categoryparentid, Integer categoryorder) {
		super();
		this.categoryId = categoryid;
		this.categoryname = categoryname;
		this.categorylevel = categorylevel;
		this.categoryparentid = categoryparentid;
		this.categoryorder = categoryorder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryId == null) ? 0 : categoryId.hashCode());
		result = prime * result + ((categoryname == null) ? 0 : categoryname.hashCode());
		result = prime * result + ((categorylevel == null) ? 0 : categorylevel.hashCode());
		result = prime * result + ((categoryparentid == null) ? 0 : categoryparentid.hashCode());
		result = prime * result + ((categoryorder == null) ? 0 : categoryorder.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}
	
	public Long getId() {
		return categoryId;
	}

	public void setId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public Integer getCategorylevel() {
		return categorylevel;
	}

	public void setCategorylevel(Integer categorylevel) {
		this.categorylevel = categorylevel;
	}

	public Long getCategoryparentid() {
		return categoryparentid;
	}

	public void setCategoryparentid(Long categoryparentid) {
		this.categoryparentid = categoryparentid;
	}

	public Integer getCategoryorder() {
		return categoryorder;
	}

	public void setCategoryorder(Integer categoryorder) {
		this.categoryorder = categoryorder;
	}
	
	public List<Bookcategory> getCategory() { return category; }
	
	public void addCategory(Bookcategory category) {
		this.category.add(category);
	}

}