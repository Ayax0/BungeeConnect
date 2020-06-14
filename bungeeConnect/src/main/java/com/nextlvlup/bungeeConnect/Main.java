package com.nextlvlup.bungeeConnect;

import com.nextlvlup.bungeeConnect.endpoint.ServerEndpoint;
import com.nextlvlup.bungeeConnect.server.ServerManager;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {
	
	@Getter private static ServerManager serverManager;
	
	@Override
	public void onEnable() {
		serverManager = new ServerManager(40);
		serverManager.start();
		
		serverManager.registerEndpoint("/server", new ServerEndpoint());
	}
	
	@Override
	public void onDisable() {
		serverManager.stop();
	}

}
