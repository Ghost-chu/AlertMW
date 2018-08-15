package com.mcsunnyside.amw;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		saveDefaultConfig();
	}
	@EventHandler(priority=EventPriority.MONITOR)
	public void placeBlock(BlockPlaceEvent e) {
		@SuppressWarnings("unchecked")
		ArrayList<String> worldList = (ArrayList<String>) getConfig().getList("world");
		boolean isEnabled = false;
		String worldname = e.getBlock().getWorld().getName();
		for (String string : worldList) {
			if(string.equals(worldname)) {
				isEnabled = true;
				break;
			}
		}
		if(!isEnabled) {
			return;
		}
		@SuppressWarnings("unchecked")
		ArrayList<String> blockList = (ArrayList<String>) getConfig().getList("block");
		boolean isCheckBlock = false;
		for (String string : blockList) {
			if(Material.matchMaterial(string)==e.getBlock().getType()) {
				isCheckBlock = true;
				break;
			}
		}
		if(!isCheckBlock) {
			return;
		}
		String worldAlertTitle = getConfig().getString("message."+worldname+"_title");
		String worldAlertSubTitle = getConfig().getString("message."+worldname+"_subtitle");
		if(worldAlertTitle==null) {
			worldAlertTitle = "§f";
		}
		if(worldAlertSubTitle==null) {
			worldAlertSubTitle="§f";
		}
		e.getPlayer().sendTitle(worldAlertTitle, worldAlertSubTitle, getConfig().getInt("time.fadein"), getConfig().getInt("time.stay"), getConfig().getInt("time.fadeout"));
	}
}
