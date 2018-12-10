package com.sl.springlearning.tran;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceB {

    @Autowired
    private ProductDao productDao;

    @Transactional(propagation = Propagation.NEVER)
    public void B() {
        productDao.update();
        throw  new ArithmeticException("测试异常");
    }
}
