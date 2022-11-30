package com.manuscript.sql.entities;

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

    @Column(name = "email", columnDefinition = "TEXT")
    private String email;

    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

    @Column(name = "phoneNumber", columnDefinition = "TEXT")
    private String phoneNumber;

    @Column(name = "uid", columnDefinition = "TEXT")
    private String uid;


}
