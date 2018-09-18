package com.example.conference.service;

import com.example.conference.MyException.CoincidesTimeException;
import com.example.conference.MyException.NoNamePresentationException;
import com.example.conference.MyException.NotFoundException;
import com.example.conference.MyException.PresentationException;
import com.example.conference.entity.Presentation;
import com.example.conference.entity.Room;
import com.example.conference.repository.PresentationRepository;
import com.example.conference.repository.RoomRepository;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

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
    @Value("${error.message9}")
    private String errorMessage9;


    @Autowired
    public PresentationService(PresentationRepository presentationRepository, RoomRepository roomRepository) {
        this.presentationRepository = presentationRepository;
        this.roomRepository = roomRepository;
    }

    public void addPresentation(Presentation presentation, Long roomId) throws NoNamePresentationException, CoincidesTimeException, PresentationException{
        String namepresentation = presentation.getNamepresentation();
        Date startdate = presentation.getStartdate();
        Date enddate = presentation.getEnddate();
        Optional<Room> newRoom = roomRepository.findById(roomId);
        Interval currentIntervar = new Interval(startdate.getTime(), enddate.getTime());

        if (!namepresentation.isEmpty() && newRoom.isPresent()) {

            Room expectedRoom = newRoom.get();

            for (Presentation item : presentationRepository.findAll()) {

                if (namepresentation.equals(item.getNamepresentation())) {

                    throw new NoNamePresentationException("Error:" + errorMessage4);
                }

                Interval itemInterval = new Interval(item.getStartdate().getTime(), item.getEnddate().getTime());

                if (itemInterval.overlaps(currentIntervar) && expectedRoom.equals(presentation.getRoom())) {

                    throw new CoincidesTimeException("Error:" + errorMessage8);

                }
            }
            Presentation newPresentation = new Presentation(namepresentation, startdate, enddate);
            newPresentation.setRoom(expectedRoom);
            presentationRepository.save(newPresentation);
        } else
        throw new PresentationException("Error:" + errorMessage5);
    }

    public void updateSetPresentation(@PathVariable("id") Long id,
                                @ModelAttribute("presentation") Presentation presentation,
                                @ModelAttribute("room") Long roomId) throws  CoincidesTimeException {
        String namepresentation = presentation.getNamepresentation();
        Date startdate = presentation.getStartdate();
        Date enddate = presentation.getEnddate();
        Optional<Presentation> editPresentation = presentationRepository.findById(id);
        Optional<Room> newRoom = roomRepository.findById(roomId);
        Interval currentIntervar = new Interval(startdate.getTime(), enddate.getTime());

        if (!namepresentation.isEmpty() && editPresentation.isPresent() && newRoom.isPresent()) {

            Presentation currentPresentation = editPresentation.get();
            Room expectedRoom = newRoom.get();

            for (Presentation item : presentationRepository.findByRoom(expectedRoom)) {

                if (item == currentPresentation) {
                    continue;
                }

                Interval itemInterval = new Interval(item.getStartdate().getTime(), item.getEnddate().getTime());

                if (itemInterval.overlaps(currentIntervar)) {
                    throw new CoincidesTimeException("Error:" + errorMessage8);
                }
            }

            currentPresentation.setNamepresentation(namepresentation);
            currentPresentation.setUsers(presentation.getUsers());
            currentPresentation.setStartdate(startdate);
            currentPresentation.setEnddate(enddate);
            currentPresentation.setRoom(expectedRoom);
            presentationRepository.save(currentPresentation);
        }
    }

    public void deleteIdPresentation(@PathVariable("id") Long id) throws Exception {
        if (id != null) {
            presentationRepository.deleteById(id);
        }
        else throw new NotFoundException("Error:" + errorMessage9);
    }
}
