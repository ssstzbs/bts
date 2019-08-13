package cn.gyyx.bts.game.world;

import java.lang.reflect.Field;
import java.util.TreeMap;

import com.google.inject.Inject;
import com.google.protobuf.Parser;
import com.gyyx.dragon.ntxs_proto.p_cg.p_cg_register;

import cn.gyyx.bts.core.AppSocketFrameDecoder;
import cn.gyyx.bts.core.LogicEvent;
import cn.gyyx.bts.core.TcpHandler;
import cn.gyyx.bts.core.Timer;
import cn.gyyx.bts.core.Utils;
import cn.gyyx.bts.core.ctrl.Warpper;
import cn.gyyx.bts.core.ctrl.misc.ServerTypeEnum;
import cn.gyyx.bts.core.ctrl.misc.ServiceParams;
import cn.gyyx.bts.core.ctrl.misc.ZooPropertiesEnum;
import cn.gyyx.bts.core.world.World;
import cn.gyyx.bts.game.ctrl.GameNetServicCtrl;
import cn.gyyx.bts.protobuf.restbean.playerstate.QueryPlayerOnline;
import cn.gyyx.bts.protobuf.restbean.playerstate.QueryPlayerOnlineResult;
import io.netty.channel.Channel;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class WorldGame extends World {
	
	private final Int2ObjectOpenHashMap<TcpHandler> protoEnumAndHandler=new Int2ObjectOpenHashMap<>();
	
	
	@Inject
	public WorldGame(Warpper warpper) throws Exception{
		super(warpper);
		registerTCPHandlers();
		Int2ObjectMap<Parser<?>> tmp=new Int2ObjectOpenHashMap<>(AppSocketFrameDecoder.protoEnumAndParser);
		AppSocketFrameDecoder.protoEnumAndParser=Int2ObjectMaps.unmodifiable(tmp);
		warpper.getTimerCtrl().addTimer(new Timer(1000,Integer.MAX_VALUE,this::test) {
		});
	}
	
	private void test(Timer timer) {
		ServiceParams.Builder builder=ServiceParams.newBuilder();
		builder.setServerType(ServerTypeEnum.PLAYER_STATE);
		builder.setProto(new QueryPlayerOnline(1, true, "test"));
		QueryPlayerOnlineResult result=warpper.getRestCtrl().restRequest(builder.build(), QueryPlayerOnlineResult.class);
		System.out.println(result.getSs());
	}
	
	private void registerTCPHandlers() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		registerTCPHandler(p_cg_register.class,this::p_cg_register_handler);
		
		
	}
	
	private void registerTCPHandler(Class<?> clazz,TcpHandler handler) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		int hash=Utils.uniqueStringHash(clazz.getName());
		if(protoEnumAndHandler.containsKey(hash)) {
			throw new RuntimeException();
		}
		protoEnumAndHandler.put(hash, handler);
		Field field = clazz.getField("PARSER");
		Parser<?> reflectParser = (Parser<?>)field.get(clazz);
		AppSocketFrameDecoder.protoEnumAndParser.put(hash,reflectParser);
	}
	
	private void p_cg_register_handler(Channel channel,Object poro) {
		
	}
	
	public void AS_on_tcp_request_comming(LogicEvent evt) {
	}
	
	public void AS_on_tcp_connect(LogicEvent evt) {
	}

	public void AS_on_tcp_disconenct(LogicEvent evt) {
	}

	@Override
	protected void registerHttpService() {
		
	}

	@Override
	protected void registerMircoService() {
		
	}

	@Override
	protected void shutdownImpl() {
		
	}

	@Override
	protected TreeMap<String, String> constructRegisterInfo() {
		GameNetServicCtrl gameNetCtrl=(GameNetServicCtrl) warpper.getNetServiceCtrl();
		TreeMap<String,String> params=new TreeMap<>();
		params.put(ZooPropertiesEnum.IPV4_TCP_HOST, gameNetCtrl.getIpv4().getHost());
		params.put(ZooPropertiesEnum.IPV4_TCP_PORT, gameNetCtrl.getIpv4().getPort()+"");
		params.put(ZooPropertiesEnum.IPV6_TCP_HOST, gameNetCtrl.getIpv6().getHost());
		params.put(ZooPropertiesEnum.IPV6_TCP_PORT, gameNetCtrl.getIpv6().getPort()+"");
		return params;
	}

	@Override
	protected String getServerType() {
		return ServerTypeEnum.GAME;
	}
		
}
