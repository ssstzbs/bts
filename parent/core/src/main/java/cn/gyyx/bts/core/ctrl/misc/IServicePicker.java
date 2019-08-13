package cn.gyyx.bts.core.ctrl.misc;

public interface IServicePicker {
	
	<T> T requestService(ServiceParams params,Class<T> backProtoClazz);
}
