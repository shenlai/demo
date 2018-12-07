package com.sl.springbootdemo;

import com.sl.springbootdemo.xml.HkDisneyRequest;
import com.sl.springbootdemo.xml.JaxbXmlUtil;
import com.sl.springbootdemo.xml.Property;
import com.sl.springbootdemo.xml.RoomTypeSegment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JaxbXmlUtilTest {


    /**
     * 输出：
     *
     * <hkDisneyRequest>
     *     <Properties>
     *         <Property chainCode="COL" hotelCode="SWHBJ" startDate="2018-12-07">
     *             <RoomTypeSegments>
     *                 <RoomTypeSegment roomTypeCode="1KDES" roomsAvailable="2"/>
     *                 <RoomTypeSegment roomTypeCode="1KBUA" roomsAvailable="5"/>
     *             </RoomTypeSegments>
     *         </Property>
     *     </Properties>
     * </hkDisneyRequest>
     */
    @Test
    public void test() {

        HkDisneyRequest hkDisneyRequest = new HkDisneyRequest();

        List<RoomTypeSegment> list = new ArrayList<>();
        RoomTypeSegment segment = new RoomTypeSegment();
        segment.setRoomTypeCode("1KDES");
        segment.setRoomsAvailable("2");
        list.add(segment);

        RoomTypeSegment segment2 = new RoomTypeSegment();
        segment2.setRoomTypeCode("1KBUA");
        segment2.setRoomsAvailable("5");
        list.add(segment2);

        Property property = new Property();
        property.setRoomTypeSegments(list);
        property.setChainCode("COL");
        property.setHotelCode("SWHBJ");
        property.setStartDate("2018-12-07");

        List<Property> properties = new ArrayList<>();
        properties.add(property);
        hkDisneyRequest.setProperties(properties);

        String strXml = JaxbXmlUtil.toXML(hkDisneyRequest);
        System.out.println(strXml);
    }
}
