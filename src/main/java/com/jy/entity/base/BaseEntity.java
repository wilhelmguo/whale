package com.jy.entity.base;

import java.io.Serializable;
/**
 * 实体类基础表
 */
public class BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected Integer isValid;

	protected String keyWord;

	protected String company;

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
}
