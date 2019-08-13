package cn.gyyx.bts.core.ctrl;

import com.google.inject.Inject;

public class Warpper {
	
	private final GuidCtrl guidCtrl;
	
	private final NetServicCtrl netServiceCtrl;
	
	private final IPCtrl ipCtrl;
	
	private final SystemTimeCtrl sysTimeCtrl;
	
	private final TimerCtrl timerCtrl;
	
	private final ZooCtrl zooCtrl;
	
	private final ProcessIndexCtrl processIndexCtrl;

	private final ZooPathCtrl zooPathCtrl;
	private final HttpClientCtrl httpClientCtrl;
	private final JsonCtrl jsonCtrl;
	
	private final HttpServiceCtrl httpServiceCtrl;
	
	private final ServiceStateCtrl serviceStateCtrl;
	
	private final MircoServiceCtrl mircoServiceCtrl;
	
	private final RestCtrl restCtrl;
	
	private final SessionCtrl sessionCtrl;
	@Inject
	public Warpper(SessionCtrl sessionCtrl,
			RestCtrl restCtrl,
			MircoServiceCtrl mircoServiceCtrl,
			ServiceStateCtrl serviceStateCtrl,
			HttpServiceCtrl httpServiceCtrl,
			HttpClientCtrl httpClientCtrl,
			JsonCtrl jsonCtrl,
			ProcessIndexCtrl processIndexCtrl,
			ZooCtrl zooCtrl,
			ZooPathCtrl zooPathCtrl,
			TimerCtrl timerCtrl,
			SystemTimeCtrl sysTimeCtrl,
			GuidCtrl guidCtrl, 
			NetServicCtrl netServiceCtrl, 
			IPCtrl ipCtrl) {
		super();
		this.sessionCtrl=sessionCtrl;
		this.restCtrl=restCtrl;
		this.serviceStateCtrl=serviceStateCtrl;
		this.httpServiceCtrl=httpServiceCtrl;
		this.httpClientCtrl=httpClientCtrl;
		this.jsonCtrl=jsonCtrl;
		this.zooPathCtrl=zooPathCtrl;
		this.processIndexCtrl=processIndexCtrl;
		this.zooCtrl=zooCtrl;
		this.timerCtrl=timerCtrl;
		this.sysTimeCtrl=sysTimeCtrl;
		this.guidCtrl = guidCtrl;
		this.netServiceCtrl = netServiceCtrl;
		this.ipCtrl = ipCtrl;
		this.mircoServiceCtrl=mircoServiceCtrl;
	}
	public GuidCtrl getGuidCtrl() {
		return guidCtrl;
	}
	public NetServicCtrl getNetServiceCtrl() {
		return netServiceCtrl;
	}
	public IPCtrl getIpCtrl() {
		return ipCtrl;
	}
	public SystemTimeCtrl getSysTimeCtrl() {
		return sysTimeCtrl;
	}
	public TimerCtrl getTimerCtrl() {
		return timerCtrl;
	}
	public ZooCtrl getZooCtrl() {
		return zooCtrl;
	}
	public ProcessIndexCtrl getProcessIndexCtrl() {
		return processIndexCtrl;
	}
	public ZooPathCtrl getZooPathCtrl() {
		return zooPathCtrl;
	}
	public JsonCtrl getJsonCtrl() {
		return jsonCtrl;
	}
	public HttpClientCtrl getHttpClientCtrl() {
		return httpClientCtrl;
	}
	public HttpServiceCtrl getHttpServiceCtrl() {
		return httpServiceCtrl;
	}
	public ServiceStateCtrl getServiceStateCtrl() {
		return serviceStateCtrl;
	}
	public MircoServiceCtrl getMircoServiceCtrl() {
		return mircoServiceCtrl;
	}
	public RestCtrl getRestCtrl() {
		return restCtrl;
	}
	public SessionCtrl getSessionCtrl() {
		return sessionCtrl;
	}
	
}
