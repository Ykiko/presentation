package com.example.conference.service;

import com.example.conference.MyException.NoNameException;
import com.example.conference.entity.Presentation;
import com.example.conference.repository.PresentationRepository;
import com.example.conference.repository.RoomRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PresentationServiceTest {

    @Autowired
    PresentationService presentationService;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    PresentationRepository presentationRepository;

    @Value("${error.message4}")
    private String errorMessage4;

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
        assertEquals(result.getRoom().getId(), Long.valueOf(1L), "Error create.");
        assertTrue(presentationRepository.findByNamepresentation("Algebra").isPresent(), "Presentation is null.");
    }

    @Test
    void addPresentation1() {
        Presentation presentation = new Presentation("Biology", new Date(), new Date());
        Throwable exception = assertThrows(NoNameException.class,() -> {
            presentationService.addPresentation(presentation, 2L);
        });
        assertEquals("Error:Presentation is already in use!", exception.getMessage());
    }
}
