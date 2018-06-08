package order;





import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.UUID;

/**
 * Created by maoxiangyi on 2016/5/4.
 */
public class OrderMqSender {
    public static void main(String[] args) {
        String TOPIC = "hello";
        Properties props = new Properties();
        props.put("bootstrap.servers", "master:9092,node1:9092,node2:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int messageNo = 1; messageNo < 1000; messageNo++) {
            System.out.println(messageNo);
            producer.send(new ProducerRecord<>(TOPIC, messageNo + "", new OrderInfo().random()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
