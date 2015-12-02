package de.kafka.example;

import java.util.concurrent.ExecutionException;

/**
 * Created by mrsbahnMac on 02.12.15.
 */
public class SimpleCounter {
    private static IProducer producer;
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        if(args.length == 0){
            System.out.println("SimpleCounterProducerNew {broker-list} {topic} {type old/new} {type sync/async} {delay (ms)} {count}");
        }

        /* get arguments */
        String brokerList = args[0];
        String topic = args[1];
        String age = args[2];
        String sync = args[3];
        int delay = Integer.parseInt(args[4]);
        int count= Integer.parseInt(args[5]);

        if(age.equals("old"))
            producer = new ProducerNew(topic);
        else if (age.equals("new"))
            producer= new ProducerNew(("new"));
        else
            System.out.println("Third argument should be old or new, got " + age);


        /*** Start a producer ***/
        producer.configure(brokerList,sync);
        producer.start();


        long startTime = System.currentTimeMillis();
        System.out.println("Starting...");
        producer.produce("Starting...");

          /* produce the numbers */
        for (int i=0; i < count; i++ ) {
            producer.produce(Integer.toString(i));
            Thread.sleep(delay);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("... and we are done. This took " + (endTime - startTime) + " ms.");
        producer.produce("... and we are done. This took " + (endTime - startTime) + " ms.");


         /* close shop and leave */
        producer.close();
        System.exit(0);







    }

}
