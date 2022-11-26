package dev.dfonline.getactiondump.mixin;

import dev.dfonline.getactiondump.GetActionDump;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.OpenWrittenBookS2CPacket;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

/**
 * @author @RedCommand-dev
 */
@Mixin(ClientPlayNetworkHandler.class)
public class OpenWrittenBook {
    @Inject(method = "onOpenWrittenBook", at = @At("HEAD"))
    private void onOpenWrittenBook(OpenWrittenBookS2CPacket packet, CallbackInfo ci){
        // How the reference book works is when you click on an option in the menu,
        // it sets the reference book's content to the book you are currently
        // holding and then resetting it back to an empty book.
        //
        // A workaround is listening to the onOpenWrittenBook packet and then
        // getting the current held book's NBT right before it gets reset.
        //
        // After that I save the NBT to a file named "book.json" in the
        // ReferenceBookScrape directory where you can later do whatever you want.
        //
        // Also, note that the event fires twice for first opening the real
        // reference book and another time for opening the 'fake' book with the
        // stuff we want, so the first event is skipped.
        String nbt = GetActionDump.MC.player.getMainHandStack().getNbt().toString(); // Get the book's NBT

        // Write the NBT to a file
        Path f = GetActionDump.MC.runDirectory.toPath().resolve("GetActionDump").resolve("book.txt"); // .minecraft/GetActionDump/book.txt
        try {
            Files.writeString(f, nbt);
        } catch (IOException e) {
            e.printStackTrace(); // Obligatory try catch
            GetActionDump.MC.player.sendMessage(Text.literal("§cError while writing file"));
        }
        // Message prompting to get the book
        GetActionDump.MC.player.sendMessage(Text.literal("§a§l» §fReceived OpenWrittenBook packet! Click to get book! §7§o(File with nbt was saved)")
                .setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dfgive written_book" + nbt))
                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("§7§oClick to get book")))), false);

    }
}