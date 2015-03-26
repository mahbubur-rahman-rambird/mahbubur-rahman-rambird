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
package com.rambird.miles.service;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.rambird.miles.model.Category;
import com.rambird.miles.model.MyMile;
import com.rambird.miles.model.Owner;
import com.rambird.miles.model.Pet;
import com.rambird.miles.model.PetType;
import com.rambird.miles.model.User;
import com.rambird.miles.model.Vet;
import com.rambird.miles.model.Visit;


/**
 * Mostly used as a facade for all Petclinic controllers
 *
 * @author Michael Isvy
 */
public interface RambirdService {

    public Collection<PetType> findPetTypes() throws DataAccessException;

    public Owner findOwnerById(int id) throws DataAccessException;

    public Pet findPetById(int id) throws DataAccessException;

    public void savePet(Pet pet) throws DataAccessException;

    public void saveVisit(Visit visit) throws DataAccessException;

    public Collection<Vet> findVets() throws DataAccessException;

    public void saveOwner(Owner owner) throws DataAccessException;

    public void saveCategory(Category category) throws DataAccessException;

    public Category findCategoryById(int id) throws DataAccessException;
    
    public Collection<Category> findAllCategory() throws DataAccessException;
    
    public void saveMile(MyMile mile) throws DataAccessException;

    public MyMile findMileById(int id) throws DataAccessException;
    
    public Collection<MyMile> findAllMiles() throws DataAccessException;
    
    public Collection<Owner> findOwnerByLastName(String lastName) throws DataAccessException;
    
    public User authUser(String userName, String password) throws DataAccessException;

}
