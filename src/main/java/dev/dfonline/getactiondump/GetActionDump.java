package dev.dfonline.getactiondump;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
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
	public static boolean scrapeBooks = false;
	public static String[] overlayText = new String[]{};
	public static Screen DisconnectMenu;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Obligitory start up message.");

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("actiondump")
					.then(ClientCommandManager.literal("colours").executes(ctx -> {
						startActionDump(true);

						return 0;
					})).executes(ctx -> {
						startActionDump(false);

						return 0;
					})
			);
			dispatcher.register(ClientCommandManager.literal("reportkeepalives")
					.then(ClientCommandManager.literal("off").executes(ctx -> {reportKeepAlives = false;
						assert MC.player != null;
						MC.player.sendMessage(Text.literal("Disabled reporting keepAlives."), false); return 0;
					}))
					.then(ClientCommandManager.literal("on").executes(ctx -> {reportKeepAlives = true;
						assert MC.player != null;
						MC.player.sendMessage(Text.literal("Enabled reporting keepAlives. Once you get the keepAlive you have the most time to not time out."), false); return 0;
					}))
			);

			dispatcher.register(ClientCommandManager.literal("scrapebooks")
					.then(ClientCommandManager.literal("off").executes(ctx -> {
						scrapeBooks = false;
						assert MC.player != null;
						MC.player.sendMessage(Text.literal("Disabled book scraping."), false); return 0;
					}))
					.then(ClientCommandManager.literal("on").executes(ctx -> {
						scrapeBooks = true;
						assert MC.player != null;
						MC.player.sendMessage(Text.literal("Enabled book scraping."), false); return 0;
					}))
			);
		});
	}

	public void startActionDump(boolean colours){
		if(colours) {
			assert MC.player != null;
			MC.player.sendChatMessage("/dumpactioninfo -c", Text.literal("/dumpactioninfo -c"));
		}
		else {
			assert MC.player != null;
			MC.player.sendChatMessage("/dumpactioninfo", Text.literal("/dumpactioninfo"));
		}
	}

	public static void setMenu(Screen menu){
		DisconnectMenu = menu;
	}
}
