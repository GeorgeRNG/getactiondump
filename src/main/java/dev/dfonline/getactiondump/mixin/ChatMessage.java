package dev.dfonline.getactiondump.mixin;

import dev.dfonline.getactiondump.database;
import dev.dfonline.getactiondump.GetActionDump;


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

@Mixin(ClientPlayNetworkHandler.class)
public class ChatMessage {

	@Inject(method = "onGameMessage", at = @At("HEAD"), cancellable = true)
	private void OnChatMessage(GameMessageS2CPacket packet, CallbackInfo ci){
		String message = packet.getMessage().getString();
		if(packet.getType() == MessageType.SYSTEM){
			if(GetActionDump.db != null){ // while the actiondump is active
				if(message.startsWith(" ") || message.equals("}")){ // if it's anything related to the database, add it.
					ci.cancel();
					database.addData(message);
					GetActionDump.overlayText = ("§dActionDump Info:\n §0§l| §eLines: §b" + database.Lines + "\n §0§l| §eSize: §b" + database.Length + "\n §0§l| §eTime: §b" + ((float)(System.currentTimeMillis() - database.startTime.getTime()) / 1000)).split("\n");
				}
				if(message.equals("}")){ // to end the actiondump scanning, previous if block should have ran.
					GetActionDump.MC.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("ActionDump over. It took " + ((float)(System.currentTimeMillis() - database.startTime.getTime()) / 1000) + " seconds."), Util.NIL_UUID);
					try{
						database.save();
					}
					catch(IOException e){ // an error
						GetActionDump.MC.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("§cAn internal exception occurred. Check the console."), Util.NIL_UUID);
						GetActionDump.LOGGER.error(e.getMessage());
					}
					GetActionDump.db = null;
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