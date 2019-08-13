package cn.gyyx.bts.core.ctrl;

import com.google.inject.Inject;

public class ProcessIndexCtrl {
	private final long processIndex;
	@Inject
	public ProcessIndexCtrl(GuidCtrl guidCtrl) throws Exception {
		processIndex=guidCtrl.generateGuid();
	}
	public long getProcessIndex() {
		return processIndex;
	}
}
