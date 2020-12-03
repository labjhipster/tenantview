package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Department.
 */
@Entity
@Table(name = "department")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "advertisement")
    private byte[] advertisement;

    @Column(name = "advertisement_content_type")
    private String advertisementContentType;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    /**
     * A relationship
     */
    @ApiModelProperty(value = "A relationship")
    @OneToMany(mappedBy = "department")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Employee> employees = new HashSet<>();

    @ManyToMany(mappedBy = "departments")
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

    public String getName() {
        return name;
    }

    public Department name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Department description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getAdvertisement() {
        return advertisement;
    }

    public Department advertisement(byte[] advertisement) {
        this.advertisement = advertisement;
        return this;
    }

    public void setAdvertisement(byte[] advertisement) {
        this.advertisement = advertisement;
    }

    public String getAdvertisementContentType() {
        return advertisementContentType;
    }

    public Department advertisementContentType(String advertisementContentType) {
        this.advertisementContentType = advertisementContentType;
        return this;
    }

    public void setAdvertisementContentType(String advertisementContentType) {
        this.advertisementContentType = advertisementContentType;
    }

    public byte[] getLogo() {
        return logo;
    }

    public Department logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public Department logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public Location getLocation() {
        return location;
    }

    public Department location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Department employees(Set<Employee> employees) {
        this.employees = employees;
        return this;
    }

    public Department addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setDepartment(this);
        return this;
    }

    public Department removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.setDepartment(null);
        return this;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<JobHistory> getHistories() {
        return histories;
    }

    public Department histories(Set<JobHistory> jobHistories) {
        this.histories = jobHistories;
        return this;
    }

    public Department addHistory(JobHistory jobHistory) {
        this.histories.add(jobHistory);
        jobHistory.getDepartments().add(this);
        return this;
    }

    public Department removeHistory(JobHistory jobHistory) {
        this.histories.remove(jobHistory);
        jobHistory.getDepartments().remove(this);
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
        if (!(o instanceof Department)) {
            return false;
        }
        return id != null && id.equals(((Department) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Department{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", advertisement='" + getAdvertisement() + "'" +
            ", advertisementContentType='" + getAdvertisementContentType() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            "}";
    }
}
