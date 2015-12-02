package de.kafka.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.InterruptException;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Created by mrsbahnMac on 02.12.15.
 */
public class ProducerNew implements  IProducer {
    String topic;
    String sync;
    private Properties kafkaProps= new Properties();
    private Producer<String, String > producer;

    public ProducerNew(String topic) {
        this.topic = topic;
    }

    public void configure(String brokerList, String sync) {
        this.sync = sync;
        kafkaProps.put("bootstrap.servers", brokerList);

        // This is mandatory, even though we don't send keys
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("acks", "1");

        // Set how many time to retry when produce request fails
        kafkaProps.put("retries", "3");
        kafkaProps.put("linger.ms", 5);
    }

    public void start() {

        producer = new KafkaProducer<String, String>(kafkaProps);

    }

    public void produce(String value) throws ExecutionException, InterruptedException {

        if(sync.equals("sync"))
            produceSync(value);
        else if (sync.equals("async"))
            produceAsync(value);
        else throw new IllegalArgumentException("Expected sync or async, got " + sync);

    }

    public void close() {
        producer.close();

    }

    /*** Produce a record and wait for server to reply. 
    Throw an exception if something goes wrong ***/
    private void produceSync(String value) throws ExecutionException, InterruptedException {
        ProducerRecord<String,String> record = new ProducerRecord<String, String>(topic,value);
        producer.send(record).get();

    }
    
    /*** Produce a record without waiting for server. 
     * This includes a callback that will print an error if something goes wrong ***/
    private void produceAsync(String value) throws ExecutionException, InterruptedException {
        ProducerRecord<String,String> record = new ProducerRecord<String,String>(topic, value);
        producer.send(record, new ImpProducerCallack()).get();
    }


    private class ImpProducerCallack implements org.apache.kafka.clients.producer.Callback {

        public void onCompletion(RecordMetadata metadata, Exception exception) {
            if (exception !=null){
                System.out.println("Error producing to topic " + metadata.topic());
                exception.printStackTrace();
            }
        }
    }
}
