package com.nextlvlup.bungeeConnect.endpoint;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ServerEndpoint extends Endpoint {
	
	private HashMap<ServerInfo, ServerPing> onlineServer = new HashMap<ServerInfo, ServerPing>();
	
	public ServerEndpoint() {
		new Timer().scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				for(ServerInfo server : BungeeCord.getInstance().getServers().values()) {
					server.ping(new Callback<ServerPing>() {

						@Override
						public void done(ServerPing ping, Throwable error) {
							if(onlineServer.containsKey(server)) onlineServer.remove(server);
							if(error == null) onlineServer.put(server, ping);
						}
					});
				}
			}
		}, 0L, 1000 * 3);
	}

	@Override
	public String getResponse(HttpExchange exchange) {
		JSONArray obj = new JSONArray();
		
		Map<String, ServerInfo> map = BungeeCord.getInstance().getServers();
		for(String name : map.keySet()) {
			JSONObject server = new JSONObject();
			ServerInfo info = map.get(name);
			
			if(onlineServer.containsKey(info)) {
				ServerPing ping = onlineServer.get(info);
				
				server.put("name", info.getName());
				server.put("motd", TextComponent.toPlainText(ping.getDescriptionComponent()));
				server.put("version", ping.getVersion().getName());
				
				JSONArray players = new JSONArray();
				for(ProxiedPlayer player : info.getPlayers()) {
					JSONObject p = new JSONObject();
					p.put("name", player.getDisplayName());
					p.put("uuid", player.getUniqueId());
					p.put("ping", player.getPing());
					players.put(p);
				}
				server.put("player", players);
				
				obj.put(server);
			}
		}
		
		return obj.toString();
	}

}
