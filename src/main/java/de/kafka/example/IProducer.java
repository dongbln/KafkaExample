package de.kafka.example;

import java.util.concurrent.ExecutionException;

/**
 * Created by mrsbahnMac on 02.12.15.
 */
public interface IProducer {

    /*** Create configuration ***/
    public void configure(String brokerList, String sync);

    /*** To start producer ***/
    public void start();

    /**
     * create record and send to Kafka
     * because the key is null, data will be sent to a random partition.
     * exact behavior will be different depending on producer implementation
     */
    public void produce(String s) throws ExecutionException, InterruptedException;

    public void close();


}
