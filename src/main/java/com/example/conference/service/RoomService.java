package com.example.conference.service;

import com.example.conference.myException.NoRoomException;
import com.example.conference.myException.NotFoundException;
import com.example.conference.myException.RoomException;
import com.example.conference.entity.Room;
import com.example.conference.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public void saveRoom(Room room) throws RoomException, NoRoomException {
        String nameroom = room.getRoom();

        if (nameroom.isEmpty()) {
            throw new NoRoomException(errorMessageRoomIsRequired);
        }
        for (Room item : roomRepository.findByRoom(nameroom)) {

            if (nameroom.equals(item.getRoom())) {

                throw new RoomException(errorMessageRoomInUse);
            }
        }
        Room newRoom = new Room(nameroom);
        roomRepository.save(newRoom);
    }

    public void deleteIdRoom(Long id) throws NotFoundException {
        try {
            roomRepository.deleteById(id);
        } catch (Exception e){
            throw new NotFoundException(errorMessageNotFound);
        }
    }

    public Iterable<Room> findAll() {
        return roomRepository.findAll();
    }

}
