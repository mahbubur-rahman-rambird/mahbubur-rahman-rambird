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
package com.rambird.miles.repository.jdbc;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.rambird.miles.model.Category;
import com.rambird.miles.model.Owner;
import com.rambird.miles.model.Pet;
import com.rambird.miles.model.Visit;
import com.rambird.miles.repository.MilesCatgRepository;
import com.rambird.miles.repository.OwnerRepository;

/**
 * A simple JDBC-based implementation of the {@link OwnerRepository} interface.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @author Sam Brannen
 * @author Thomas Risberg
 * @author Mark Fisher
 */
@Repository
public class JdbcCatgRepositoryImpl implements MilesCatgRepository {


    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertMileCatg;

    @Autowired
    public JdbcCatgRepositoryImpl(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {

        this.insertMileCatg = new SimpleJdbcInsert(dataSource)
                .withTableName("category")
                .usingGeneratedKeyColumns("catgid");

        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    /**
     * Loads the {@link Owner} with the supplied <code>id</code>; also loads the {@link Pet Pets} and {@link Visit Visits}
     * for the corresponding owner, if not already loaded.
     */
    @Override
    public Category findById(int id) throws DataAccessException {
        Category category;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", id);
            category = this.namedParameterJdbcTemplate.queryForObject(
                    "SELECT catgid, catg, catg_label, user_name, home, catg_rank FROM category WHERE catgid= :id",
                    params,
                    ParameterizedBeanPropertyRowMapper.newInstance(Category.class)
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new ObjectRetrievalFailureException(Category.class, id);
        }
        return category;
    }
    /**
     * Loads {@link Owner Owners} from the data store by last name, returning all owners whose last name <i>starts</i> with
     * the given name; also loads the {@link Pet Pets} and {@link Visit Visits} for the corresponding owners, if not
     * already loaded.
     */
    @Override
    public Collection<Category> findAll() throws DataAccessException {
    	Map<String, Object> params = new HashMap<String, Object>();
    	List<Category> categories = this.namedParameterJdbcTemplate.query(
                "SELECT catgid, catg, catg_label, user_name, home, catg_rank FROM category order by catgid desc",
                params,
                ParameterizedBeanPropertyRowMapper.newInstance(Category.class)
        );
        
        return categories;
    }


    @Override
    public void save(Category category) throws DataAccessException {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(category);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        category.setUserName(auth.getName()); //get logged in username
   
        if (category.isNew()) {
            Number newKey = this.insertMileCatg.executeAndReturnKey(parameterSource);
            category.setCatgid(newKey.intValue());
        } else {
            this.namedParameterJdbcTemplate.update(
                    "UPDATE category SET catg=:catg, catg_label=:catgLabel, home=:home WHERE catgid=:catgid",
                    parameterSource);
        }
    }
}
