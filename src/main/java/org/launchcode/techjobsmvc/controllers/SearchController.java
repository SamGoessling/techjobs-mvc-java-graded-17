package org.launchcode.techjobsmvc.controllers;

//-- necessary imports --//
import org.launchcode.techjobsmvc.models.Job;
import org.launchcode.techjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import static org.launchcode.techjobsmvc.controllers.ListController.columnChoices;

//-- indicates that this class is a Spring MVC controller --//
@Controller

//-- maps all requests starting with "search" to methods in this class --//
@RequestMapping("search")
public class SearchController {

    //-- method to display the initial search form --//
    @GetMapping(value = "")
    public String search(Model model) {

        //-- adds "columnChoices" to the model to populate search options --//
        model.addAttribute("columns", columnChoices);

        //-- returns the "search" view (search.html) --//
        return "search";
    }

    //-- method to process the search form submission and display results --//
    @PostMapping(value = "results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {

        //-- list to store search results --//
        ArrayList<Job> jobs;

        //-- checks if the searchTerm is "all" or empty to determine search mode --//
        if (searchTerm.toLowerCase().equals("all") || searchTerm.trim().isEmpty()) {

            //-- fetches all jobs if searchTerm is "all" or empty --//
            jobs = JobData.findAll();
        } else {

            //-- fetches jobs based on searchType and searchTerm --//
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
        }

        //-- adds "columnChoices" to the model for search options --//
        model.addAttribute("columns", columnChoices);

        //-- adds the search results to the model --//
        model.addAttribute("jobs", jobs);

        //-- returns the "search" view with updated job listings --//
        return "search";
    }
}
