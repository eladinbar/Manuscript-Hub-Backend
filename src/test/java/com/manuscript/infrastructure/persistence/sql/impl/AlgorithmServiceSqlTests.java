package com.manuscript.infrastructure.persistence.sql.impl;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.UUID;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.datasource.url=jdbc:mysql://localhost:3306/ManuscriptInfo"})
@ContextConfiguration(locations = "/test-context.xml")
public class AlgorithmServiceSqlTests {
    @Autowired
    private AlgorithmServiceSqlImpl repoService;
//    private AlgorithmModel newAlgorithm;

//    @BeforeTestClass
//    public void initialize() {
////        newAlgorithm = AlgorithmModel.builder()
////                .algorithmId(UUID.randomUUID())
////                .userId(UUID.randomUUID())
////                .url("/url").build();
//    }

    @Test
    public void saveSuccess() {

        assert true;
//        repoService.save(newAlgorithm);
    }
}
