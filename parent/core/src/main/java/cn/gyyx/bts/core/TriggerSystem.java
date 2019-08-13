package cn.gyyx.bts.core;

import java.util.PriorityQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lishile on 2016/3/8.
 */
public class TriggerSystem implements TriggerInterface {

    private static final Logger logger = LoggerFactory.getLogger(TriggerSystem.class);
    
    private static long curMilltime;

    public TriggerSystem(){
    }

    private final PriorityQueue<Timer> timerQueue = new PriorityQueue<>((Timer o1, Timer o2) -> {
        long triggerTime1 = o1.getTriggerTime();
        long triggerTime2 = o2.getTriggerTime();
        if (triggerTime1 > triggerTime2) {
            return 1;
        } else if (triggerTime1 == triggerTime2) {
            return 0;
        } else {
            return -1;
        }
    });

    @Override
    public void addTimer(Timer timer) {
        timer.setStartTime(curMilltime);
        timerQueue.offer(timer);
    }

    @Override
    public void tickTrigger() {
        Timer tempTimer;

        for (;;) {

            tempTimer = timerQueue.peek();

            if (null == tempTimer) {

                return;

            }

            if (curMilltime <= tempTimer.getTriggerTime()) {

                return;

            }

            timerQueue.poll();
            
            if (tempTimer.getExecuteNum() <= 0) {

                continue;

            }

            try {

                tempTimer.setExecuteNum(tempTimer.getExecuteNum() - 1);

                tempTimer.trigger();

            } catch (Exception e) {

                logger.error("", e);

            }

            if (tempTimer.getExecuteNum() <= 0) {

                continue;

            }

            tempTimer.setStartTime(curMilltime);

            timerQueue.offer(tempTimer);

        }
    }

	public static long getCurMilltime() {
		return curMilltime;
	}

	public static void setCurMilltime(long curMilltime) {
		TriggerSystem.curMilltime = curMilltime;
	}
}