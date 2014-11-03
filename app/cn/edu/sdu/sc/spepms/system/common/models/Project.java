package cn.edu.sdu.sc.spepms.system.common.models;

import javax.persistence.Entity;

@Entity
public class Project extends AuditableModel {

	private String name;
	
	private String words;

	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@org.hibernate.annotations.Type(type = "text")
	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
