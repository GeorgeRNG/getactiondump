package dev.dfonline.getactiondump.mixin;

import dev.dfonline.getactiondump.GetActionDump;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.gui.hud.InGameHud.class)
public class InGameHud {
    private static final TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;


    @Inject(method = "render", at = @At("RETURN"))
    public void onRender (MatrixStack matrices, float tickDelta, CallbackInfo info) {
        int index = 0;
        for (String text : GetActionDump.overlayText){
            textRenderer.draw(matrices, text, 30, 50 + (index * 9), -1);
            index++;
        }
    }
}