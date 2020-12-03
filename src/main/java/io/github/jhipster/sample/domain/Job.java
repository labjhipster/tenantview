package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import io.github.jhipster.sample.domain.enumeration.JobType;

/**
 * A Job.
 */
@Entity
@Table(name = "job")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(min = 5, max = 25)
    @Column(name = "title", length = 25)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private JobType type;

    @Column(name = "min_salary")
    private Long minSalary;

    @Column(name = "max_salary")
    private Long maxSalary;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "job_chore",
               joinColumns = @JoinColumn(name = "job_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "chore_id", referencedColumnName = "id"))
    private Set<Task> chores = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "jobs", allowSetters = true)
    private Employee emp;

    @ManyToMany(mappedBy = "jobs")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<JobHistory> histories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Job title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JobType getType() {
        return type;
    }

    public Job type(JobType type) {
        this.type = type;
        return this;
    }

    public void setType(JobType type) {
        this.type = type;
    }

    public Long getMinSalary() {
        return minSalary;
    }

    public Job minSalary(Long minSalary) {
        this.minSalary = minSalary;
        return this;
    }

    public void setMinSalary(Long minSalary) {
        this.minSalary = minSalary;
    }

    public Long getMaxSalary() {
        return maxSalary;
    }

    public Job maxSalary(Long maxSalary) {
        this.maxSalary = maxSalary;
        return this;
    }

    public void setMaxSalary(Long maxSalary) {
        this.maxSalary = maxSalary;
    }

    public Set<Task> getChores() {
        return chores;
    }

    public Job chores(Set<Task> tasks) {
        this.chores = tasks;
        return this;
    }

    public Job addChore(Task task) {
        this.chores.add(task);
        task.getLinkedJobs().add(this);
        return this;
    }

    public Job removeChore(Task task) {
        this.chores.remove(task);
        task.getLinkedJobs().remove(this);
        return this;
    }

    public void setChores(Set<Task> tasks) {
        this.chores = tasks;
    }

    public Employee getEmp() {
        return emp;
    }

    public Job emp(Employee employee) {
        this.emp = employee;
        return this;
    }

    public void setEmp(Employee employee) {
        this.emp = employee;
    }

    public Set<JobHistory> getHistories() {
        return histories;
    }

    public Job histories(Set<JobHistory> jobHistories) {
        this.histories = jobHistories;
        return this;
    }

    public Job addHistory(JobHistory jobHistory) {
        this.histories.add(jobHistory);
        jobHistory.getJobs().add(this);
        return this;
    }

    public Job removeHistory(JobHistory jobHistory) {
        this.histories.remove(jobHistory);
        jobHistory.getJobs().remove(this);
        return this;
    }

    public void setHistories(Set<JobHistory> jobHistories) {
        this.histories = jobHistories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Job)) {
            return false;
        }
        return id != null && id.equals(((Job) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Job{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", type='" + getType() + "'" +
            ", minSalary=" + getMinSalary() +
            ", maxSalary=" + getMaxSalary() +
            "}";
    }
}
