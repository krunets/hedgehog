package by.runets.hedgehog.producer.impl;

import by.runets.hedgehog.event.VerificationEvent;
import by.runets.hedgehog.producer.EventProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static by.runets.hedgehog.utils.Constants.KAFKA_TOPIC_KEY;

@Service
public class KafkaEventProducerImpl implements EventProducer<VerificationEvent> {

    private static final Logger LOG = LogManager.getLogger(KafkaEventProducerImpl.class);

    @Value(KAFKA_TOPIC_KEY)
    private String kafkaTopic;
    @Autowired
    private KafkaTemplate<String, VerificationEvent> kafkaTemplate;

    @Override
    public void fireEvent(VerificationEvent event) {
        LOG.debug("Producing event={}", event);
        final ListenableFuture<SendResult<String, VerificationEvent>> send = kafkaTemplate.send(kafkaTopic, event);
        send.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                LOG.error(ex);
            }

            @Override
            public void onSuccess(SendResult<String, VerificationEvent> result) {
                final RecordMetadata recordMetadata = result.getRecordMetadata();
                final ProducerRecord<String, VerificationEvent> producerRecord = result.getProducerRecord();

                LOG.debug("ProducerRecord={}", producerRecord);
                LOG.debug("RecordMetadata={}", recordMetadata);
            }
        });
    }

    public void setKafkaTemplate(KafkaTemplate<String, VerificationEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void setKafkaTopic(String topic) {
        this.kafkaTopic = topic;
    }
}
