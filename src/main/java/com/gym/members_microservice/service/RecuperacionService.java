// RecuperacionService.java
package com.gym.members_microservice.service;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.*;

@Service
public class RecuperacionService {

    private final KafkaConsumer<String, String> consumer;

    public RecuperacionService(KafkaConsumer<String, String> consumer) {
        this.consumer = consumer;
    }

    public void iniciarProcesamiento() {
        consumer.subscribe(Arrays.asList("datos-entrenamiento"));
        Map<TopicPartition, Long> ultimoOffsetProcesado = cargarUltimoOffset();
        for (TopicPartition tp : ultimoOffsetProcesado.keySet()) {
            consumer.seek(tp, ultimoOffsetProcesado.get(tp));
        }
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                procesarRecord(record);
                guardarOffset(record.topic(), record.partition(), record.offset());
            }
        }
    }

    private Map<TopicPartition, Long> cargarUltimoOffset() {
        return new HashMap<>();
    }

    private void guardarOffset(String topic, int partition, long offset) {

    }
    public void iniciarProcesamientoUnaVez() {
        consumer.subscribe(Arrays.asList("datos-entrenamiento"));
        Map<TopicPartition, Long> ultimoOffsetProcesado = cargarUltimoOffset();
        for (TopicPartition tp : ultimoOffsetProcesado.keySet()) {
            consumer.seek(tp, ultimoOffsetProcesado.get(tp));
        }
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
        for (ConsumerRecord<String, String> record : records) {
            procesarRecord(record);
            guardarOffset(record.topic(), record.partition(), record.offset());
        }
    }
    private void procesarRecord(ConsumerRecord<String, String> record) {
        System.out.printf("Procesando record: key=%s, value=%s, topic=%s, partition=%d, offset=%d%n",
                record.key(), record.value(), record.topic(), record.partition(), record.offset());
    }
}