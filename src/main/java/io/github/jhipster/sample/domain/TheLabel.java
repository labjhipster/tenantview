package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TheLabel.
 */
@Entity
@Table(name = "the_label")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TheLabel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "label_name", nullable = false)
    private String labelName;

    @ManyToMany(mappedBy = "theLabels")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Operation> operations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabelName() {
        return labelName;
    }

    public TheLabel labelName(String labelName) {
        this.labelName = labelName;
        return this;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Set<Operation> getOperations() {
        return operations;
    }

    public TheLabel operations(Set<Operation> operations) {
        this.operations = operations;
        return this;
    }

    public TheLabel addOperation(Operation operation) {
        this.operations.add(operation);
        operation.getTheLabels().add(this);
        return this;
    }

    public TheLabel removeOperation(Operation operation) {
        this.operations.remove(operation);
        operation.getTheLabels().remove(this);
        return this;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TheLabel)) {
            return false;
        }
        return id != null && id.equals(((TheLabel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TheLabel{" +
            "id=" + getId() +
            ", labelName='" + getLabelName() + "'" +
            "}";
    }
}
