package dev.dfonline.getactiondump;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetActionDump implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String ModId = "getactiondump";
	public static final Logger LOGGER = LoggerFactory.getLogger(ModId);
	public static final MinecraftClient MC = MinecraftClient.getInstance();

	public static database db;
	public static boolean reportKeepAlives = false;
	public static String[] overlayText = new String[]{"test", "test2"};

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Obligitory start up message.");

		ClientCommandManager.DISPATCHER.register(ClientCommandManager.literal("actiondump")
				.then(ClientCommandManager.literal("colours").executes(ctx -> {
					startActionDump(true);

					return 0;
				})).executes(ctx -> {
					startActionDump(false);

					return 0;
				})
		);

		ClientCommandManager.DISPATCHER.register(ClientCommandManager.literal("reportkeepalives")
				.then(ClientCommandManager.literal("off").executes(ctx -> {reportKeepAlives = false; MC.player.sendMessage(new LiteralText("Disabled reporting keepAlives."), false); return 0;}))
				.then(ClientCommandManager.literal("on").executes(ctx -> {reportKeepAlives = true; MC.player.sendMessage(new LiteralText("Enabled reporting keepAlives. Once you get the keepAlive you have the most time to not time out."), false); return 0;}))
		);
	}

	public void startActionDump(boolean colours){
		if(colours)
			MC.player.sendChatMessage("/dumpactioninfo -c");
		else
			MC.player.sendChatMessage("/dumpactioninfo");
	}
}
