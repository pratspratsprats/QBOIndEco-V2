package com.intuit.oauthsample;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class JobA implements Job{

	
	
	@Override
	public void execute(JobExecutionContext context)
		throws JobExecutionException {
		
		String companyId=(String) context.getMergedJobDataMap().get("companyId");
		System.out.println("companyId-=========>"+companyId);
	}


	

}
