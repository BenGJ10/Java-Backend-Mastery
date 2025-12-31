package com.bengregory.JobApp.repository;

import com.bengregory.JobApp.model.JobPost;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JobRepository {

    private List<JobPost> jobs = new ArrayList<>();

    public void addJob(JobPost job){
        jobs.add(job);
    }

    public List<JobPost> viewJobs(){
        return jobs;
    }

}
