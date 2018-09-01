package com.example.conference.service;

import com.example.conference.entity.NoNameException;
import com.example.conference.entity.Presentation;
import com.example.conference.entity.Room;
import com.example.conference.repository.RepositoryPresent;
import com.example.conference.repository.RepositoryRoom;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PresentationService {

    private final RepositoryPresent repositoryPresent;
    private final RepositoryRoom repositoryRoom;

    @Value("${error.message4}")
    private String errorMessage4;
    @Value("${error.message5}")
    private String errorMessage5;
    @Value("${error.message8}")
    private String errorMessage8;


    @Autowired
    public PresentationService(RepositoryPresent repositoryPresent, RepositoryRoom repositoryRoom) {
        this.repositoryPresent = repositoryPresent;
        this.repositoryRoom = repositoryRoom;
    }

    public Presentation addPresentation(/*Model model,*/ Presentation presentation, Long roomId) throws NoNameException {
        String namepresentation = presentation.getNamepresentation();
        Date startdate = presentation.getStartdate();
        Date enddate = presentation.getEnddate();
        Optional<Room> newRoom = repositoryRoom.findById(roomId);

        if (!namepresentation.isEmpty() && newRoom.isPresent()) {

            Room expectedRoom = newRoom.get();

            for (Presentation item : repositoryPresent.findAll()) {

                if (namepresentation.equals(item.getNamepresentation())) {

                    throw new NoNameException("Error:" + errorMessage4);
                }

                // check time overlap
                Interval itemInterval = new Interval(item.getStartdate().getTime(), item.getEnddate().getTime());
                Interval currentIntervar = new Interval(startdate.getTime(), enddate.getTime());


                for (Room item1 : repositoryRoom.findAll()) {

                    if (itemInterval.overlaps(currentIntervar) && expectedRoom.equals(item1)) {

                        throw new NoNameException("Error:" + errorMessage8);

                    }
                }
            }
            Presentation newPresentation = new Presentation(namepresentation, startdate, enddate);
            newPresentation.setRoom(expectedRoom);
            repositoryPresent.save(newPresentation);
            return newPresentation;
        }
        throw new NoNameException("Error:" + errorMessage5);
    }
}
