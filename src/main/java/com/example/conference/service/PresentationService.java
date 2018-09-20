package com.example.conference.service;

import com.example.conference.MyException.*;
import com.example.conference.entity.Presentation;
import com.example.conference.entity.Room;
import com.example.conference.repository.PresentationRepository;
import com.example.conference.repository.RoomRepository;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

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

    public Presentation AddPresentation(Presentation presentation, Long roomId) throws PresentationAlreadyExistsException, CoincidesTimeException, PresentationException, NoRoomException {
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

    public void UpdateSetPresentation(@PathVariable("id") Long id,
                                      @ModelAttribute("presentation") Presentation presentation,
                                      @ModelAttribute("room") Long roomId) throws CoincidesTimeException, NoRoomException {
        String namepresentation = presentation.getNamepresentation();
        Date startdate = presentation.getStartdate();
        Date enddate = presentation.getEnddate();
        Optional<Presentation> editPresentation = presentationRepository.findById(id);
        Room newRoom = roomRepository.findById(roomId).orElseThrow(NoRoomException::new);
        Interval currentIntervar = new Interval(startdate.getTime(), enddate.getTime());

        if (!namepresentation.isEmpty() && editPresentation.isPresent()) {

            Presentation currentPresentation = editPresentation.get();

            for (Presentation item : presentationRepository.findByRoom(newRoom)) {

                if (item == currentPresentation) {
                    continue;
                }

                Interval itemInterval = new Interval(item.getStartdate().getTime(), item.getEnddate().getTime());

                if (itemInterval.overlaps(currentIntervar)) {
                    throw new CoincidesTimeException(errorMessageDateInUse);
                }
            }

            currentPresentation.setNamepresentation(namepresentation);
            currentPresentation.setUsers(presentation.getUsers());
            currentPresentation.setStartdate(startdate);
            currentPresentation.setEnddate(enddate);
            currentPresentation.setRoom(newRoom);
            presentationRepository.save(currentPresentation);
        }
    }

    public void DeleteIdPresentation(@PathVariable("id") Long id) throws NotFoundException {
        if (id != null) {
            presentationRepository.deleteById(id);
        } else throw new NotFoundException(errorMessageNotFound);
    }

    public Iterable<Presentation> findAll() {
        return presentationRepository.findAll();
    }

    public Optional<Presentation> findById(Long id) {
        return presentationRepository.findById(id);
    }
}
