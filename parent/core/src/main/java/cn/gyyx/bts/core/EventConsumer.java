package cn.gyyx.bts.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;


public abstract class EventConsumer<T> {
	private static final Logger logger = LoggerFactory
            .getLogger(EventConsumer.class);
	private static final int LOOP_PER_EVENT_TIMES = 10000;
	
	private int eventTimeIndex = 0;

    private boolean isFirstLoop = true;
    
    protected Injector injector;
    
    public final void loopTemplate() {
        try {
            ensureThreadStart();
            loop();
            eventTimeIndex = 0;
        } catch (Throwable e) {
            logger.error("", e);
            if (e instanceof OutOfMemoryError) {
                System.exit(-1);
            }
        }

    }
    
    public abstract void loop();
    
    private void ensureThreadStart() {
        if (isFirstLoop) {
            logger.info("logic thread starting");
            isFirstLoop = false;
            try {
            	initTemplate();
            } catch (Throwable e) {
                logger.error("", e);
                System.exit(-1);
            }
            logger.info("logic thread started");
        }
    }
    
    private void initTemplate() throws Exception {
    	injector=Guice.createInjector(createModule());
    	initWhenThreadStart();
    }
    
    protected abstract AbstractModule createModule();
    
    protected abstract void initWhenThreadStart() throws Exception;
    
    protected abstract void onEvent(T event, long sequence, boolean endOfBatch)
            throws Throwable;

    public final void onEventTemplate(T event, long sequence, boolean endOfBatch)
            throws Exception {

        try {
            ensureThreadStart();

            try {
                onEvent(event, sequence, endOfBatch);
            } finally {
                //event.clean();
                ++eventTimeIndex;
                if (eventTimeIndex == LOOP_PER_EVENT_TIMES) {
                    eventTimeIndex = 0;
                    loopTemplate();

                    return;

                }

                if (eventTimeIndex > LOOP_PER_EVENT_TIMES) {

                    throw new UnsupportedOperationException(
                            "an impossible event happen , I think we need stop the world");

                }
            }

        } catch (Throwable e) {

            logger.error("", e);

            if (e instanceof OutOfMemoryError) {

                System.exit(-1);

            }

        }

    }
}
