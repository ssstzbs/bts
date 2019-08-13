package cn.gyyx.bts.core.ctrl;

import com.google.inject.Inject;

import cn.gyyx.bts.core.ctrl.misc.FailCallBack;
import cn.gyyx.bts.core.ctrl.misc.IServicePicker;
import cn.gyyx.bts.core.ctrl.misc.RandomServicePicker;
import cn.gyyx.bts.core.ctrl.misc.ServiceParams;

public class RestCtrl {

	private final IServicePicker servicePicker;
	
	@Inject
	public RestCtrl (HttpClientCtrl httpClientCtrl,
			RandomCtrl randomCtrl,
			ServiceStateCtrl serviceStateCtrl,
			JsonCtrl jsonCtrl,
			SystemTimeCtrl sysTimeCtrl,
			RestProtoCtrl restProtoCtrl) {
		servicePicker=new RandomServicePicker(restProtoCtrl,httpClientCtrl,randomCtrl,serviceStateCtrl,jsonCtrl,sysTimeCtrl);
	}
	
	public <T > T restRequest(ServiceParams serviceParams,FailCallBack failCallBack,Class<T> backProtoClazz){
		T proto=servicePicker.requestService(serviceParams,backProtoClazz);
		if(proto==null) {
			failCallBack.failedCallBack(serviceParams);
			return null;
		}
		return proto;
	}
	
	public <T> T restRequest(ServiceParams serviceParams,Class<T> backProtoClazz) {
		return servicePicker.requestService(serviceParams,backProtoClazz);
	}
}
