package com.sonin.kafkaapi;

import com.alibaba.fastjson.JSON;
import com.sonin.kafkaapi.producer.component.CustomKafkaProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/12/11 8:54
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = KafkaApiApplication.class)
public class KafkaApiApplicationTest {

    @Autowired
    private CustomKafkaProducer customKafkaProducer;

    @Test
    public void test1() throws Exception {
        customKafkaProducer.open();
        for (int i = 0; i < 10; i++) {
            long id = System.currentTimeMillis();
            String name = "producer" + id;
            Map<String, Object> entity = new HashMap<>();
            entity.put("id", id);
            entity.put("name", name);
            customKafkaProducer.send("test1", "key1", JSON.toJSONString(entity));
            Thread.sleep(1000);
        }
        customKafkaProducer.close();
    }

}
