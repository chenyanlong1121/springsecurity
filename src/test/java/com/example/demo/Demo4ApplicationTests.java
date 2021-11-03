package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class Demo4ApplicationTests {

    @Test
    void contextLoads() {
        List<String> list=new ArrayList<>();
        list.add("new");
        list.add("");
        list.add(null);
        list.add("gogo");
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
        System.out.println(list.size());
    }

}
