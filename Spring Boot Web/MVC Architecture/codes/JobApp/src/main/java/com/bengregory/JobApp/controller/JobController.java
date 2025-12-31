package com.bengregory.JobApp.controller;

import com.bengregory.JobApp.model.JobPost;
import com.bengregory.JobApp.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class JobController {

    @Autowired
    private JobService service;

    @GetMapping({"/", "home"})
    public String home(){
        return "home.jsp";
    }

    @GetMapping("addjob")
    public String addJobs(){
        return "addjob.jsp";
    }

    @GetMapping("viewalljobs")
    public String viewAllJobs(Model model){
        List<JobPost> jobPosts = service.viewJobs();
        model.addAttribute("jobPosts", jobPosts);
        return "viewalljobs.jsp";
    }

    @PostMapping("handleForm")
    public String handleForm(JobPost jobPost){
        service.addJob(jobPost);
        return "success.jsp";
    }
}
