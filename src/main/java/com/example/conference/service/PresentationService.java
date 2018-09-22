package com.example.conference.service;

import com.example.conference.myException.*;
import com.example.conference.entity.Presentation;
import com.example.conference.entity.Room;
import com.example.conference.repository.PresentationRepository;
import com.example.conference.repository.RoomRepository;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Optional;

@Service
public class PresentationService {

    private final PresentationRepository presentationRepository;
    private final RoomRepository roomRepository;

    @Value("${error.message.presentationInUse}")
    private String errorMessagePresentationInUse;
    @Value("${error.message.presentationIsRequired}")
    private String errorMessagePresentationIsRequired;
    @Value("${error.message.dateInUse}")
    private String errorMessageDateInUse;
    @Value("${error.message.notFound}")
    private String errorMessageNotFound;


    @Autowired
    public PresentationService(PresentationRepository presentationRepository, RoomRepository roomRepository) {
        this.presentationRepository = presentationRepository;
        this.roomRepository = roomRepository;
    }

    public Presentation addPresentation(Presentation presentation, Long roomId) throws PresentationAlreadyExistsException, CoincidesTimeException, PresentationException, NoRoomException {
        String presentationName = presentation.getNamepresentation();
        Date startdate = presentation.getStartdate();
        Date enddate = presentation.getEnddate();
        Room newRoom = roomRepository.findById(roomId).orElseThrow(NoRoomException::new);
        Interval currentIntervar = new Interval(startdate.getTime(), enddate.getTime());

        if (StringUtils.isEmpty(presentationName)) {
            throw new PresentationException(errorMessagePresentationIsRequired);
        }

        if (presentationRepository.findByNamepresentation(presentationName).isPresent()) {
            throw new PresentationAlreadyExistsException(errorMessagePresentationInUse + presentationName);
        }

        for (Presentation item : presentationRepository.findByRoom(newRoom)) {

            Room itemRoom = item.getRoom();
            Interval itemInterval = new Interval(item.getStartdate().getTime(), item.getEnddate().getTime());

            if (itemInterval.overlaps(currentIntervar) && itemRoom.equals(presentation.getRoom())) {
                throw new CoincidesTimeException(errorMessageDateInUse);
            }
        }
        Presentation newPresentation = new Presentation(presentationName, startdate, enddate);
        newPresentation.setRoom(newRoom);
        presentationRepository.save(newPresentation);
        return newPresentation;
    }

    public void updateSetPresentation(Long id, Presentation presentation, Long roomId) throws CoincidesTimeException, NoRoomException, NotFoundException, PresentationException, PresentationAlreadyExistsException {
        String presentationName = presentation.getNamepresentation();
        Date startdate = presentation.getStartdate();
        Date enddate = presentation.getEnddate();
        Presentation editPresentation = presentationRepository.findById(id).orElseThrow(NotFoundException::new);
        Room newRoom = roomRepository.findById(roomId).orElseThrow(NoRoomException::new);
        Interval currentIntervar = new Interval(startdate.getTime(), enddate.getTime());

        for (Presentation item : presentationRepository.findByRoom(newRoom)) {

            if (item == editPresentation) {
                continue;
            }

            Interval itemInterval = new Interval(item.getStartdate().getTime(), item.getEnddate().getTime());

            if (itemInterval.overlaps(currentIntervar)) {
                throw new CoincidesTimeException(errorMessageDateInUse);
            }
        }
        editPresentation.setNamepresentation(presentationName);
        editPresentation.setUsers(presentation.getUsers());
        editPresentation.setStartdate(startdate);
        editPresentation.setEnddate(enddate);
        editPresentation.setRoom(newRoom);
        presentationRepository.save(editPresentation);
    }

    public void deleteIdPresentation(Long id) throws NotFoundException {
        try {
            presentationRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException(errorMessageNotFound);
        }
    }

    public Iterable<Presentation> findAll() {
        return presentationRepository.findAll();
    }

    public Optional<Presentation> findById(Long id) {
        return presentationRepository.findById(id);
    }
}
