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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.rambird.miles.model.Category;
import com.rambird.miles.model.Mile;
import com.rambird.miles.service.RambirdService;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
@SessionAttributes(types = Mile.class)
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
        Mile mile = new Mile();
        model.addAttribute(mile);
        return "miles/addOrUpdateMile";
    }

    @RequestMapping(value = "/mile/new", method = RequestMethod.POST)
    public String processCreationForm(@Valid Mile mile, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "miles/addOrUpdateMile";
        } else {
            this.rambirdService.saveMile(mile);
            status.setComplete();
            return "redirect:/miles/" + mile.getMileId();
        }
    }
    @RequestMapping(value = "/miles/mileList", method = RequestMethod.GET)
    public ModelAndView processFindForm( Model model) {
    	ModelAndView mav = new ModelAndView("miles/mileList");
    	// find all categories
        Collection<Mile> results = this.rambirdService.findAllMiles();
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
        Mile mile = this.rambirdService.findMileById(mileId);
        model.addAttribute(mile);
        return "miles/addOrUpdateMile";
    }

    @RequestMapping(value = "/miles/{mileId}/edit", method = RequestMethod.PUT)
    public String processUpdateOwnerForm(@Valid Mile mile, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "miles/addOrUpdateMile";
        } else {
            this.rambirdService.saveMile(mile);
            status.setComplete();
            return "redirect:/miles/{mileId}";
        }
    }   
   
}