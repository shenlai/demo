package com.sl.springlearning.aoptest;

import org.springframework.stereotype.Service;

/**
 * @Author: sl
 * @Date: 2019/3/19
 * @Description: TODO
 */
@Service
public class SalaryManagerImpl implements SalaryManagerService {
    @Override
    public String showSalary() {
        System.out.println("执行showSalary");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "aaaa";
    }
}
