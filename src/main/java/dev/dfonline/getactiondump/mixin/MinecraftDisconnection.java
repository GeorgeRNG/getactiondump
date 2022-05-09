package dev.dfonline.getactiondump.mixin;

import dev.dfonline.getactiondump.GetActionDump;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftDisconnection {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(CallbackInfo ci){
        if(GetActionDump.DisconnectMenu != null){
            ci.cancel();
            assert GetActionDump.MC.world != null;
            GetActionDump.MC.world.disconnect();
            GetActionDump.MC.disconnect(GetActionDump.DisconnectMenu);
            GetActionDump.DisconnectMenu = null;
        }
    }
}
