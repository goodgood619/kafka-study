package myapps.sample;


import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;


public class KafkaConfigConsumer {

    private String topic;
    public KafkaConfigConsumer(String topic){
        this.topic = topic;
    }
    public Properties makeConfigConsumer() {
        Properties props = new Properties();
        props.put("group.id",this.topic);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");

        return props;
    }

    public KafkaConsumer<String,String> makeKafkaConsumer() {
        return new KafkaConsumer<String, String>(makeConfigConsumer());
    }
}
