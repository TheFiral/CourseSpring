package com.entity;

import javax.persistence.*;

@Entity
@Table(name = "tasks_new")
public class Task {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "owner")
    private String owner;
    @Column(name = "executor")
    private String executor;
    @Column(name = "file_task_name")
    private String fileTaskName;
    @Column(name = "decision_task_name")
    private String decisionTaskName;

    public Task(String name, String owner, String executor) {
        this.name = name;
        this.owner = owner;
        this.executor = executor;
        this.decisionTaskName = "";
    }

    public Task() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getFileTaskName() {
        return fileTaskName;
    }

    public void setFileTaskName(String fileTaskName) {
        this.fileTaskName = fileTaskName;
    }

    public String getDecisionTaskName() {
        return decisionTaskName;
    }

    public void setDecisionTaskName(String decisionTaskName) {
        this.decisionTaskName = decisionTaskName;
    }
}
