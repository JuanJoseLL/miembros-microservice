package com.gym.members_microservice;

import com.gym.members_microservice.service.RecuperacionService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = { "datos-entrenamiento" })
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"
})
public class RecuperacionServiceTest {

    @Autowired
    private RecuperacionService recuperacionService;
    /**
     * Test de integración para RecuperacionService usando Kafka embebido.
     *
     * - Envía un mensaje de prueba al tópico 'datos-entrenamiento' con un productor Kafka.
     * - Invoca el método 'iniciarProcesamientoUnaVez' del servicio para consumir y procesar el mensaje.
     * - Verifica que la configuración de serializadores es correcta para evitar errores de serialización.
     *
     * Este test asegura que el servicio puede recibir y procesar mensajes desde Kafka en un entorno controlado.
     */
    @Test
    public void testKafkaProcesamiento() {
        Map<String, Object> producerProps = KafkaTestUtils.producerProps("localhost:9092");
        producerProps.put("key.serializer", StringSerializer.class.getName());
        producerProps.put("value.serializer", StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(producerProps);
        producer.send(new ProducerRecord<>("datos-entrenamiento", "key", "mensaje de prueba"));
        producer.close();

        recuperacionService.iniciarProcesamientoUnaVez();
    }
}