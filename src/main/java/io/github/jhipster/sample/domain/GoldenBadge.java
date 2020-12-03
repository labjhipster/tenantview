package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A GoldenBadge.
 */
@Entity
@Table(name = "golden_badge")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GoldenBadge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "goldenBadges", allowSetters = true)
    private Identifier iden;

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

    public GoldenBadge name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Identifier getIden() {
        return iden;
    }

    public GoldenBadge iden(Identifier identifier) {
        this.iden = identifier;
        return this;
    }

    public void setIden(Identifier identifier) {
        this.iden = identifier;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoldenBadge)) {
            return false;
        }
        return id != null && id.equals(((GoldenBadge) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GoldenBadge{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
