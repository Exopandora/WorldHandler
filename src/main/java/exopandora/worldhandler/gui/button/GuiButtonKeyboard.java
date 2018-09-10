package exopandora.worldhandler.gui.button;

import org.lwjgl.input.Mouse;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.config.ConfigSkin;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiButtonKeyboard extends GuiButtonWorldHandler
{
	private static final ResourceLocation NOTE = new ResourceLocation(Main.MODID, "textures/misc/note.png");
	private final Orientation orientation;
	private final float pitch;
	
	private boolean lastMousePressed;
	
	private final BlockPos pos;
	private final SoundEvent sound;
	private final Content content;
	private final Container container;
	
	public GuiButtonKeyboard(int id, int x, int y, int width, int height, String displayString, Orientation orientation, float pitch, Container container, Content content, BlockPos pos, SoundEvent sound)
	{
		super(id, x, y, width, height, displayString);
		this.orientation = orientation;
		this.pitch = pitch;
		this.pos = pos;
		this.sound = sound;
		this.content = content;
		this.container = container;
	}
	
	@Override
	public void drawButton(Minecraft minecraft, int mouseX, int mouseY, float partialTicks)
	{
		if(this.visible)
		{
			FontRenderer fontRenderer = minecraft.fontRenderer;
			GlStateManager.color(1.0F, 1.0F, 1.0F, (float) ConfigSkin.getButtonAlpha() / 255);
			minecraft.renderEngine.bindTexture(NOTE);
			
			switch(this.orientation)
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
			
			switch(this.orientation)
			{
				case BLACK:
					this.drawTexturedModalRect(this.x, this.y, 55 + hoverstate * -9 + 18, 0, 9, 58);
					break;
				case LEFT:
				case NORMAL:
				case RIGHT:
				default:
					int textColor = 0x000000;
					
					if(!this.enabled)
					{
						textColor = 0xA0A0A0;
					}
					else if(this.hovered)
					{
						textColor = 0x8B8B8B;
					}
					
					this.drawTexturedModalRect(this.x, this.y, 25 + hoverstate * 15 - 15, 0, 15, 92);
					fontRenderer.drawString(this.displayString, this.x + this.width / 2 - fontRenderer.getStringWidth(this.displayString) / 2, this.y + (this.height - 8) / 2 + 36, textColor);
					break;
			}
		}
		
		this.mouseDragged(minecraft, mouseX, mouseY);
	}
	
	private boolean isHoveringBlack(int mouseX, int mouseY)
	{
		return mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
	}
	
	private boolean isHoveringLeft(int mouseX, int mouseY)
	{
		return (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + 10 && mouseY < this.y + 60) || (mouseX >= this.x && mouseY >= this.y + 58 && mouseX < this.x + 14 && mouseY < this.y + 93);
	}
	
	private boolean isHoveringNormal(int mouseX, int mouseY)
	{
		return (mouseX >= this.x + 4 && mouseY >= this.y && mouseX < this.x + 10 && mouseY < this.y + 60) || (mouseX >= this.x && mouseY >= this.y + 58 && mouseX < this.x + 14 && mouseY < this.y + 93);
	}
	
	private boolean isHoveringRight(int mouseX, int mouseY)
	{
		return (mouseX >= this.x + 4 && mouseY >= this.y && mouseX < this.x + 14 && mouseY < this.y + 60) || (mouseX >= this.x && mouseY >= this.y + 58 && mouseX < this.x + 14 && mouseY < this.y + 93);
	}
	
	@Override
	public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY)
	{
		switch(this.orientation)
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

	@Override
	protected void mouseDragged(Minecraft minecraft, int mouseX, int mouseY)
	{
		if(this.visible)
		{
			if(this.lastMousePressed != mousePressed(minecraft, mouseX, mouseY))
			{
				if(mousePressed(minecraft, mouseX, mouseY) && Mouse.isButtonDown(0))
				{
					this.playPressSound(minecraft.getSoundHandler());
					
					try
					{
						this.content.actionPerformed(this.container, this);
					}
					catch(Exception e)
					{
						WorldHandler.throwError(e);
					}
				}
				
				this.lastMousePressed = mousePressed(minecraft, mouseX, mouseY);
			}
		}
	}

	@Override
	public void playPressSound(SoundHandler soundHandlerIn)
	{
		soundHandlerIn.playSound(PositionedSoundRecord.getMasterRecord(this.sound, this.pitch));
	}
	
	public static enum Orientation
	{
		LEFT,
		NORMAL,
		RIGHT,
		BLACK;
	}
}
