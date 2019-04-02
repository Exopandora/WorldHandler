package exopandora.worldhandler.gui.button;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiButtonPiano extends GuiButtonBase
{
	private static final ResourceLocation NOTE = new ResourceLocation(Main.MODID, "textures/misc/note.png");
	private final Type type;
	private final SoundEvent sound;
	private final float pitch;
	
	public GuiButtonPiano(int x, int y, int widthIn, int heightIn, String buttonText, SoundEvent sound, float pitch, Type type, ActionHandler actionHandler)
	{
		this(0, x, y, widthIn, heightIn, buttonText, sound, pitch, type, actionHandler);
	}
	
	public GuiButtonPiano(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, SoundEvent sound, float pitch, Type type, ActionHandler actionHandler)
	{
		super(buttonId, x, y, widthIn, heightIn, buttonText, actionHandler);
		this.sound = sound;
		this.pitch = pitch;
		this.type = type;
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks)
	{
		if(this.visible)
		{
			switch(this.type)
			{
				case LEFT:
					this.hovered = this.isHoveringLeft(mouseX, mouseY);
					break;
				case NORMAL:
					this.hovered = this.isHoveringNormal(mouseX, mouseY);
					break;
				case RIGHT:
					this.hovered = this.isHoveringRight(mouseX, mouseY);
					break;
				case BLACK:
					this.hovered = this.isHoveringBlack(mouseX, mouseY);
					break;
				default:
					break;
			}
			
			int hoverstate = this.getHoverState(this.hovered);
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, Config.getSkin().getButtonAlphaF());
			Minecraft.getInstance().getTextureManager().bindTexture(NOTE);
			
			switch(this.type)
			{
				case LEFT:
				case NORMAL:
				case RIGHT:
					this.drawWhiteKey(hoverstate);
					break;
				case BLACK:
					this.drawBlackKey(hoverstate);
					break;
				default:
					break;
			}
		}
	}
	
	protected void drawWhiteKey(int hoverstate)
	{
		int textColor = this.getTextColor();
		FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
		
		this.drawTexturedModalRect(this.x, this.y, 25 + hoverstate * 15 - 15, 0, 15, 92);
		fontRenderer.drawString(this.displayString, this.x + this.width / 2 - fontRenderer.getStringWidth(this.displayString) / 2, this.y + (this.height - 8) / 2 + 36, textColor);
	}
	
	protected void drawBlackKey(int hoverstate)
	{
		this.drawTexturedModalRect(this.x, this.y, 55 + hoverstate * -9 + 18, 0, 9, 58);
	}
	
	protected int getTextColor()
	{
		int textColor = 0x000000;
		
		if(!this.enabled)
		{
			textColor = 0xA0A0A0;
		}
		else if(this.hovered)
		{
			textColor = 0x8B8B8B;
		}
        
        return textColor;
	}
	
	@Override
	public void playPressSound(SoundHandler soundHandler)
	{
		soundHandler.play(SimpleSound.getMasterRecord(this.sound, this.pitch));
	}
	
	private boolean isHoveringBlack(double mouseX, double mouseY)
	{
		return mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
	}
	
	private boolean isHoveringLeft(double mouseX, double mouseY)
	{
		return (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + 10 && mouseY < this.y + 60) || (mouseX >= this.x && mouseY >= this.y + 58 && mouseX < this.x + 14 && mouseY < this.y + 93);
	}
	
	private boolean isHoveringNormal(double mouseX, double mouseY)
	{
		return (mouseX >= this.x + 4 && mouseY >= this.y && mouseX < this.x + 10 && mouseY < this.y + 60) || (mouseX >= this.x && mouseY >= this.y + 58 && mouseX < this.x + 14 && mouseY < this.y + 93);
	}
	
	private boolean isHoveringRight(double mouseX, double mouseY)
	{
		return (mouseX >= this.x + 4 && mouseY >= this.y && mouseX < this.x + 14 && mouseY < this.y + 60) || (mouseX >= this.x && mouseY >= this.y + 58 && mouseX < this.x + 14 && mouseY < this.y + 93);
	}
	
	@Override
	protected boolean isPressable(double mouseX, double mouseY)
	{
		switch(this.type)
		{
			case LEFT:
				return this.enabled && this.visible && this.isHoveringLeft(mouseX, mouseY);
			case NORMAL:
				return this.enabled && this.visible && this.isHoveringNormal(mouseX, mouseY);
			case RIGHT:
				return this.enabled && this.visible && this.isHoveringRight(mouseX, mouseY);
			case BLACK:
				return this.enabled && this.visible && this.isHoveringBlack(mouseX, mouseY);
			default:
				return false;
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static enum Type
	{
		LEFT,
		NORMAL,
		RIGHT,
		BLACK;
	}
}
