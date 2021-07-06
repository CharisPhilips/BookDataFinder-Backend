package com.bdf.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.bdf.common.DateUtils;

/**
 * Employee Entity.
 * 
 * @author ali.cavac
 *
 */
@Entity
@Table(name = "tb_user")
//@JsonIdentityInfo(
//		  generator = ObjectIdGenerators.PropertyGenerator.class, 
//		  property = "id", scope = Employee.class)

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_pid", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uesrId;
	
	@NotEmpty
    @Column(name="email", nullable=false)
	private String email;
	
	@NotEmpty
    @Column(name="password", nullable=false)
    private String password;
	
    @Column(name="status", nullable=true)
	private Long status; //null, 0=no active, 1: active(basic), 2: pay, 3: full pay 
	
    @Column(name="service_date", nullable=true)
    private Date servicedate; //null, 0=no active, 1: active(basic), 2: pay, 3: full pay
    
	public User() {}
    
	public User(Long userid, String name, String email, String password) {
		super();
		this.uesrId = userid;
		this.email = name;
		this.password = password;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uesrId == null) ? 0 : uesrId.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		User other = (User) obj;
		if (this.email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!this.email.equals(other.email))
			return false;
		return true;
	}
	
	public Long getId() { return uesrId; }
	public void setId(Long uesrId) { this.uesrId = uesrId; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	
    public Long getStatus() { return status; }
	public void setStatus(Long status) { this.status = status; }

	public Date getServicedate() { return servicedate; }
	public void setServicedate(Date servicedate) { this.servicedate = servicedate; }
	
	public String getServicedateStr() {
		Date dtNow = new Date();
		if(!isAllowService()) {
			return "Your service date is expired";
		}
		int nServiceDays = DateUtils.getDiffDays(dtNow, this.servicedate);
		return String.format("You can download pdf books in %d days", nServiceDays);
	}
	
	public boolean isAllowService() {
		Date dtNow = new Date();
		if(this.servicedate!=null && this.servicedate.after(dtNow)) {
			return true;
		}
		return false;
	}

}