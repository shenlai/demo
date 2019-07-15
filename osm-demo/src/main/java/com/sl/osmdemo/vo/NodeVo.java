package com.sl.osmdemo.vo;

import lombok.Data;

import javax.swing.text.html.HTML;
import java.util.List;

/**
 * @Author: sl
 * @Date: 2019/7/9
 * @Description: TODO
 */
@Data
public class NodeVo {

    private String id;

    private String lon;

    private String lat;


    private List<TagVo> tag;
}


