package myapps.sample;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Arrays;
import java.util.Properties;

public class Consumer {
    public static void main(String[] args) {
        KafkaConfigConsumer kafkaConfigConsumer = new KafkaConfigConsumer("test");
        KafkaConsumer<String,String> consumer = kafkaConfigConsumer.makeKafkaConsumer();
        KafkaConfigProducer kafkaConfigProducer = new KafkaConfigProducer("testResponse");
        KafkaProducer<String,String> producer = kafkaConfigProducer.makeKafkaProducer();
        consumer.subscribe(Arrays.asList("test"));

        while(true) {
            ConsumerRecords<String,String> records = consumer.poll(500);
            for(ConsumerRecord<String,String> record : records) {
                String s = record.topic();
                if("test".equals(s)) {
                    System.out.println("Kafka receive "+record.value());

                    producer.send(new ProducerRecord<>("testResponse",s), new Callback() {
                        @Override
                        public void onCompletion(RecordMetadata recordMetadata, Exception e) {

                        }
                    });
                }
            }
            producer.flush();
        }
    }
}
