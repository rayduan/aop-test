package com.geely.devops.aoptest.service;

import com.atlassian.jira.rest.client.api.*;
import com.atlassian.jira.rest.client.api.domain.*;
import com.atlassian.jira.rest.client.api.domain.input.*;
import com.geely.devops.aoptest.util.DateUtil;
import io.atlassian.util.concurrent.Promise;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @ProjectName: aop-test
 * @Package: com.geely.devops.aoptest.service
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2018/11/1 15:00
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2018
 */
@Service
public class JiraToolService {
    @Autowired
    private JiraRestClient jiraRestClient;
    // 日期转字符串短格式
    private SimpleDateFormat sdf_short=new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取所有项目
     */
    public void getAllProjects() {
        Promise<Iterable<BasicProject>> list = jiraRestClient.getProjectClient().getAllProjects();
        Iterable<BasicProject>          a    = null;
        try {
            a = list.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Iterator<BasicProject> it = a.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
    public void getProjectByKey(String projectKey) {
        ProjectRestClient projectClient = jiraRestClient.getProjectClient();
        Project projectInfo;
        try {
             projectInfo = projectClient.getProject(projectKey).claim();
            System.out.println(projectInfo);
        }catch (Exception e){
            System.out.println("获取项目失败");
        }


    }

    /**
     * 获取子任务信息
     * @param taskKey
     */
    public void getJiraInfo(String taskKey) {
        Promise<Issue> issue     = jiraRestClient.getIssueClient().getIssue(taskKey);
        Issue          issueInfo = null;
        try {
            issueInfo = issue.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(issueInfo.toString());

        System.out.println("*****************************************************");
        System.out.println(issueInfo.getSummary());
//        JSONObject  myJson = J
        System.out.println();
    }

    public void createjiraTask(){
        IssueRestClient issueClient = jiraRestClient.getIssueClient();
//        IssueType issueType = new IssueType();
        IssueInputBuilder builder = new IssueInputBuilder("IPD",
                11001L, "创建审计任务");
//        IssueInputBuilder builder = new IssueInputBuilder("IPD",
//                10600L, "创建不符合问题");
        builder.setDescription("issue description");
//
        Map<String, Object> times = new HashMap<>();
        times.put("originalEstimate","3d");
        times.put("remainingEstimate","1d");
        builder.setFieldValue("timetracking",new ComplexIssueInputFieldValue(times));

        /**
         * 计开始时间
         */
//        builder.setFieldValue("customfield_10708", DateUtil.format(DateUtil.getDateBefore(new Date(),7),DateUtil.PATTERN_CLASSICAL_NORMAL));
        builder.setFieldValue("customfield_10708", sdf_short.format(DateUtil.getDateBefore(new Date(), 7))+"T09:00:00.000+0800");

        ArrayList audits = new ArrayList();
        audits.add(ComplexIssueInputFieldValue.with("value", "PDCP评审"));


        /**
         * 审计类型
         */
        builder.setFieldValue("customfield_11111",audits);
        builder.setAssigneeName("rui.duan1");

        /**
         * 任务内容
         */
//        builder.setFieldValue("'customfield","ADCP评审");
        builder.setDueDate(DateTime.now());
        //设定父问题
        Map<String, Object> parent = new HashMap<String, Object>();
        parent.put("key", "IPD-5878");
        FieldInput parentField = new FieldInput("parent", new ComplexIssueInputFieldValue(parent));
        builder.setFieldInput(parentField);

        final IssueInput input = builder.build();
        BasicIssue claim = issueClient.createIssue(input).claim();
        System.out.println(claim.getKey());

    }

    public void getJiraTaskTypes(){
        MetadataRestClient metadataClient = jiraRestClient.getMetadataClient();
        Iterable<IssueType> issueTypes = metadataClient.getIssueTypes().claim();
        Iterator<IssueType> it = issueTypes.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }


    public void getJiraTaskStatus(){
        MetadataRestClient metadataClient = jiraRestClient.getMetadataClient();
        Iterable<Status> claims = metadataClient.getStatuses().claim();
        Iterator<Status> it = claims.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public void changeStatus(String taskKey){
        IssueRestClient issueClient = jiraRestClient.getIssueClient();
        Issue issueInfo = issueClient.getIssue(taskKey).claim();
        Status status = issueInfo.getStatus();
        System.out.println(status.getId());
//        if(status.getId().equals(10002)){
            TransitionInput transitionInput = new TransitionInput(71,Comment.valueOf("zhsfdfsdfsdfsdfsd"));
            issueClient.transition(issueInfo, transitionInput).claim();

//        Iterable<Transition> claims = issueClient.getTransitions(issueInfo).claim();
//        Iterator<Transition> it = claims.iterator();
//        while (it.hasNext()) {
//            System.out.println(it.next());
//        }
//        }

    }
    public void setEndTime(String taskKey){
        IssueRestClient issueClient = jiraRestClient.getIssueClient();
        IssueInputBuilder builder = new IssueInputBuilder();
        builder.setDueDate(DateTime.now());
        final IssueInput input = builder.build();
//        builder.setid();
        issueClient.updateIssue(taskKey,input);
    }

    public void getSearch(){
        SearchRestClient searchClient = jiraRestClient.getSearchClient();
        SearchResult result = searchClient.searchJql("issuetype=不符合项子任务  AND parent =IPD-2712").claim();

        Iterator<Issue> it = result.getIssues().iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        System.out.println(result);
    }
}

