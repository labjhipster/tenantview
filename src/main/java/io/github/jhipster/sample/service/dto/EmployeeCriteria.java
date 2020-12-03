package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link io.github.jhipster.sample.domain.Employee} entity. This class is used
 * in {@link io.github.jhipster.sample.web.rest.EmployeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter email;

    private StringFilter phoneNumber;

    private ZonedDateTimeFilter hireDate;

    private LongFilter salary;

    private LongFilter commissionPct;

    private LongFilter userId;

    private LongFilter jobId;

    private LongFilter managerId;

    private LongFilter sibagId;

    private LongFilter gobagId;

    private LongFilter departmentId;

    private LongFilter historyId;

    public EmployeeCriteria() {
    }

    public EmployeeCriteria(EmployeeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.hireDate = other.hireDate == null ? null : other.hireDate.copy();
        this.salary = other.salary == null ? null : other.salary.copy();
        this.commissionPct = other.commissionPct == null ? null : other.commissionPct.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.jobId = other.jobId == null ? null : other.jobId.copy();
        this.managerId = other.managerId == null ? null : other.managerId.copy();
        this.sibagId = other.sibagId == null ? null : other.sibagId.copy();
        this.gobagId = other.gobagId == null ? null : other.gobagId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.historyId = other.historyId == null ? null : other.historyId.copy();
    }

    @Override
    public EmployeeCriteria copy() {
        return new EmployeeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ZonedDateTimeFilter getHireDate() {
        return hireDate;
    }

    public void setHireDate(ZonedDateTimeFilter hireDate) {
        this.hireDate = hireDate;
    }

    public LongFilter getSalary() {
        return salary;
    }

    public void setSalary(LongFilter salary) {
        this.salary = salary;
    }

    public LongFilter getCommissionPct() {
        return commissionPct;
    }

    public void setCommissionPct(LongFilter commissionPct) {
        this.commissionPct = commissionPct;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getJobId() {
        return jobId;
    }

    public void setJobId(LongFilter jobId) {
        this.jobId = jobId;
    }

    public LongFilter getManagerId() {
        return managerId;
    }

    public void setManagerId(LongFilter managerId) {
        this.managerId = managerId;
    }

    public LongFilter getSibagId() {
        return sibagId;
    }

    public void setSibagId(LongFilter sibagId) {
        this.sibagId = sibagId;
    }

    public LongFilter getGobagId() {
        return gobagId;
    }

    public void setGobagId(LongFilter gobagId) {
        this.gobagId = gobagId;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
    }

    public LongFilter getHistoryId() {
        return historyId;
    }

    public void setHistoryId(LongFilter historyId) {
        this.historyId = historyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeeCriteria that = (EmployeeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(hireDate, that.hireDate) &&
            Objects.equals(salary, that.salary) &&
            Objects.equals(commissionPct, that.commissionPct) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(jobId, that.jobId) &&
            Objects.equals(managerId, that.managerId) &&
            Objects.equals(sibagId, that.sibagId) &&
            Objects.equals(gobagId, that.gobagId) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(historyId, that.historyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        firstName,
        lastName,
        email,
        phoneNumber,
        hireDate,
        salary,
        commissionPct,
        userId,
        jobId,
        managerId,
        sibagId,
        gobagId,
        departmentId,
        historyId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
                (hireDate != null ? "hireDate=" + hireDate + ", " : "") +
                (salary != null ? "salary=" + salary + ", " : "") +
                (commissionPct != null ? "commissionPct=" + commissionPct + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (jobId != null ? "jobId=" + jobId + ", " : "") +
                (managerId != null ? "managerId=" + managerId + ", " : "") +
                (sibagId != null ? "sibagId=" + sibagId + ", " : "") +
                (gobagId != null ? "gobagId=" + gobagId + ", " : "") +
                (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
                (historyId != null ? "historyId=" + historyId + ", " : "") +
            "}";
    }

}
