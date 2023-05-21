package com.manuscript.infrastructure.persistence.sql.entities;

import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.infrastructure.persistence.sql.common.entities.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "User")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class UserEntity extends BaseEntity {
    @Column(name = "uid", unique = true, nullable = false, updatable = false, columnDefinition = "VARCHAR(36)")
    private String uid;
    @Column(name = "email", unique = true, nullable = false, updatable = false, columnDefinition = "VARCHAR(255)")
    private String email;
    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;
    @Column(name = "phone_number", columnDefinition = "TEXT")
    private String phoneNumber;
}
