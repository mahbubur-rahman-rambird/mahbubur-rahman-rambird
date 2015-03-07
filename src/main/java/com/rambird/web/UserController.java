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
package com.rambird.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.rambird.model.Owner;
import com.rambird.model.User;
import com.rambird.service.RambirdService;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
@SessionAttributes(types = User.class)
public class UserController {

    private final RambirdService rambirdService;


    @Autowired
    public UserController(RambirdService rambirdService) {
        this.rambirdService = rambirdService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/users/signin", method = RequestMethod.GET)
    public String initFindForm(Model model) {
        model.addAttribute("user", new User());
        return "users/signin";
    }

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public String processFindForm(User user, BindingResult result, Model model) {

    	try{
	        // find owners by last name
	        user = this.rambirdService.authUser(user.getUserName(), user.getPassword());
        	return "welcome";
    	}catch(ObjectRetrievalFailureException ex){
            result.rejectValue("userName", "notFound", "not found");
            return "users/signin";
    		
    	}
    }

}
