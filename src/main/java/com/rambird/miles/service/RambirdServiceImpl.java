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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rambird.miles.model.Category;
import com.rambird.miles.model.Mile;
import com.rambird.miles.model.Owner;
import com.rambird.miles.model.Pet;
import com.rambird.miles.model.PetType;
import com.rambird.miles.model.User;
import com.rambird.miles.model.Vet;
import com.rambird.miles.model.Visit;
import com.rambird.miles.repository.MileRepository;
import com.rambird.miles.repository.MilesCatgRepository;
import com.rambird.miles.repository.OwnerRepository;
import com.rambird.miles.repository.PetRepository;
import com.rambird.miles.repository.UserRepository;
import com.rambird.miles.repository.VetRepository;
import com.rambird.miles.repository.VisitRepository;

/**
 * Mostly used as a facade for all rambird controllers
 * Also a placeholder for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class RambirdServiceImpl implements RambirdService {

    private PetRepository petRepository;
    private VetRepository vetRepository;
    private OwnerRepository ownerRepository;
    private VisitRepository visitRepository;
    private UserRepository userRepository;
    private MilesCatgRepository milesCatgRepository;
    private MileRepository mileRepository;

    @Autowired
    public RambirdServiceImpl(PetRepository petRepository, VetRepository vetRepository, OwnerRepository ownerRepository, VisitRepository visitRepository, 
    		UserRepository userRepository, MilesCatgRepository milesCatgRepository, MileRepository mileRepository) {
        this.petRepository = petRepository;
        this.vetRepository = vetRepository;
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
        this.userRepository = userRepository;
        this.milesCatgRepository = milesCatgRepository;
        this.mileRepository = mileRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public User authUser(String userName, String password) throws DataAccessException {
        return userRepository.authUser(userName, password);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<PetType> findPetTypes() throws DataAccessException {
        return petRepository.findPetTypes();
    }

    @Override
    @Transactional(readOnly = true)
    public Owner findOwnerById(int id) throws DataAccessException {
        return ownerRepository.findById(id);
    }
    

 

    @Override
    @Transactional(readOnly = true)
    public Collection<Owner> findOwnerByLastName(String lastName) throws DataAccessException {
        return ownerRepository.findByLastName(lastName);
    }

    @Override
    @Transactional
    public void saveOwner(Owner owner) throws DataAccessException {
        ownerRepository.save(owner);
    }

    @Override
    @Transactional
    public void saveCategory(Category category) throws DataAccessException {
        milesCatgRepository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Category findCategoryById(int id) throws DataAccessException {
        return milesCatgRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Category> findAllCategory()throws DataAccessException {
    	return milesCatgRepository.findAll();
    }

    @Override
    @Transactional
    public void saveMile(Mile mile) throws DataAccessException {
        mileRepository.save(mile);
    }

    @Override
    @Transactional(readOnly = true)
    public Mile findMileById(int id) throws DataAccessException {
        return mileRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Mile> findAllMiles()throws DataAccessException {
    	return mileRepository.findAll();
    }
    
    
    @Override
    @Transactional
    public void saveVisit(Visit visit) throws DataAccessException {
        visitRepository.save(visit);
    }


    @Override
    @Transactional(readOnly = true)
    public Pet findPetById(int id) throws DataAccessException {
        return petRepository.findById(id);
    }

    @Override
    @Transactional
    public void savePet(Pet pet) throws DataAccessException {
        petRepository.save(pet);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "vets")
    public Collection<Vet> findVets() throws DataAccessException {
        return vetRepository.findAll();
    }


}
