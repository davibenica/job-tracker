package com.jobtracker.jobtrackerbackend.reposotory;

import com.jobtracker.jobtrackerbackend.models.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface jobRepository extends JpaRepository<Jobs, Integer>
{
    List<Jobs> findByUserId(Integer userId);
}
