package com.example.conference.service;

import com.example.conference.entity.Room;
import com.example.conference.exception.NoRoomException;
import com.example.conference.exception.NotFoundException;
import com.example.conference.exception.RoomException;
import com.example.conference.repository.RoomRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "error.message")
@Data
public class RoomService {

    private final RoomRepository roomRepository;

    private String roomInUse;

    private String roomIsRequired;

    private String notFound;

    public void saveRoom(Room room) throws RoomException, NoRoomException {
        String nameroom = room.getRoom();

        if (nameroom.isEmpty()) {
            throw new NoRoomException(roomIsRequired);
        }
        for (Room item : roomRepository.findByRoom(nameroom)) {

            if (nameroom.equals(item.getRoom())) {

                throw new RoomException(roomInUse);
            }
        }
        Room newRoom = new Room(nameroom);
        roomRepository.save(newRoom);
    }

    public void deleteIdRoom(Long id) throws NotFoundException {
        try {
            roomRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException(notFound);
        }
    }

    public Iterable<Room> findAll() {
        return roomRepository.findAll();
    }

}
