package dev.dfonline.getactiondump.mixin;

import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisconnectS2CPacket.class)
public class Disconnect {
    @Inject(method = "getReason", at = @At("RETURN"))
    private void getReason(Text reason, CallbackInfo ci){
    }
}
