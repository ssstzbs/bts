package cn.gyyx.bts.core.ctrl;

import com.google.inject.Inject;

public class SystemTimeCtrl {
	
	/**
	 * 系统的毫秒时间戳
	 */
	private long sysMillTime;

	/**
	 * 系统的秒数时间戳
	 */
	private int sysSecTime;
	
	/**
	 * 帧率间隔
	 */
	private int deltaMilltime;
	
	@Inject
	public SystemTimeCtrl() {
		
	}

	public long getSysMillTime() {
		return sysMillTime;
	}

	public void setSysMillTime(long sysMillTime) {
		this.sysMillTime = sysMillTime;
	}

	public int getSysSecTime() {
		return sysSecTime;
	}

	public void setSysSecTime(int sysSecTime) {
		this.sysSecTime = sysSecTime;
	}
	
	public int update()
	{
		long cur = System.currentTimeMillis();
		long escapedMillTime = cur - sysMillTime;
		if(escapedMillTime < 1000/25)
		{
			return 0;
		}
		this.sysMillTime=cur;
		this.sysSecTime=(int) (cur/1000);
		deltaMilltime=(int)escapedMillTime;
		return deltaMilltime;

	}
}
