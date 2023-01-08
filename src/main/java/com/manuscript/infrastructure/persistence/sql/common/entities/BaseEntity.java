package com.manuscript.infrastructure.persistence.sql.common.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdTime", "updatedTime"}, allowGetters = true)
@TypeDef(name = "json", typeClass = JsonType.class)
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    @org.hibernate.annotations.Type(type = "uuid-char")
    protected UUID id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedTime", nullable = false, updatable = false)
    @CreatedDate
    private Date createdTime;

    //TODO need to be changed
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UpdatedTime", nullable = false)
    @LastModifiedDate
    private Date updatedTime;

}
