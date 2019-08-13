package cn.gyyx.bts.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

@SuppressWarnings("unchecked")
public class GlobalQueue {
	
	private static final int INIT_LOGIC_EVENT_CAPACITY = 1024 * 8;
	
	private static final Disruptor<LogicEvent> DISRUPTOR;
	
	public static final RingBuffer<LogicEvent> logicQueue;
	
	private static final SleepingWaitExtendStrategy strategy;
	
	static {

        ExecutorService logicExecutor = Executors.newSingleThreadExecutor((
                Runnable r) -> new Thread(r, "LOGIC_THREAD"));

		EventConsumer<LogicEvent> logicEventConsumer = GlobalFactory.logicConsumerFactory
                .newConsumer();
        strategy=new SleepingWaitExtendStrategy(logicEventConsumer);
        DISRUPTOR = new Disruptor<>(
                new LogicEventFactory(), INIT_LOGIC_EVENT_CAPACITY,
                logicExecutor, ProducerType.MULTI,
                strategy );
        DISRUPTOR.handleEventsWith(logicEventConsumer::onEventTemplate);
        logicQueue = DISRUPTOR.getRingBuffer();

        DISRUPTOR.start();

    }
}
