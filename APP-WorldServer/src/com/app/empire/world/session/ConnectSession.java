package com.app.empire.world.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.app.empire.protocol.data.account.RoleLoginOk;
import com.app.empire.protocol.data.error.ProtocolError;
import com.app.empire.protocol.data.server.Heartbeat;
import com.app.empire.protocol.data.server.Kick;
import com.app.empire.protocol.data.server.NotifyMaintance;
import com.app.empire.protocol.data.server.NotifyMaxPlayer;
import com.app.empire.protocol.data.server.ShutDown;
import com.app.empire.world.WorldServer;
import com.app.empire.world.model.Client;
import com.app.empire.world.model.player.WorldPlayer;
import com.app.empire.world.service.base.impl.PlayerService;
import com.app.empire.world.service.factory.ServiceManager;
import com.app.empire.world.service.impl.ConnectService;
import com.app.empire.world.skeleton.AccountSkeleton;
import com.app.protocol.data.AbstractData;
import com.app.protocol.exception.ProtocolException;
import com.app.session.Session;

/**
 * 类ConnectSession 客户sesson自定义封装应用，继承 Session 类
 * 
 * @see Session
 * @author doter
 */
public class ConnectSession extends Session {
	private static final Logger log = Logger.getLogger(ConnectSession.class);
	public static final String SERVERPASSWORD = "serverpassword";
	public static final long GETEXP_TIME_LIMIT = 259200000L;
	private int id;
	private int maxPlayer;
	private String name;

	private ConcurrentHashMap<Integer, Client> sessionId2Clients = new ConcurrentHashMap<Integer, Client>();
	private ConcurrentHashMap<Integer, Client> accountId2Clients = new ConcurrentHashMap<Integer, Client>();
	private ConcurrentHashMap<Integer, Integer> playerid2sessionid = new ConcurrentHashMap<Integer, Integer>();
	private ConcurrentHashMap<Integer, Client> playerId2Clients = new ConcurrentHashMap<Integer, Client>();

	/**
	 * 玩家服务
	 */
	private PlayerService playerService;
	@SuppressWarnings("unused")
	private AccountSkeleton accountSkeleton;
	private boolean shutdown = false;

	public ConnectSession(IoSession session) {
		super(session);
	}

	@Override
	public void created() {
	}

	@Override
	public void closed() {
		if (!(this.shutdown)) {
			for (Entry<Integer, Client> entry : accountId2Clients.entrySet()) {
				Integer accountId = entry.getKey();
				WorldPlayer player = this.playerService.getWorldPlayers().get(accountId);
				playerService.release(player);
				ServiceManager
						.getManager()
						.getPlayerService()
						.writeLog(
								"连接关闭保存玩家信息：id=" + player.getPlayer().getId() + "---name=" + player.getPlayer().getNickname() + "---level="
										+ player.getPlayer().getLv());
			}

			log.info(this.name + "closed");
			System.out.println("dispatch " + this.name + " closed");
		}
	}

	@Override
	public <T> void handle(T paramT) {

	}

	@Override
	public void idle(IoSession session, IdleStatus status) {
		System.out.println("关闭链接：" + session);
		session.close(true);
	}

