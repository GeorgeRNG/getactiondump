package dev.dfonline.getactiondump.mixin;

import dev.dfonline.getactiondump.GetActionDump;
import dev.dfonline.getactiondump.util.ChatType;
import dev.dfonline.getactiondump.util.ChatUtil;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.KeepAliveC2SPacket;
import net.minecraft.network.packet.s2c.play.KeepAliveS2CPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class handlePackets {

    @Inject(method = "onKeepAlive", at = @At("HEAD"))
    private void reportKeepAlive(KeepAliveS2CPacket packet, CallbackInfo ci){
        if(GetActionDump.reportKeepAlives){
            assert GetActionDump.MC.player != null;
            ChatUtil.sendMessage("A keepAlive (id " + packet.getId() + ") was just sent. Now might be ideal to do /actiondump.", ChatType.INFO);
        }
    }

    @Inject(method = "sendPacket", at = @At("HEAD"), cancellable = true)
    private void sendPacket(Packet packet, CallbackInfo ci){
        if(GetActionDump.db != null){
            if(!(packet instanceof KeepAliveC2SPacket)) {
                ci.cancel();
            }
        }
    }
}
