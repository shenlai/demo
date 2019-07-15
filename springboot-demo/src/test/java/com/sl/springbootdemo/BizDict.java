package com.sl.springbootdemo;

import lombok.Data;

/**
 * @Author: sl
 * @Date: 2019/3/7
 * @Description: TODO
 */
@Data
public class BizDict {

    public BizDict(String name, Long dictId) {
        this.dictId = dictId;
        this.dictName = name;
    }

    private Long dictId;

    private String dictName;

}
