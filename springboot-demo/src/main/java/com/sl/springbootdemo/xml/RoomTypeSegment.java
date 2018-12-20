package com.sl.springbootdemo.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

public class RoomTypeSegment {

    @XmlAttribute
    private String roomTypeCode;

    @XmlAttribute
    private String roomsAvailable;


    @XmlTransient
    public String getRoomTypeCode() {
        return roomTypeCode;
    }

    public void setRoomTypeCode(String roomTypeCode) {
        this.roomTypeCode = roomTypeCode;
    }

    @XmlTransient
    public String getRoomsAvailable() {
        return roomsAvailable;
    }

    public void setRoomsAvailable(String roomsAvailable) {
        this.roomsAvailable = roomsAvailable;
    }
}
