package com.sl.springlearning.aoptest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SalaryManagerImplTest {


    @Autowired
    private SalaryManagerService salaryManagerService;

    @Test
    public void showSalary() throws InterruptedException {
        salaryManagerService.showSalary();
        Thread.sleep(1000);
    }
}