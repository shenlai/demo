package com.sl.springbootdemo;

import org.apache.logging.log4j.util.Strings;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootdemoApplicationTests {

    @Test
    public void contextLoads() {

        Map<String, String> map = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();
        map2.put("key1", "value1");
        map2.put("key2", "value2");
        map2.put("key3", "value3");

        Map<String, String> map3 = new HashMap<>();
        map.putAll(map2);
        map.putAll(map3);
        String x = map.get("qqw");
        Assert.assertTrue(x != null);
    }

    @Test
    public void TestLambda() {

        List<BizDict> bizDicts = new ArrayList<>();
        bizDicts.add(new BizDict("01", 1L));
        bizDicts.add(new BizDict("03", 2L));
        bizDicts.add(new BizDict("03", 3L));

        Stream.of(2, 1, 4, 5, 3).max(Integer::max).get();

        List<Long> dictIds = new ArrayList<Long>();

        //多个bizDicts,说明床型和规格属性表的床型名称相同,取max(bizDict.getDictId())


        List<BizDict> l2 = bizDicts.stream().filter(x -> Strings.isNotEmpty(x.getDictName()) && "03".equals(x.getDictName())).collect(Collectors.toList());

        List<Long> l3 = bizDicts.stream().filter(x -> Strings.isNotEmpty(x.getDictName()) && "03".equals(x.getDictName())).map(x -> x.getDictId()).collect(Collectors.toList());

        Long l4 = bizDicts.stream().filter(x -> Strings.isNotEmpty(x.getDictName()) && "03".equals(x.getDictName())).map(x -> x.getDictId()).max(Long::compareTo).get();


        Assert.assertTrue(true);


    }

}