	@Override
	public void opened() {

	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	/**
	 * 根据玩家id发送对应数据包
	 * 
	 * @param seg
	 * @param playerId
	 */
	public void write(AbstractData seg, int playerId) {
		Integer sessionId = this.playerid2sessionid.get(playerId);
		if (sessionId != null) {
			seg.setSessionId(sessionId.intValue());
			write(seg);
		}
	}

	/**
	 * 通知dispatcher server 服务器最大玩家人数信息
	 * 
	 * @param current
	 */
	public void notifyMaxPlayer() {
		if (this.maxPlayer != 0) {
			NotifyMaxPlayer seg = new NotifyMaxPlayer();
			seg.setCurrentCount(getPlayerCount());
			seg.setMaxCount(this.maxPlayer);
			seg.setCurrentTime(System.currentTimeMillis());
			write(seg);
		}
	}

	/**
	 * 获取现在登录人数
	 * 
	 * @return
	 */
	public int getPlayerCount() {
		return this.playerId2Clients.size();
	}

	/**
	 * 踢出玩家
	 * 
	 * @param sessionId
	 */
	public void killSession(int sessionId) {
		Kick kick = new Kick();
		kick.setSession(sessionId);
		write(kick);
	}

	/**
	 * 根据账号id，注销账号
	 * 
	 * @param accountId
	 */
	public void forceLogout(int accountId) {
		Client client = this.accountId2Clients.get(accountId);
		if (client != null) {
			removeClient(client);
			killSession(client.getSessionId());
		}
	}


	// public void checkListened(int playerId) {
	// Object o = this.listened.get(playerId);
	// if ((o != null) && (o != this.NULL)) {
	// this.listenedSession.remove(o);
	// this.listened.put(playerId, this.NULL);
	// }
	// }
	public void shutdown() {
		this.shutdown = true;
		write(new ShutDown());
	}

	public void loginOnline() {
		log.info("ONLINE[" + this.name + "][" + this.playerid2sessionid.size() + "]");
	}

	public int sessionSize() {
		return this.playerid2sessionid.size();
	}

	/**
	 * 禁止玩家上线
	 * 
	 * @param playerId
	 */
	public void kick(int playerId) {
		Client client = this.playerId2Clients.get(playerId);
		if (client != null) {
			removeClient(client);
			this.killSession(client.getSessionId());
		}
	}

	/**
	 * 把玩家链接信息从map表中移除
	 * 
	 * @param client
	 */
	public void removeClient(Client client) {
		this.sessionId2Clients.remove(client.getSessionId());
		this.accountId2Clients.remove(client.getAccountId());
		this.playerId2Clients.remove(client.getPlayerId());

		if (client.getStatus() == Client.STATUS.PLAYERLOGIN) {
			WorldPlayer player = this.playerService.getWorldPlayers().get(client.getPlayerId());
			if (player != null)
				playerService.release(player);
		}
	}

	public void setAccountSkeleton(AccountSkeleton accountSkeleton) {
		this.accountSkeleton = accountSkeleton;
	}

	public void setPlayerService(PlayerService playerService) {
		this.playerService = playerService;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMaxPlayer(int max) {
		this.maxPlayer = max;
	}

	/**
	 * 通知dispatcher server 服务器是否在维护信息
	 */
	public void notifyMaintanceStatus() {
		if (this.maxPlayer != 0) {
			NotifyMaintance seg = new NotifyMaintance();
			seg.setMaintance(WorldServer.config.isMaintance());
			write(seg);
		}
	}

	/**
	 * 根据包ID获取<tt>sessionId2Clients</tt>中对应<tt>Client</tt>对象<br>
	 * 如果<tt>sessionId2Clients</tt>中不存在对应<tt>Client</tt>对象,则创建新的<tt>Client</tt>
	 * 对象，并将其加到<tt>sessionId2Clients</tt>中
	 * 
	 * @param sessionId
	 * @return
	 */
	public Client getAndCreateClient(int sessionId) {
		Client client = this.sessionId2Clients.get(sessionId);
		if (client == null) {
			client = new Client(sessionId);
			this.sessionId2Clients.put(sessionId, client);
		}
		return client;
	}

	/**
	 * 根据包ID获取<tt>sessionId2Clients</tt>中对应<tt>Client</tt>对象<br>
	 * 
	 * @param sessionId
	 * @return
	 */
	public Client getClient(int sessionId) {
//		 System.out.println("Client Size:"+this.sessionId2Clients.size()+sessionId2Clients);
		return this.sessionId2Clients.get(sessionId);
	}

	/**
	 * 判断session连接是否已经登录
	 * 
	 * @param sessionId
	 * @return
	 */
	public boolean containsClient(int sessionId) {
		return sessionId2Clients.containsKey(sessionId);
	}

	/**
	 * 根据<tt>sessionId</tt>返回玩家信息
	 * 
	 * @param sessionId
	 * @return
	 * @throws ProtocolException
	 */
	public WorldPlayer getPlayer(int sessionId) throws ProtocolException {
		Client client = this.sessionId2Clients.get(sessionId);
		if (client == null)
			return null;
		return this.playerService.getWorldPlayers().get(client.getPlayerId());
	}

	/**
	 * 根据accountId 获取Client对象
	 * 
	 * @param accountid
	 * @return
	 */
	public Client getClientByAccountId(int accountid) {
		// System.out.println("AClient Size:"+this.accountId2Clients.size());
		return this.accountId2Clients.get(accountid);
	}

	/**
	 * 根据登录角色账号id，注册登录角色
	 * 
	 * @param client
	 */
	public void registerClientWithAccountId(Client client) {
		this.accountId2Clients.put(client.getAccountId(), client);
		// this.accountName2Clients.put(client.getName(), client);
	}

	/**
	 * 服务器是否达到登录上限
	 * 
	 * @return
	 */
	public boolean isFull() {
		return (getPlayerCount() >= this.maxPlayer);
	}

	/**
	 * 游戏角色登录。返回各项游戏角色数据。
	 * 
	 * @param player
	 * @param data
	 * @param client
	 * @param relogin
	 * @return
	 * @throws Exception
	 */
	public void loginPlayer(WorldPlayer worldPlayer, AbstractData data, Client client) throws Exception {
		client.setStatus(Client.STATUS.PLAYERLOGIN);
		client.setPlayerId(worldPlayer.getPlayer().getId());
		worldPlayer.setAccountClient(client);
		worldPlayer.setConnectSession((ConnectSession) data.getHandlerSource());
		worldPlayer.getPlayer().setIsOnline(true);
		addWorldPlayer(client, worldPlayer, data.getSessionId());

		// RoleLoginOk playerLoginOk = new
		// RoleLoginOk(data.getSessionId(), data.getSerial());
		// playerLoginOk.setPlayerId(player.getId());
		// playerLoginOk.setPlayerName(player.getName());
		// playerLoginOk.setTickets(player.getDiamond());
		// playerLoginOk.setMaxLevel(WorldServer.config.getMaxLevel());
		// playerLoginOk.setPlayerHp(player.getMaxHP());
		// playerLoginOk.setPlayerDefend(player.getDefend());
		// playerLoginOk.setPlayerDefense(player.getCrit());
		// playerLoginOk.setPlayerPhysical(player.getMaxPF());
		// playerLoginOk.setPlayerGold(player.getPlayer().getMoneyGold());
		// playerLoginOk.setPlayerHonor(player.getPlayer().getHonor());
		// playerLoginOk.setPlayerSex(player.getPlayer().getSex());
		// playerLoginOk.setLevel(player.getLevel());
		// playerLoginOk.setAttack(player.getAttack());
		// playerLoginOk.setExp(player.getPlayer().getExp());
		// playerLoginOk.setGuildName(player.getGuildName());
		// playerLoginOk.setMedalNum(player.getMedalNum());
		// playerLoginOk.setCritRate(player.getCrit());
		// playerLoginOk.setExplodeRadius(player.getExplodeRadius());
		// playerLoginOk.setProficiency(1);
		// playerLoginOk.setSuit_head("1");
		// playerLoginOk.setSuit_face("1");
		// playerLoginOk.setSuit_body("1");
		// playerLoginOk.setSuit_weapon("1");
		// playerLoginOk.setWeapon_type(1);
		// playerLoginOk.setUpgradeexp(ServiceManager.getManager().getPlayerService()
		// .getUpgradeExp(player.getLevel(), player.getPlayer().getZsLevel()));
		// playerLoginOk.setGuideLevel(10);
		// playerLoginOk.setBlastLevel(1);
		// if (null != player.getPlayer().getVipTime() &&
		// System.currentTimeMillis() <=
		// player.getPlayer().getVipTime().getTime()) {
		// playerLoginOk.setVipLevel(player.getPlayer().getVipLevel());
		// } else {
		// playerLoginOk.setVipLevel(0);
		// }
		// playerLoginOk.setSuit_wing("1");
		// String[] markStr = player.getPlayer().getWbUserId().split(",");
		// playerLoginOk.setPlayer_title("");
		// playerLoginOk.setWeaponLevel(1);
		// Map<String, String> map = new HashMap<String, String>();
		// String[] str;
		// for (String s : markStr) {
		// str = s.split("=");
		// map.put(str[0], str[1]);
		// }
		// playerLoginOk.setWbUserId(new String[]{"map.get(Common.XLWB)",
		// "map.get(Common.TXWB)"});
		// playerLoginOk.setQualifyingLevel(player.getPlayer().getHonorLevel());
		// //
		// System.out.println(ServiceManager.getManager().getPlayerService().getOnlinePlayerNum()+"----------------");
		// playerLoginOk.setZsleve(player.getPlayer().getZsLevel());
		// playerLoginOk.setInjuryFree(player.getInjuryFree());
		// playerLoginOk.setWreckDefense(player.getWreckDefense());
		// playerLoginOk.setReduceCrit(player.getReduceCrit());
		// playerLoginOk.setReduceBury(player.getReduceBury());
		// playerLoginOk.setForce(player.getForce());
		// playerLoginOk.setArmor(player.getArmor());
		// playerLoginOk.setAgility(player.getAgility());
		// playerLoginOk.setPhysique(player.getPhysique());
		// playerLoginOk.setLuck(player.getLuck());
		// playerLoginOk.setDoubleCard(player.isHasDoubleCard());
		// playerLoginOk.setFighting(player.getFighting());
		// playerLoginOk.setGuildId(player.getGuildId());
		// playerLoginOk.setSteps(player.getPlayer().getSteps());
		// playerLoginOk.setVipMark(0);
		// playerLoginOk.setVipLastDay(0);
		//
		// playerLoginOk.setHeart(1);
		// // int petNum =
		// //
		// ServiceManager.getManager().getPetItemService().getPlayerPetNum(player.getId());
		// playerLoginOk.setPetNum(1);
		// return playerLoginOk;
	}

	/**
	 * 将玩家信息存入服务器缓存中
	 * 
	 * @param client
	 * @param player
	 * @param sessionId
	 */
	private void addWorldPlayer(Client client, WorldPlayer player, int sessionId) {
		this.playerId2Clients.put(player.getPlayer().getId(), client);
		this.playerid2sessionid.put(player.getPlayer().getId(), sessionId);
	}

	/**
	 * 用户是否已经在线
	 * 
	 * @param playerId
	 * @return
	 */
	public boolean contains(int playerId) {
		return this.playerid2sessionid.containsKey(id);
	}

	public int getPlayerSessionId(int playerId) {
		Integer sessionId = this.playerid2sessionid.get(playerId);
		if (sessionId != null) {
			return sessionId.intValue();
		}
		return -1;
	}

	/**
	 * 拋出异常
	 */
	@Override
	public void sendError(ProtocolException ex) {
		ProtocolError seg = new ProtocolError(ex);
		write(seg);
	}

	public String getName() {
		return this.name;
	}

	public int getMaxPlayer() {
		return this.maxPlayer;
	}

}