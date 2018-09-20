package com.example.conference.service;

import com.example.conference.MyException.NoRoomException;
import com.example.conference.MyException.NotFoundException;
import com.example.conference.MyException.RoomException;
import com.example.conference.entity.Presentation;
import com.example.conference.entity.Room;
import com.example.conference.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class RoomService {

    @Value("${error.message.roomInUse}")
    private String errorMessageRoomInUse;
    @Value("${error.message.roomIsRequired}")
    private String errorMessageRoomIsRequired;
    @Value("${error.message.notFound}")
    private String errorMessageNotFound;

    private RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {

        this.roomRepository = roomRepository;
    }

    public void SaveRoom(@ModelAttribute("room") Room room) throws RoomException, NoRoomException {
        String nameroom = room.getRoom();

        if (!nameroom.isEmpty()) {
            for (Room item : roomRepository.findAll()) {

                if (nameroom.equals(item.getRoom())) {

                    throw new RoomException(errorMessageRoomInUse);
                }
            }
            Room newRoom = new Room(nameroom);
            roomRepository.save(newRoom);
        } else
        throw new NoRoomException(errorMessageRoomIsRequired);
    }

    public void DeleteIdRoom(@PathVariable("id") Long id) throws NotFoundException {
        if (id != null) {
            roomRepository.deleteById(id);
        }
        else throw new NotFoundException(errorMessageNotFound);
    }

    public Iterable<Room> findAll(){
        return roomRepository.findAll();
    }

    public Optional<Room> findById(Long id) { return  roomRepository.findById(id);}
}
