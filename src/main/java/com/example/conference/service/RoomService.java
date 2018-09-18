package com.example.conference.service;

import com.example.conference.MyException.NoRoomException;
import com.example.conference.MyException.NotFoundException;
import com.example.conference.MyException.RoomException;
import com.example.conference.entity.Room;
import com.example.conference.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class RoomService {

    @Value("${error.message6}")
    private String errorMessage6;
    @Value("${error.message7}")
    private String errorMessage7;
    @Value("${error.message9}")
    private String errorMessage9;

    private RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {

        this.roomRepository = roomRepository;
    }

    public void saveRoom(@ModelAttribute("room") Room room) throws RoomException, NoRoomException {
        String nameroom = room.getRoom();

        if (!nameroom.isEmpty()) {
            for (Room item : roomRepository.findAll()) {

                if (nameroom.equals(item.getRoom())) {

                    throw new RoomException("Error:" + errorMessage6);
                }
            }
            Room newRoom = new Room(nameroom);
            roomRepository.save(newRoom);
        } else
        throw new NoRoomException("Error:" + errorMessage7);
    }
    public void deleteIdRoom(@PathVariable("id") Long id) throws Exception {
        if (id != null) {
            roomRepository.deleteById(id);
        }
        else throw new NotFoundException("Error:" + errorMessage9);
    }
}
