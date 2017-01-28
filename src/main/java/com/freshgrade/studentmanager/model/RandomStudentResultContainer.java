/*
 * Demo Project for Fresh Grade Rest API test
 * By: Mark Vejvoda
 */

package com.freshgrade.studentmanager.model;

import java.util.List;

/**
 * The container of the results for requesting a random Student
 * @author softcoder
 */
public class RandomStudentResultContainer {
	
	private List<RandomStudentResult> results;
	
	public RandomStudentResultContainer() {
	}

	public List<RandomStudentResult> getResults() {
		return results;
	}

	public void setResults(List<RandomStudentResult> results) {
		this.results = results;
	}
}
