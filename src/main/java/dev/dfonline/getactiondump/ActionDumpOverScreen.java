package dev.dfonline.getactiondump;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.MultilineText;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.Clipboard;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class ActionDumpOverScreen extends Screen {
    private final Text info;
    private MultilineText infoFormatted;
    private final Screen parent = new TitleScreen();
    private int reasonHeight;
    private final String clipboard;

    public ActionDumpOverScreen(Text title, Text info, String clipboard){
        super(title);
        super.init();
        this.info = info;
        this.infoFormatted = MultilineText.EMPTY;
        this.clipboard = clipboard;
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    protected void init() {
        this.infoFormatted = MultilineText.create(this.textRenderer, this.info, this.width - 50);
        int infoCount = this.infoFormatted.count();
        Objects.requireNonNull(this.textRenderer);
        this.reasonHeight = infoCount * 9;
        int GoodX = this.width / 2 - 100;
        int GoodY = this.height / 2 + this.reasonHeight / 2;
        Objects.requireNonNull(this.textRenderer);
        this.addDrawableChild(new ButtonWidget(GoodX, Math.min(GoodY + 9, this.height - 30), 200, 20, new LiteralText("Main Menu"), (button) -> {
            assert this.client != null;
            this.client.setScreen(this.parent);
            GetActionDump.DisconnectMenu = null;
        }));
        this.addDrawableChild(new ButtonWidget(GoodX, Math.min(GoodY + 9, this.height - 30) + ((20)), 200, 20, new LiteralText("Copy Data"), (button -> new Clipboard().setClipboard(0, clipboard))));
        this.addDrawableChild(new ButtonWidget(GoodX, Math.min(GoodY + 9, this.height - 30) + ((20 * 2)), 200, 20, new LiteralText("Copy Path"), (button -> new Clipboard().setClipboard(0, FileManager.Path().toString()))));
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        TextRenderer textRenderer = this.textRenderer;
        Text title = this.title;
        int horCenter = this.width / 2;
        int verCenter = this.height / 2 - this.reasonHeight / 2;
        Objects.requireNonNull(this.textRenderer);
        drawCenteredText(matrices, textRenderer, title, horCenter, verCenter - 9 * 2, 11184810);
        this.infoFormatted.drawCenterWithShadow(matrices, this.width / 2, this.height / 2 - this.reasonHeight / 2);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
