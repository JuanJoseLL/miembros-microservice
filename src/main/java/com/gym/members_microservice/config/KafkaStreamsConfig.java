package com.gym.members_microservice.config;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.StreamsBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;
import com.gym.members_microservice.model.DatosEntrenamiento;
import com.gym.members_microservice.model.ResumenEntrenamiento;
import org.apache.kafka.streams.kstream.Materialized;

@Configuration
public class KafkaStreamsConfig {

    @Bean
    public StreamsBuilder streamsBuilder() {
        return new StreamsBuilder();
    }
    @Bean
    public KStream<String, DatosEntrenamiento> kStream(StreamsBuilder streamsBuilder) {
        KStream<String, DatosEntrenamiento> stream = streamsBuilder.stream("datos-entrenamiento");

        stream.groupByKey()
                .windowedBy(TimeWindows.of(Duration.ofDays(7)))
                .aggregate(
                        ResumenEntrenamiento::new,
                        (key, value, aggregate) -> aggregate.actualizar(value),
                        Materialized.as("resumen-entrenamiento-store")
                )
                .toStream()
                .to("resumen-entrenamiento");

        return stream;
    }
}