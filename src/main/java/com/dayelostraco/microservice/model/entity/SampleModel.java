package com.dayelostraco.microservice.model.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Calendar;

/**
 * Created by Dayel Ostraco
 * 10/28/15.
 */
@Entity
@Table(name = "SampleModel")
public class SampleModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Column(name = "revision")
    private Integer revision;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Calendar created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    private Calendar modified;

    @PrePersist
    protected void onCreate() {
        created = modified = Calendar.getInstance();
    }

    @PreUpdate
    protected void onUpdate() {
        modified = Calendar.getInstance();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public Calendar getModified() {
        return modified;
    }

    public void setModified(Calendar modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("created", created)
                .add("modified", modified)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SampleModel)) return false;
        SampleModel that = (SampleModel) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(name, that.name) &&
                Objects.equal(created, that.created) &&
                Objects.equal(modified, that.modified);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, created, modified);
    }
}
