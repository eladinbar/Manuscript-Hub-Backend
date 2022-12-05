package com.manuscript.persistence.nosql.common.documents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public abstract class BaseDocument implements Serializable, Persistable<UUID> {
    @Id
    protected UUID id;

    @CreatedDate
    private Date createdTime;

    @LastModifiedDate
    private Date updatedTime;

    @Override
    public boolean isNew() {
        return id == null;
    }

}
