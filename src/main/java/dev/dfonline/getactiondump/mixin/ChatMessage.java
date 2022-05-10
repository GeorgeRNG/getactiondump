package dev.dfonline.getactiondump.mixin;

import dev.dfonline.getactiondump.ActionDumpOverScreen;
import dev.dfonline.getactiondump.FileManager;
import dev.dfonline.getactiondump.GetActionDump;
import dev.dfonline.getactiondump.database;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

import static dev.dfonline.getactiondump.GetActionDump.*;

@Mixin(ClientPlayNetworkHandler.class)
public class ChatMessage {

	@Inject(method = "onGameMessage", at = @At("HEAD"), cancellable = true)
	private void OnChatMessage(GameMessageS2CPacket packet, CallbackInfo ci){

		String message = packet.getMessage().getString();
		if(packet.getType() == MessageType.SYSTEM){
			if(db != null){ // while the actiondump is active
				if(message.startsWith(" ") || message.equals("}")){ // if it's anything related to the database, add it.
					ci.cancel();
					database.addData(message);
					overlayText = ("§dActionDump Info:\n §0§l| §eLines: §b" + database.Lines + "\n §0§l| §eSize: §b" + database.Length + "\n §0§l| §eTime: §b" + ((float)(System.currentTimeMillis() - database.startTime.getTime()) / 1000)).split("\n");
				}
				if(message.equals("}")){ // to end the actiondump scanning, previous if block should have ran.
					try{
						database.save();
						setMenu(new ActionDumpOverScreen(new LiteralText("ActionDump Complete"), new LiteralText(
								"§eLines: §b" + database.Lines +
										"\n§eSize: §b" + database.Length +
										"\n§eTime: §b" + ((float)(System.currentTimeMillis() - database.startTime.getTime()) / 1000) +
										"\n§eThe file can be found in: §b" + FileManager.Path()
						),database.Data));
					}
					catch(IOException e){ // an error
						setMenu(new ActionDumpOverScreen(new LiteralText("An exception occurred"), new LiteralText(
								"§eAn error occurred while saving.\n§ePress copy data to copy the stacktrace and message.\n\n§c" + e.getMessage()
						),e.getMessage() + "\n" + e.getStackTrace()));
						LOGGER.error(e.getMessage());
					}
					db = null;
				}
			}
			else { // for starting the actiondump
				if(message.equals("{")){
					GetActionDump.MC.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("ActionDump started."), Util.NIL_UUID);
					GetActionDump.LOGGER.info("Actiondump seems to have started.");
					GetActionDump.db = new database();
					database.addData(message);
					ci.cancel();
				}
			}
		}
	}
}