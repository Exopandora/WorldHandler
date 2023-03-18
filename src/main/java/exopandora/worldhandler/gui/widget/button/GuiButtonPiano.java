package exopandora.worldhandler.gui.widget.button;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class GuiButtonPiano extends GuiButtonBase
{
	private static final ResourceLocation NOTE = new ResourceLocation(Main.MODID, "textures/misc/note.png");
	private final Type type;
	private final SoundEvent sound;
	private final float pitch;
	
	public GuiButtonPiano(int x, int y, int width, int height, Component buttonText, SoundEvent sound, float pitch, Type type, ActionHandler actionHandler)
	{
		super(x, y, width, height, buttonText, actionHandler);
		this.sound = sound;
		this.pitch = pitch;
		this.type = type;
	}
	
	@Override
	public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		switch(this.type)
		{
			case LEFT:
				this.isHovered = this.isHoveringLeft(mouseX, mouseY);
				break;
			case NORMAL:
				this.isHovered = this.isHoveringNormal(mouseX, mouseY);
				break;
			case RIGHT:
				this.isHovered = this.isHoveringRight(mouseX, mouseY);
				break;
			case BLACK:
				this.isHovered = this.isHoveringBlack(mouseX, mouseY);
				break;
			default:
				break;
		}
		
		int hovered = this.getTextureY();
		
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, Config.getSkin().getButtonAlpha());
		RenderSystem.setShaderTexture(0, NOTE);
		
		switch(this.type)
		{
			case LEFT:
			case NORMAL:
			case RIGHT:
				this.drawWhiteKey(poseStack, hovered);
				break;
			case BLACK:
				this.drawBlackKey(poseStack, hovered);
				break;
			default:
				break;
		}
	}
	
	protected void drawWhiteKey(PoseStack poseStack, int hoverstate)
	{
		int textColor = this.getFGColor();
		Font font = Minecraft.getInstance().font;
		
		GuiComponent.blit(poseStack, this.getX(), this.getY(), 25 + hoverstate * 15 - 15, 0, 15, 92);
		font.draw(poseStack, this.getMessage(), (float) (this.getX() + this.width / 2 - font.width(this.getMessage()) / 2), (float) (this.getY() + (this.height - 8) / 2 + 36), textColor);
	}
	
	protected void drawBlackKey(PoseStack poseStack, int hoverstate)
	{
		GuiComponent.blit(poseStack, this.getX(), this.getY(), 55 - hoverstate * 9 + 18, 0, 9, 58);
	}
	
	@Override
	public int getFGColor()
	{
		int textColor = 0x000000;
		
		if(!this.active)
		{
			textColor = 0xA0A0A0;
		}
		else if(this.isHoveredOrFocused())
		{
			textColor = 0x8B8B8B;
		}
		
		return textColor;
	}
	
	@Override
	public void playDownSound(SoundManager soundManager)
	{
		if(this.sound != null)
		{
			soundManager.play(SimpleSoundInstance.forUI(this.sound, this.pitch));
		}
	}
	
	private boolean isHoveringBlack(double mouseX, double mouseY)
	{
		return mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;
	}
	
	private boolean isHoveringLeft(double mouseX, double mouseY)
	{
		return (mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + 10 && mouseY < this.getY() + 60) || (mouseX >= this.getX() && mouseY >= this.getY() + 58 && mouseX < this.getX() + 14 && mouseY < this.getY() + 93);
	}
	
	private boolean isHoveringNormal(double mouseX, double mouseY)
	{
		return (mouseX >= this.getX() + 4 && mouseY >= this.getY() && mouseX < this.getX() + 10 && mouseY < this.getY() + 60) || (mouseX >= this.getX() && mouseY >= this.getY() + 58 && mouseX < this.getX() + 14 && mouseY < this.getY() + 93);
	}
	
	private boolean isHoveringRight(double mouseX, double mouseY)
	{
		return (mouseX >= this.getX() + 4 && mouseY >= this.getY() && mouseX < this.getX() + 14 && mouseY < this.getY() + 60) || (mouseX >= this.getX() && mouseY >= this.getY() + 58 && mouseX < this.getX() + 14 && mouseY < this.getY() + 93);
	}
	
	@Override
	public boolean isMouseOver(double mouseX, double mouseY)
	{
		switch(this.type)
		{
			case LEFT:
				return this.active && this.visible && this.isHoveringLeft(mouseX, mouseY);
			case NORMAL:
				return this.active && this.visible && this.isHoveringNormal(mouseX, mouseY);
			case RIGHT:
				return this.active && this.visible && this.isHoveringRight(mouseX, mouseY);
			case BLACK:
				return this.active && this.visible && this.isHoveringBlack(mouseX, mouseY);
			default:
				return false;
		}
	}
	
	@Override
	protected boolean clicked(double mouseX, double mouseY)
	{
		return this.isMouseOver(mouseX, mouseY);
	}
	
	public static enum Type
	{
		LEFT,
		NORMAL,
		RIGHT,
		BLACK;
	}
}
