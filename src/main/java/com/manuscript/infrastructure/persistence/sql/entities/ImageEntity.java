package com.manuscript.infrastructure.persistence.sql.entities;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.infrastructure.persistence.sql.common.entities.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.json.JSONArray;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Image")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class ImageEntity extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @org.hibernate.annotations.Type(type = "uuid-char")
    private UserEntity user;

    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(name = "author", columnDefinition = "TEXT")
    private String author;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "publication_date")
    private Date publicationDate;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Lob
    @Column(name = "tags", columnDefinition = "JSON")
    @org.hibernate.annotations.Type(type = "json")
    private JSONArray tags;

    @Lob
    @Column(name = "shared_user_ids", columnDefinition = "JSON")
    @org.hibernate.annotations.Type(type = "json")
    private JSONArray sharedUserIds;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "privacy")
    private Privacy privacy;
}
