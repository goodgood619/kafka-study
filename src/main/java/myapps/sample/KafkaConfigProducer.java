package myapps.sample;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.Properties;

public class KafkaConfigProducer {

    private String topic;

    public KafkaConfigProducer(String topic) {
        this.topic = topic;
    }
    public Properties makeConfigProducer() {
        Properties props = new Properties();
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("group.id",this.topic);
        return props;
    }

    public KafkaProducer<String,String> makeKafkaProducer() {
        return new KafkaProducer<String, String>(makeConfigProducer());
    }
}
