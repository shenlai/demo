package com.sl.springbootdemo.xml;

import javax.xml.bind.annotation.*;
import java.util.List;

//@XmlType(propOrder = { "id", "name", "activities", "transitions" })
public class Property{

    @XmlAttribute
    private String chainCode;

    @XmlAttribute
    private String hotelCode;

    @XmlAttribute
    private String startDate;

    @XmlElementWrapper(name = "RoomTypeSegments")
    @XmlElement(name = "RoomTypeSegment")
    private List<RoomTypeSegment> roomTypeSegments;

    @XmlTransient
    public String getChainCode() {
        return chainCode;
    }

    public void setChainCode(String chainCode) {
        this.chainCode = chainCode;
    }
    @XmlTransient
    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }
    @XmlTransient
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    @XmlTransient
    public List<RoomTypeSegment> getRoomTypeSegments() {
        return roomTypeSegments;
    }

    public void setRoomTypeSegments(List<RoomTypeSegment> roomTypeSegments) {
        this.roomTypeSegments = roomTypeSegments;
    }
}