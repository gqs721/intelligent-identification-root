package com.java.model.dao.quartz;

import com.java.model.domain.quartz.JobAndTrigger;

import java.util.List;

public interface JobAndTriggerMapper {
	public List<JobAndTrigger> getJobAndTriggerDetails();
}
