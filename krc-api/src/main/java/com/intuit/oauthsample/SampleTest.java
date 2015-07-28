package com.intuit.oauthsample;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
 
public class SampleTest {
	public static void main( String[] args ) throws Exception
    {
		
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		 
    	scheduler.start();
 
		for( int i =0; i< 3; i++){
		
	JobKey jobKeyA = new JobKey("jobA"+i, "group1");
	   JobDataMap newJobDataMap=new JobDataMap();
	   newJobDataMap.put("companyId", "1300129");
    	JobDetail jobA = JobBuilder.newJob(JobA.class).setJobData(newJobDataMap).withIdentity(jobKeyA).build();
 
    	/*JobKey jobKeyB = new JobKey("jobB", "group1");
    	JobDetail jobB = JobBuilder.newJob(JobA.class)
		.withIdentity(jobKeyB).build();
 
    	JobKey jobKeyC = new JobKey("jobC", "group1");
    	JobDetail jobC = JobBuilder.newJob(JobA.class)
		.withIdentity(jobKeyC).build();
 */
    	
    
 
    	Trigger trigger1 = TriggerBuilder
		.newTrigger()
		.withIdentity("dummyTriggerName1"+i, "group1").withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5)).build();
		
 
    	/*Trigger trigger2 = TriggerBuilder
		.newTrigger()
		.withIdentity("dummyTriggerName2", "group1")
		.withSchedule(
			CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
		.build();
 
    	Trigger trigger3 = TriggerBuilder
		.newTrigger()
		.withIdentity("dummyTriggerName3", "group1")
		.withSchedule(
			CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
		.build();*/
    	
    	scheduler.scheduleJob(jobA, trigger1);
    	
		}
 
    
    	/*scheduler.scheduleJob(jobA, trigger1);
    	scheduler.scheduleJob(jobB, trigger2);
    	scheduler.scheduleJob(jobC, trigger3);*/
 
    }
}

