package com.barlo.project_service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;


@ActiveProfiles("test")
@ExtendWith(TestResults.class)
public abstract class AbstractTest {
}
