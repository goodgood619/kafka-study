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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.Arrays;
import java.util.Properties;

public class Producer {

    public static void main(String[] args) throws InterruptedException {

        KafkaConfigProducer kafkaConfigProducer = new KafkaConfigProducer("test");
        KafkaProducer<String,String> producer = kafkaConfigProducer.makeKafkaProducer();
        KafkaConfigConsumer kafkaConfigConsumer = new KafkaConfigConsumer("testResponse");
        KafkaConsumer<String,String> kafkaConsumer = kafkaConfigConsumer.makeKafkaConsumer();
        kafkaConsumer.subscribe(Arrays.asList("testResponse"));

        for(int i=0;i<5;i++) {
            String s = "KafKa Produce Sample Test"+ i;
            producer.send(new ProducerRecord<>("test", s), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    System.out.println(recordMetadata.toString());
                }
            });
        }
        producer.flush();
        producer.close();

        Thread.sleep(2000);

        while(true) {
            ConsumerRecords<String,String> records = kafkaConsumer.poll(500);
            for(ConsumerRecord<String,String> record : records) {
                String s = record.topic();
                if("testResponse".equals(s)) {
                    System.out.println("Kafka receive "+record.value());
                }
            }
            break;
        }
    }
}
