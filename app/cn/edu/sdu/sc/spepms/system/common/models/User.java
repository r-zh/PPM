package cn.edu.sdu.sc.spepms.system.common.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class User extends AuditableModel {

    private String name;

    private String gender;

    private String personalId;

    private String birthday;

    private String hometown;

    private String password;

    //存入
    private Integer give;

    //取出
    private Integer receive;

    //总金额
    private Integer all;

    private boolean admin;

	public void setGive(Integer give) {
		this.give = give;
	}

	public void setReceive(Integer receive) {
		this.receive = receive;
	}

	public void setAll(Integer all) {
		this.all = all;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGive() {
		return give;
	}

	public Integer getReceive() {
		return receive;
	}

	public Integer getAll() {
		return all;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}
