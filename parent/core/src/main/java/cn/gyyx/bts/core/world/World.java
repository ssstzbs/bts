package cn.gyyx.bts.core.world;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.curator.framework.recipes.nodes.PersistentEphemeralNode;
import org.apache.curator.framework.recipes.nodes.PersistentEphemeralNode.Mode;

import cn.gyyx.bts.core.FullDecodedRequest;
import cn.gyyx.bts.core.LogicEvent;
import cn.gyyx.bts.core.Timer;
import cn.gyyx.bts.core.TriggerSystem;
import cn.gyyx.bts.core.Utils;
import cn.gyyx.bts.core.Values;
import cn.gyyx.bts.core.ctrl.Warpper;
import cn.gyyx.bts.core.ctrl.misc.Session;
import cn.gyyx.bts.core.ctrl.misc.ZooPropertiesEnum;

public abstract class World {

	protected final Warpper warpper;

	private final PersistentEphemeralNode node;
	
	public World(Warpper warpper) throws Exception {
		this.warpper = warpper;
		registerHttpService();
		registerMircoService();
		warpper.getTimerCtrl().addTimer(new Timer(1000,Integer.MAX_VALUE,this::onSecondTemplate) {});
		node=createZooNode();
	}
	
	private PersistentEphemeralNode createZooNode() throws Exception{
		String gamePath=warpper.getZooPathCtrl().getOnlinePath(warpper.getProcessIndexCtrl().getProcessIndex());
		Map<String,String> params=new TreeMap<>();
		params.put(ZooPropertiesEnum.IPV4_HTTP_HOST, warpper.getNetServiceCtrl().getIpv4Http().getHost());
		params.put(ZooPropertiesEnum.IPV4_HTTP_PORT, warpper.getNetServiceCtrl().getIpv4Http().getPort()+"");
		params.put(ZooPropertiesEnum.PROCESS_INDEX, warpper.getProcessIndexCtrl().getProcessIndex()+"");
		params.put(ZooPropertiesEnum.SERVER_TYPE, getServerType());
		params.putAll(constructRegisterInfo());
		PersistentEphemeralNode node=new PersistentEphemeralNode(warpper.getZooCtrl().constructCurator(), Mode.EPHEMERAL, gamePath, warpper.getJsonCtrl().serialize(params));
		node.start();
		return node;
	}
	
	protected abstract TreeMap<String,String> constructRegisterInfo();
	
	protected abstract String getServerType();

	protected abstract void registerHttpService() ;
	
	protected abstract void registerMircoService() ;
	
	
	private void onSecondTemplate(Timer timer) {
		warpper.getServiceStateCtrl().onSecond();
	}
	
	public void tick() {
		if (0 == warpper.getSysTimeCtrl().update()) {
			return;
		}
		TriggerSystem.setCurMilltime(warpper.getSysTimeCtrl().getSysMillTime());
		warpper.getTimerCtrl().tickTrigger();
	}

	public void AS_on_httpclient_regist(LogicEvent evt) {
		warpper.getSessionCtrl().registerSession(new Session(evt.getChannel(), warpper.getSysTimeCtrl().getSysSecTime()));
	}

	public void AS_on_httpclient_disconnect(LogicEvent evt) {
		Utils.closeQuitely(evt.getChannel());
	}

	public final void AS_on_http_request(LogicEvent evt) {
		FullDecodedRequest msg = (FullDecodedRequest) evt.getProto();
		Values values=msg.getValues();
		String path = msg.getPath();
		String protoString=values.getAsString("protoString");
		String protoClassString=values.getAsString("protoClassString");
		if(protoString==null||protoClassString==null) {
			warpper.getHttpServiceCtrl().handleRequest(evt.getChannel(), path, values);
			return;
		}
		warpper.getMircoServiceCtrl().handleRequest(path, values, warpper.getSessionCtrl().getSession(evt.getChannel()));
	}

	public void AS_on_tcp_request_comming(LogicEvent evt) {

	}

	public void AS_on_tcp_connect(LogicEvent evt) {

	}

	public void AS_on_tcp_disconenct(LogicEvent evt) {

	}
	
	protected void shutdownTemplate() throws IOException {
		shutdownImpl();
		node.close();
	}
	
	protected abstract void shutdownImpl() ;
}
