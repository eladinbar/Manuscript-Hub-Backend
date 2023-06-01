package com.manuscript.configuration;

import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import com.manuscript.infrastructure.persistence.sql.repositories.IUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
@AllArgsConstructor
public class DefaultAdminUserInitializer implements ApplicationRunner {
    private IUserRepo userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String ADMIN_EMAIL = "manuscript.hubs@gmail.com";
        String ADMIN_NAME = "admin";
        String ADMIN_UID = "XikqtLvG5BfVXfp86xFzp1drzoi2";

        Optional<UserEntity> optionalAdmin = userRepository.findByEmail(ADMIN_EMAIL);
        if(!optionalAdmin.isPresent()) {
            UserEntity admin = UserEntity.builder()
                    .uid(ADMIN_UID)
                    .email(ADMIN_EMAIL)
                    .name(ADMIN_NAME)
                    .role(Role.Admin)
                    .status(Status.Enabled)
                    .createdTime(new Date())
                    .updatedTime(new Date())
                    .build();

            userRepository.save(admin);
        }
    }
}
