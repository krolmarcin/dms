package pl.com.bottega.dms.application;

import org.apache.log4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import pl.com.bottega.dms.model.events.DocumentPublishedEvent;

@Component
public class DocumentPublishedEmailNotifier {

    @TransactionalEventListener
    @Async
    public void documentPublished(DocumentPublishedEvent documentPublishedEvent) {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Logger.getLogger(DocumentPublishedEvent.class).info("Mailing to recipents!");
    }

}
