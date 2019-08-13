package cn.gyyx.bts.core.ctrl;

import java.util.Random;

import com.google.inject.Inject;

public class RandomCtrl {

	private final Random rand=new Random();
	
	private final GuidCtrl guidCtrl;
	@Inject
	public RandomCtrl(GuidCtrl guidCtrl) {
		this.guidCtrl=guidCtrl;
	}
	
	public int nextInt(int len) {
		rand.setSeed(guidCtrl.generateGuid()*guidCtrl.generateGuid());
		return rand.nextInt(len);
	}
	
	public boolean checkRate(float rate) {
		return true;
	}
}
