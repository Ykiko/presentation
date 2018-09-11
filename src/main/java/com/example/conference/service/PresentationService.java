package com.example.conference.service;

import com.example.conference.NoNameException;
import com.example.conference.entity.Presentation;
import com.example.conference.entity.Room;
import com.example.conference.repository.PresentationRepository;
import com.example.conference.repository.RoomRepository;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PresentationService {

    private final PresentationRepository presentationRepository;
    private final RoomRepository roomRepository;

    @Value("${error.message4}")
    private String errorMessage4;
    @Value("${error.message5}")
    private String errorMessage5;
    @Value("${error.message8}")
    private String errorMessage8;


    @Autowired
    public PresentationService(PresentationRepository presentationRepository, RoomRepository roomRepository) {
        this.presentationRepository = presentationRepository;
        this.roomRepository = roomRepository;
    }

    public Presentation addPresentation(Presentation presentation, Long roomId) throws NoNameException {
        String namepresentation = presentation.getNamepresentation();
        Date startdate = presentation.getStartdate();
        Date enddate = presentation.getEnddate();
        Optional<Room> newRoom = roomRepository.findById(roomId);
        Interval currentIntervar = new Interval(startdate.getTime(), enddate.getTime());

        if (!namepresentation.isEmpty() && newRoom.isPresent()) {

            Room expectedRoom = newRoom.get();

            for (Presentation item : presentationRepository.findAll()) {

                if (namepresentation.equals(item.getNamepresentation())) {

                    throw new NoNameException("Error:" + errorMessage4);
                }

                Interval itemInterval = new Interval(item.getStartdate().getTime(), item.getEnddate().getTime());

                    if (itemInterval.overlaps(currentIntervar) && expectedRoom.equals(presentation.getRoom())) {

                        throw new NoNameException("Error:" + errorMessage8);

                    }
            }
            Presentation newPresentation = new Presentation(namepresentation, startdate, enddate);
            newPresentation.setRoom(expectedRoom);
            presentationRepository.save(newPresentation);
            return newPresentation;
        }
        throw new NoNameException("Error:" + errorMessage5);
    }
}
