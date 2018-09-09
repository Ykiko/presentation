package com.example.conference.service;

import com.example.conference.NoNameException;
import com.example.conference.entity.Presentation;
import com.example.conference.repository.RepositoryPresent;
import com.example.conference.repository.RepositoryRoom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PresentationServiceTest {

    @Autowired
    PresentationService presentationService;
    @Autowired
    RepositoryRoom repositoryRoom;
    @Autowired
    RepositoryPresent repositoryPresent;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addPresentation() throws NoNameException {
        Presentation presentation = new Presentation("Algebra", new Date(), new Date());
        Presentation result = presentationService.addPresentation(presentation, 1L);
        assertEquals(result, presentation, "Error create.");
        assertTrue(repositoryPresent.findByNamepresentation("Algebra").isPresent(), "Presentation is null.");


    }
}
