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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.core.style.ToStringCreator;

/**
 * Simple JavaBean domain object representing an owner.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
@Entity
@Table(name = "category")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "catgid")
	protected Integer catgid;

	@Column(name = "catg")
    @NotEmpty
    private String catg;

    @Column(name = "catg_label")
    @NotEmpty
    private String catgLabel;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "home")
    private String home;

    @Column(name = "catg_rank")
    @NotNull
    private Integer catgRank;

   





	public Integer getCatgid() {
		return catgid;
	}



	public void setCatgid(Integer catgid) {
		this.catgid = catgid;
	}



	public String getCatg() {
		return catg;
	}



	public void setCatg(String catg) {
		this.catg = catg;
	}



	public String getCatgLabel() {
		return catgLabel;
	}



	public void setCatgLabel(String catgLabel) {
		this.catgLabel = catgLabel;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}


    public boolean isNew() {
        return (this.catgid == null);
    }


	public String getHome() {
		return home;
	}



	public void setHome(String home) {
		this.home = home;
	}



	public Integer getCatgRank() {
		return catgRank;
	}



	public void setCatgRank(Integer catgRank) {
		this.catgRank = catgRank;
	}



	@Override
    public String toString() {
        return new ToStringCreator(this)

                .append("catg", this.getCatg())
                .append("Catg label", this.getCatgLabel())
                .toString();
    }
}
