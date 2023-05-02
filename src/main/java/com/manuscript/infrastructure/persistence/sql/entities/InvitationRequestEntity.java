package com.manuscript.infrastructure.persistence.sql.entities;

import com.manuscript.core.domain.common.enums.InvitationEnum;
import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.infrastructure.persistence.sql.common.entities.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "InvitationRequest")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class InvitationRequestEntity extends BaseEntity {
    @Column(name = "uid", columnDefinition = "TEXT")
    private String uid;
    @Column(name = "email", columnDefinition = "TEXT")
    private String email;
    @Column(name = "name", columnDefinition = "TEXT")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @Column(name = "phoneNumber", columnDefinition = "TEXT")
    private String phoneNumber;
    @Column(name = "InvitationEnum")
    private InvitationEnum invitationEnum;

}
