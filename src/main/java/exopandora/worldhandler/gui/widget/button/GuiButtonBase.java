package exopandora.worldhandler.gui.widget.button;

import com.mojang.blaze3d.systems.RenderSystem;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class GuiButtonBase extends AbstractButton
{
	protected static final WidgetSprites VANILLA_BUTTON_SPRITES = new WidgetSprites
	(
		new ResourceLocation(Main.MODID, "textures/skins/vanilla/button.png"),
		new ResourceLocation(Main.MODID, "textures/skins/vanilla/button_disabled.png"),
		new ResourceLocation(Main.MODID, "textures/skins/vanilla/button_highlighted.png")
	);
	
	private final ActionHandler actionHandler;
	
	public GuiButtonBase(int x, int y, int width, int height, String translationKey, ActionHandler actionHandler)
	{
		this(x, y, width, height, Component.translatable(translationKey), actionHandler);
	}
	
	public GuiButtonBase(int x, int y, int width, int height, Component buttonText, ActionHandler actionHandler)
	{
		super(x, y, width, height, buttonText);
		this.actionHandler = actionHandler;
	}
	
	@Override
	public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderString(guiGraphics, Minecraft.getInstance().font, this.getFGColor() | Mth.ceil(this.alpha * 255.0F) << 24);
	}
	
	protected void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
	{
		RenderSystem.enableBlend();
		RenderUtils.colorDefaultButton(guiGraphics);
		guiGraphics.blitSprite(SPRITES.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight());
		RenderUtils.resetColor(guiGraphics);
		RenderSystem.disableBlend();
	}
	
	@Override
	public void onPress()
	{
		ActionHelper.tryRun(this.actionHandler);
	}
	
	@Override
	protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput)
	{
		this.defaultButtonNarrationText(narrationElementOutput);
	}
}
