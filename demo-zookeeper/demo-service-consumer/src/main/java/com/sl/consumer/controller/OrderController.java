package com.sl.consumer.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.sl.consumer.pojo.Order;
import com.sl.consumer.pojo.Product;
import com.sl.consumer.utils.LoadBalance;
import com.sl.consumer.utils.RamdomLoadBalance;

import javax.annotation.Resource;


@RequestMapping("/order")
@RestController
public class OrderController {

    @Resource
    private RestTemplate restTemplate;

    private LoadBalance loadBalance = new RamdomLoadBalance();

    @RequestMapping("/getOrder/{id}")
    public Object getOrder(@PathVariable("id") String id) {
        String url = "http://" + loadBalance.choseServiceHost() + "/product/getProduct/1";
        Product product = restTemplate.getForObject(url, Product.class);
        return new Order(id, "orderName", product);
    }
}
