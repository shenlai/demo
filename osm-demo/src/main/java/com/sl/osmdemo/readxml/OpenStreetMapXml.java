package com.sl.osmdemo.readxml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.alibaba.fastjson.JSON;
import com.sl.osmdemo.vo.NodeVo;
import com.sl.osmdemo.vo.TagVo;
import org.xml.sax.helpers.DefaultHandler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * @Author: sl
 * @Date: 2019/7/8
 * @Description: SAX方式解析OSM地图数据
 */
public class OpenStreetMapXml {

    static String s = null;

    //实现自己的解析方式
    static class MySAXHandler extends DefaultHandler {

        private String id;
        private String lon;
        private String lat;
        private List<TagVo> tags = new ArrayList<>();

        private List<NodeVo> nodeVoList = new ArrayList<>();

        FileOutputStream fosPoint;
        OutputStreamWriter oswPoint;
        BufferedWriter bwPoint;

        FileOutputStream fosArc;
        BufferedWriter bwArc;
        OutputStreamWriter oswArc;

        //开始解析时调用
        public void startDocument() throws SAXException {
            // 点信息
            File pointFile = new File("C:\\Users\\Administrator\\Desktop\\POI\\Point.txt");
            // 弧信息
            File arcFile = new File("C:\\Users\\Administrator\\Desktop\\POI\\Arc.txt");
            try {
                fosPoint = new FileOutputStream(pointFile);
                fosArc = new FileOutputStream(arcFile);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            oswPoint = new OutputStreamWriter(fosPoint);
            bwPoint = new BufferedWriter(oswPoint);
            oswArc = new OutputStreamWriter(fosArc);
            bwArc = new BufferedWriter(oswArc);
            System.out.println("开始解析文档！");
        }

        //完成解析时调用
        public void endDocument() throws SAXException {
            try {
                bwPoint.close();
                oswPoint.close();
                fosPoint.close();
                bwArc.close();
                oswArc.close();
                fosArc.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("文档解析完成！");
        }

        /**
         * 开始一个元素的解析
         *
         * @param uri
         * @param localName
         * @param qName      标签名
         * @param attributes 属性
         * @throws SAXException
         */
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            if ((attributes != null) && attributes.getLength() > 0) {
                //解析node节点
                if (qName.equals("node")) {
                    this.id = attributes.getValue("id");
                    this.lat = attributes.getValue("lat");
                    this.lon = attributes.getValue("lon");


//                    StringBuilder sb = new StringBuilder();
//                    sb.append(attributes.getValue("id") + "      ");
//                    sb.append(attributes.getValue("lat") + "      ");
//                    sb.append(attributes.getValue("lon"));
//                    try {
//                        bwPoint.write(sb.toString() + "\r\n");
//                        bwPoint.flush();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
                    //System.out.println(sb.toString());

                } else if (qName.equals("way")) {
                    s = attributes.getValue("id") + "      "
                            + attributes.getValue("version") + "      ";
                } else if (qName.equals("nd")) {
                    if (s == null)
                        return;
                    try {
                        bwArc.write(s + attributes.getValue("ref") + "      \r\n");
                        bwArc.flush();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println(s + attributes.getValue("ref") + "      ");
                } else if (qName.equals("tag")) {
                    TagVo tagVo = new TagVo();
                    tagVo.setKey(attributes.getValue("name"));
                    tagVo.setKey(attributes.getValue("shop"));
                    tags.add(tagVo);

//                    if (s == null)
//                        return;
//                    try {
//                        bwArc.write(s + "      " + attributes.getValue("k") + "\r\n");
//                        bwArc.flush();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                    System.out.println(s + "      " + attributes.getValue("k"));
                } else if (qName.equals("relation")) {
                    if (s != null)
                        s = null;
                }
            }
        }

        /**
         * 结束一个元素的解析,遇到结束标签时调用此方法 通常在此方法对标签取值并处理
         *
         * @param uri
         * @param localName
         * @param qName
         * @throws SAXException
         */
        public void endElement(String uri, String localName, String qName)
                throws SAXException {

            System.out.println("当前标签:" + qName);
            if ("node".equals(qName)) {
                NodeVo nodes = new NodeVo();
                nodes.setId(this.id);
                nodes.setLat(this.lat);
                nodes.setLon(this.lon);
                nodes.setTag(tags);
                nodeVoList.add(nodes);

                this.tags = new ArrayList<>();
            }

            if (nodeVoList.size() > 20) {
                System.out.println(JSON.toJSONString(nodeVoList));
                Integer i = 1 / 0;
            }
        }

        //该方法是获得元素间的text文本内容，可以通过new String(ch, start, length)来获得
        public void characters(char[] ch, int start, int length)
                throws SAXException {
//            System.out.print(new String(ch, start, length));
        }
    }

    public static void main(String[] args) {
        SAXParserFactory saxfac = SAXParserFactory.newInstance();
        try {
            SAXParser saxparser = saxfac.newSAXParser();
            //InputStream is = new FileInputStream("D:\\项目\\map");
            InputStream is = new FileInputStream("C:\\Users\\Administrator\\Desktop\\POI\\map.osm");
            MySAXHandler handler = new MySAXHandler();
            saxparser.parse(is, handler);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
