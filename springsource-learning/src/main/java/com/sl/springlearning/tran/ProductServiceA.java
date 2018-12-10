package com.sl.springlearning.tran;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceA {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductServiceB productServiceB;

    //自定义回滚异常
    @Transactional(propagation = Propagation.REQUIRED,noRollbackFor = ArithmeticException.class)
    //@Transactional(propagation = Propagation.REQUIRED)
    public void A() {
        productDao.insert();
        B();
       // productServiceB.B();

    }
    @Transactional
    public void B() {
        productDao.update();
        throw  new ArithmeticException("测试异常");
    }
}
