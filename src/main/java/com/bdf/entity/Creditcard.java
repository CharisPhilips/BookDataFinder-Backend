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
@Table(name = "tb_creditcard")
public class Creditcard implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="card_pid", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cardId;
	
	@NotEmpty
    @Column(name="cardno", nullable=false)
    private String cardno;
	
    @Column(name="expmonth", nullable=false)
    private Integer expmonth;
	
    @Column(name="expyear", nullable=false)
    private Integer expyear;
	
	@NotEmpty
    @Column(name="cardcvc", nullable=false)
    private String cardcvc;
	
    @Column(name="userid", nullable=false)
    private Long userid;
	
    public Creditcard() {}
    
	public Creditcard(Long cardId, String username, String cardno, Integer expmonth, Integer expyear, String cardcvc, Long userid) {
		super();
		this.cardId = cardId;
		this.cardno = cardno;
		this.expmonth = expmonth;
		this.expyear = expyear;
		this.cardcvc = cardcvc;
		this.userid = userid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardId == null) ? 0 : cardId.hashCode());
		result = prime * result + ((cardno == null) ? 0 : cardno.hashCode());
		result = prime * result + ((expmonth == null) ? 0 : expmonth.hashCode());
		result = prime * result + ((expyear == null) ? 0 : expyear.hashCode());
		result = prime * result + ((cardcvc == null) ? 0 : cardcvc.hashCode());
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Creditcard other = (Creditcard) obj;
		if (this.cardId == null) {
			if (other.cardId != null) {
				return false;
			}
		} else if (!this.cardId.equals(other.cardId)) {
			return false;
		}
		return true;
	}
	
	public Long getId() {
		return cardId;
	}

	public void setId(Long cardId) {
		this.cardId = cardId;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public Integer getExpmonth() {
		return expmonth;
	}

	public void setExpmonth(Integer expmonth) {
		this.expmonth = expmonth;
	}

	public Integer getExpyear() {
		return expyear;
	}

	public void setExpyear(Integer expyear) {
		this.expyear = expyear;
	}

	public String getCardcvc() {
		return cardcvc;
	}

	public void setCardcvc(String cardcvc) {
		this.cardcvc = cardcvc;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

}