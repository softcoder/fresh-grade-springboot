/*
 * Demo Project for Fresh Grade Rest API test
 * By: Mark Vejvoda
 */

package com.freshgrade.studentmanager.model;

/**
 * Entity representing the result of requesting a random student
 * @author softcoder
 */
public class RandomStudentResult {
	
	private RandomStudentName name;
	private RandomStudentPicture picture;
	
	public RandomStudentResult() {
	}

	public RandomStudentName getName() {
		return name;
	}

	public void setName(RandomStudentName name) {
		this.name = name;
	}

	public RandomStudentPicture getPicture() {
		return picture;
	}

	public void setPicture(RandomStudentPicture picture) {
		this.picture = picture;
	}
	
	
}
