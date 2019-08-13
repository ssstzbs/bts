package cn.gyyx.bts.core.ctrl.misc;

public class ServiceParams {
	
	private final Object proto;
	
	private final String serverType;

	
	private ServiceParams(Builder builder) {
		this.proto=builder.getProto();
		this.serverType=builder.getServerType();
	}
	
	public Object getProto() {
		return proto;
	}


	public String getServerType() {
		return serverType;
	}
	
	public static class Builder{
		
		private Builder() {
			
		}
		private Object proto;
		private String serverType;
		public Object getProto() {
			return proto;
		}
		public void setProto(Object proto) {
			this.proto = proto;
		}
		public String getServerType() {
			return serverType;
		}
		public void setServerType(String serverType) {
			this.serverType = serverType;
		}
		
		public ServiceParams build() {
			return new ServiceParams(this);
		}
		
	}
	
	public static Builder newBuilder() {
		return new Builder();
	}
	
	
}
