package com.payment.taskconsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.common.RechargingMoneyTask;
import com.payment.common.SubTask;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Component
public class TaskConsumer {

    private final KafkaConsumer<String, String> consumer;

    private final TaskResultProducer taskResultProducer;

    public TaskConsumer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                        @Value("${task.topic}")String topic,
                        TaskResultProducer taskResultProducer) {

        this.taskResultProducer = taskResultProducer;

        Properties props = new Properties();

        props.put("bootstrap.servers", bootstrapServers);

        // consumer group
        props.put("group.id", "my-group");

        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        this.consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Collections.singletonList(topic));
        Thread consumerThread = new Thread(() -> {
            try {
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                    ObjectMapper mapper = new ObjectMapper();

                    for (ConsumerRecord<String, String> record : records) {

                        RechargingMoneyTask rechargingMoneyTask;

                        // task run
                        try {
                            rechargingMoneyTask = mapper.readValue(record.value(), RechargingMoneyTask.class);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }

                        // task result
                        for (SubTask subTask : rechargingMoneyTask.getSubTaskList()) {

                            // 정상 수행 가정
                            subTask.setStatus("success");
                        }

                        // produce task Result
                        this.taskResultProducer.sendTaskResult(rechargingMoneyTask.getTaskID(), rechargingMoneyTask);


                    }
                }
            } finally {
                consumer.close();
            }
        });
        consumerThread.start();
    }

}
