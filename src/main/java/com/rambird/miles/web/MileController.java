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
package com.rambird.miles.web;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import com.rambird.miles.model.Category;
import com.rambird.miles.model.MyMile;
import com.rambird.miles.model.SearchMiles;
import com.rambird.miles.service.RambirdService;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
@SessionAttributes(types = MyMile.class)
public class MileController {

    private final RambirdService rambirdService;


    @Autowired
    public MileController(RambirdService rambirdService) {
        this.rambirdService = rambirdService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/miles/new", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
    	Collection<Category> categories = this.rambirdService.findAllCategory();
		model.addAttribute("categories", categories);
    	MyMile myMile = new MyMile();
    	// Default priority
    	myMile.setPriority("3UI");
    	// Default category
    	myMile.setCatg(categories.iterator().next().getCatg());
        model.addAttribute(myMile);
        return "miles/addOrUpdateMile";
    }

    @RequestMapping(value = "/miles/new", method = RequestMethod.POST)
    public @ResponseBody String processCreationForm(@Valid MyMile myMile, BindingResult result, SessionStatus status ) {
        if (result.hasErrors()) {
            return "error";
        } else {
            this.rambirdService.saveMile(myMile);
            status.setComplete();
            return "success";
        }
    }
    @RequestMapping(value = "/miles/mileList", method = RequestMethod.GET)
    public ModelAndView processDefaultList(Model model) {
    	SearchMiles searchMiles = new SearchMiles();
    	model.addAttribute("searchMiles", searchMiles);
    	ModelAndView mav = new ModelAndView("miles/mileList");
    	// find all categories
        Collection<MyMile> results = this.rambirdService.findAllMiles(searchMiles);
        if (results.size() > 1) {
            // multiple owners found
        	mav.addObject("selections", results);
        }
        Collection<Category> categories = this.rambirdService.findAllCategory();
		if (categories.isEmpty()){
			Category c = new Category();
			c.setCatg("Sumon");
			c.setCatgLabel("SUMON");
			categories.add(c);
		}
        model.addAttribute("categories", categories);
    	
		MyMile myMile = new MyMile();
    	// Default priority
    	myMile.setPriority("3UI");
    	// Default category
    	
    	myMile.setCatg(categories.iterator().next().getCatg());
        model.addAttribute(myMile);
        return mav;

    }
    @RequestMapping(value = "/miles/find", method = RequestMethod.GET)
    public ModelAndView processFindForm(SearchMiles searchMiles, Model model) {
    	ModelAndView mav = new ModelAndView("miles/mileList");
    	// find all categories
        Collection<MyMile> results = this.rambirdService.findAllMiles(searchMiles);
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
    @RequestMapping("/miles/{mileId}")
    public ModelAndView showCatg(@PathVariable("mileId") int mileId) {
        ModelAndView mav = new ModelAndView("miles/mileDetails");
        mav.addObject(this.rambirdService.findMileById(mileId));
        return mav;
    }

    @RequestMapping(value = "/miles/{mileId}/edit", method = RequestMethod.GET)
    public String initUpdateOwnerForm(@PathVariable("mileId") int mileId, Model model) {
        MyMile myMile = this.rambirdService.findMileById(mileId);
        model.addAttribute(myMile);
     	Collection<Category> categories = this.rambirdService.findAllCategory();
    	model.addAttribute("categories", categories);
        
        return "miles/addOrUpdateMile";
    }

    @RequestMapping(value = "/miles/{mileId}/edit", method = RequestMethod.PUT)
    public String processUpdateOwnerForm(@Valid MyMile myMile, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "miles/addOrUpdateMile";
        } else {
            this.rambirdService.saveMile(myMile);
            status.setComplete();
            return "redirect:/miles/{mileId}";
        }
    }   
    
    @RequestMapping(value = "/miles/ajaxsave", method = RequestMethod.POST)
    public @ResponseBody
    String saveMileOnAjax(@Valid MyMile myMile, BindingResult result) throws NoSuchRequestHandlingMethodException{
    	if (result.hasErrors()) {
            throw new NoSuchRequestHandlingMethodException("Missing data", this.getClass());
        } else {
            this.rambirdService.saveMile(myMile);
            return "success";
        }
    }

   
}