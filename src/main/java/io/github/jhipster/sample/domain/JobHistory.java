package io.github.jhipster.sample.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import io.github.jhipster.sample.domain.enumeration.Language;

/**
 * JobHistory comment.
 */
@ApiModel(description = "JobHistory comment.")
@Entity
@Table(name = "job_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JobHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "job_history_department",
               joinColumns = @JoinColumn(name = "job_history_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "department_id", referencedColumnName = "id"))
    private Set<Department> departments = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "job_history_job",
               joinColumns = @JoinColumn(name = "job_history_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "job_id", referencedColumnName = "id"))
    private Set<Job> jobs = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "job_history_emp",
               joinColumns = @JoinColumn(name = "job_history_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "emp_id", referencedColumnName = "id"))
    private Set<Employee> emps = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public JobHistory startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public JobHistory endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Language getLanguage() {
        return language;
    }

    public JobHistory language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public JobHistory departments(Set<Department> departments) {
        this.departments = departments;
        return this;
    }

    public JobHistory addDepartment(Department department) {
        this.departments.add(department);
        department.getHistories().add(this);
        return this;
    }

    public JobHistory removeDepartment(Department department) {
        this.departments.remove(department);
        department.getHistories().remove(this);
        return this;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public JobHistory jobs(Set<Job> jobs) {
        this.jobs = jobs;
        return this;
    }

    public JobHistory addJob(Job job) {
        this.jobs.add(job);
        job.getHistories().add(this);
        return this;
    }

    public JobHistory removeJob(Job job) {
        this.jobs.remove(job);
        job.getHistories().remove(this);
        return this;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    public Set<Employee> getEmps() {
        return emps;
    }

    public JobHistory emps(Set<Employee> employees) {
        this.emps = employees;
        return this;
    }

    public JobHistory addEmp(Employee employee) {
        this.emps.add(employee);
        employee.getHistories().add(this);
        return this;
    }

    public JobHistory removeEmp(Employee employee) {
        this.emps.remove(employee);
        employee.getHistories().remove(this);
        return this;
    }

    public void setEmps(Set<Employee> employees) {
        this.emps = employees;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobHistory)) {
            return false;
        }
        return id != null && id.equals(((JobHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobHistory{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
