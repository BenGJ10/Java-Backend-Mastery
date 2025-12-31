package com.bengregory.JobApp.service;

import com.bengregory.JobApp.model.JobPost;
import com.bengregory.JobApp.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository repository;

    public void addJob(JobPost jobPost){
        repository.addJob(jobPost);
    }

    public List<JobPost> viewJobs(){
        return repository.viewJobs();
    }
}
