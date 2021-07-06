package com.bdf.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Employee Entity.
 * 
 * @author ali.cavac
 *
 */
@Entity
@Table(name = "tb_bookmeta")
//@JsonIdentityInfo(
//		  generator = ObjectIdGenerators.PropertyGenerator.class, 
//		  property = "id", scope = Employee.class)

public class Bookmeta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="meta_pid", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long metaId;
	
	@NotEmpty
    @Column(name="metaname", nullable=false)
	private String metaname;
	
    public Bookmeta() {}
    
	public Bookmeta(Long metaId, String metaname) {
		super();
		this.metaId = metaId;
		this.metaname = metaname;
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((metaId == null) ? 0 : metaId.hashCode());
		result = prime * result + ((metaname == null) ? 0 : metaname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return (this == obj);
	}

	public Long getId() {
		return metaId;
	}

	public void setId(Long metaId) {
		this.metaId = metaId;
	}

	public String getMetaname() {
		return metaname;
	}

	public void setMetaname(String metaname) {
		this.metaname = metaname;
	}

}