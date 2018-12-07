package com.sl.springbootdemo.xml;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
public class HkDisneyRequest {

    @XmlElementWrapper(name = "Properties")
    @XmlElement(name = "Property")
    private List<Property> properties;

    @XmlTransient
    public List<Property> getProperties() {
        return properties;
    }


    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}



