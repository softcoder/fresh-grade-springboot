/*
 * Demo Project for Fresh Grade Rest API test
 * By: Mark Vejvoda
 */

package com.freshgrade.studentmanager.model;

import org.springframework.http.MediaType;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * This class represents a Student model in this application
 * @author softcoder
 *
 */
public class Student {

	@JsonProperty(required = true)
    @ApiModelProperty(notes = "The student id", required = true)
    private long id;
	@JsonProperty(required = true)
	@ApiModelProperty(notes = "The student first name", required = true)
    private String firstName;
	@JsonProperty(required = true)
	@ApiModelProperty(notes = "The student last name", required = true)
    private String lastName;
	@JsonProperty(required = true)
	@ApiModelProperty(notes = "The student photo", required = false)
    private byte[] photo;
	@JsonProperty(required = true)
	@ApiModelProperty(notes = "The media type of the student photo", required = false)
	private MediaType mediaType;

    public Student() {
    }
    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Copies the input students values into the current students values.
     * @param student
     */
    public void copyMutable(Student student) {
        this.firstName = student.firstName;
        this.lastName = student.lastName;
        this.photo = student.photo;
        this.mediaType = student.mediaType;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String name) {
        this.lastName = name;
    }

    public MediaType getMediaType() {
		return this.mediaType;
	}
	public byte[] getPhoto() {
		return this.photo;
	}

    public void setPhoto(byte[] data, MediaType mediaType) {
		this.mediaType = mediaType;
		this.photo = data;
	}
}
