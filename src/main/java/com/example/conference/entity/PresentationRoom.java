package com.example.conference.entity;

public class PresentationRoom {
    private Long presentationId;
    private Long roomId;

    public Long getPresentationId() {
        return presentationId;
    }

    public void setPresentationId(Long presentationId) {
        this.presentationId = presentationId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoonId(Long roomId) {
        this.roomId = roomId;
    }
}
