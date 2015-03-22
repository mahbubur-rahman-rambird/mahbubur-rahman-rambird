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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.rambird.model.Category;
import com.rambird.model.Owner;
import com.rambird.service.RambirdService;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
@SessionAttributes(types = Owner.class)
public class CatgController {

    private final RambirdService rambirdService;


    @Autowired
    public CatgController(RambirdService rambirdService) {
        this.rambirdService = rambirdService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/categories/new", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
        Category category = new Category();
        model.addAttribute(category);
        return "categories/addOrUpdateCatg";
    }

    @RequestMapping(value = "/categories/new", method = RequestMethod.POST)
    public String processCreationForm(@Valid Category category, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "categories/addOrUpdateCatg";
        } else {
            this.rambirdService.saveCategory(category);
            status.setComplete();
            return "redirect:/categories/" + category.getCatgid();
        }
    }
    @RequestMapping(value = "/categories/catgList", method = RequestMethod.GET)
    public ModelAndView processFindForm( Model model) {
    	ModelAndView mav = new ModelAndView("categories/catgList");
    	// find all categories
        Collection<Category> results = this.rambirdService.findAllCategory();
        if (results.size() > 1) {
            // multiple owners found
        	mav.addObject("selections", results);
        }
        return mav;

    }

    /**
     * Custom handler for displaying an owner.
     *
     * @param ownerId the ID of the owner to display
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping("/categories/{catgid}")
    public ModelAndView showCatg(@PathVariable("catgid") int catgid) {
        ModelAndView mav = new ModelAndView("categories/catgDetails");
        mav.addObject(this.rambirdService.findCategoryById(catgid));
        return mav;
    }

    @RequestMapping(value = "/categories/{catgid}/edit", method = RequestMethod.GET)
    public String initUpdateOwnerForm(@PathVariable("catgid") int catgid, Model model) {
        Category category = this.rambirdService.findCategoryById(catgid);
        model.addAttribute(category);
        return "categories/addOrUpdateCatg";
    }

    @RequestMapping(value = "/categories/{catgid}/edit", method = RequestMethod.PUT)
    public String processUpdateOwnerForm(@Valid Category category, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "categories/addOrUpdateCatg";
        } else {
            this.rambirdService.saveCategory(category);;
            status.setComplete();
            return "redirect:/categories/{catgid}";
        }
    }   
   
}