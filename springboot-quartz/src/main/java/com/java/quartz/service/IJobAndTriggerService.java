package com.java.quartz.service;


import com.github.pagehelper.PageInfo;
import com.java.model.domain.quartz.JobAndTrigger;

public interface IJobAndTriggerService {
	public PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize);
}
