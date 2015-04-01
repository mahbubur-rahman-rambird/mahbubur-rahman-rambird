/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rambird.miles.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.core.style.ToStringCreator;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Simple JavaBean domain object representing an owner.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
@Entity
@Table(name = "mymiles")
public class MyMile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "mileid")
	protected Integer mileId;

	@Column(name = "catg")
	@NotEmpty
	private String catg;

    @Column(name = "milestone")
    @NotEmpty
    private String milestone;

    @Column(name = "comments")
    private String comments;

    @Column(name = "priority")
    @NotEmpty
    private String priority;
    
    
    @Column(name = "finish_by")
    @DateTimeFormat (pattern="dd-MMM-YYYY HH:mm")
    private Date finishBy;
    
    
    @Column(name = "user_name")
    private String userName;








	public Integer getMileId() {
		return mileId;
	}







	public void setMileId(Integer mileId) {
		this.mileId = mileId;
	}







	public String getCatg() {
		return catg;
	}







	public void setCatg(String catg) {
		this.catg = catg;
	}







	public String getMilestone() {
		return milestone;
	}







	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}







	public String getUserName() {
		return userName;
	}







	public void setUserName(String userName) {
		this.userName = userName;
	}












	public String getComments() {
		return comments;
	}







	public void setComments(String comments) {
		this.comments = comments;
	}







	public String getPriority() {
		return priority;
	}







	public void setPriority(String priority) {
		this.priority = priority;
	}


	public Date getFinishBy() {
		return finishBy;
	}

	public void setFinishBy(Date finishBy) {
		this.finishBy = finishBy;
	}

	public boolean isNew() {
        return (this.mileId == null);
    }
	

	@Override
    public String toString() {
        return new ToStringCreator(this)

			    .append("catg", this.getCatg())
			    .append("milestone", this.getMilestone())
			    .append("comments", this.getComments())
                .toString();
    }
}
