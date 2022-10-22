package dev.dfonline.getactiondump.mixin;

import dev.dfonline.getactiondump.GetActionDump;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class onExit {
    @Inject(method = "disconnect", at = @At("HEAD"))
    public void disconnect(CallbackInfo ci){
        if(GetActionDump.DisconnectMenu == null && GetActionDump.db != null){
            GetActionDump.db.close();
        }
    }
}
