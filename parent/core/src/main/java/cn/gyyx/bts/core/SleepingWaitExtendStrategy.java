package cn.gyyx.bts.core;

import com.lmax.disruptor.AlertException;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.TimeoutException;
import com.lmax.disruptor.WaitStrategy;

public class SleepingWaitExtendStrategy implements WaitStrategy {
	
	private final int retries;
	private static final int DEFAULT_RETRIES = 200;
	private final EventConsumer<?> eventConsumer;
	
	public SleepingWaitExtendStrategy(EventConsumer<?> eventConsumer) {

		this.retries = DEFAULT_RETRIES;

		this.eventConsumer = eventConsumer;

	}
	@Override
	public void signalAllWhenBlocking() {
	}

	@Override
	public long waitFor(final long sequence, Sequence cursor,
			final Sequence dependentSequence, final SequenceBarrier barrier)
			throws AlertException, InterruptedException, TimeoutException {
		long availableSequence;
		int counter = retries;

		while ((availableSequence = dependentSequence.get()) < sequence) {
			eventConsumer.loopTemplate();
			counter = applyWaitMethod(barrier, counter);
		}
		return availableSequence;
	}

	private int applyWaitMethod(final SequenceBarrier barrier, int counter)
			throws AlertException {
		barrier.checkAlert();
		if (counter > 100) {
			--counter;
		} else if (counter > 0) {
			--counter;
			Thread.yield();
		} else {
		}
		return counter;
	}
}
