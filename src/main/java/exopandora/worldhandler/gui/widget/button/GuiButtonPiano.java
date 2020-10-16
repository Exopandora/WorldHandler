package exopandora.worldhandler.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiButtonPiano extends GuiButtonBase
{
	private static final ResourceLocation NOTE = new ResourceLocation(Main.MODID, "textures/misc/note.png");
	private final Type type;
	private final SoundEvent sound;
	private final float pitch;
	
	public GuiButtonPiano(int x, int y, int widthIn, int heightIn, ITextComponent buttonText, SoundEvent sound, float pitch, Type type, ActionHandler actionHandler)
	{
		super(x, y, widthIn, heightIn, buttonText, actionHandler);
		this.sound = sound;
		this.pitch = pitch;
		this.type = type;
	}
	
	@Override
	public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
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
		
		int hovered = this.getYImage(this.isHovered());
		RenderUtils.color(1.0F, 1.0F, 1.0F, Config.getSkin().getButtonAlphaF());
		Minecraft.getInstance().getTextureManager().bindTexture(NOTE);
		
		switch(this.type)
		{
			case LEFT:
			case NORMAL:
			case RIGHT:
				this.drawWhiteKey(matrix, hovered);
				break;
			case BLACK:
				this.drawBlackKey(matrix, hovered);
				break;
			default:
				break;
		}
	}
	
	protected void drawWhiteKey(MatrixStack matrix, int hoverstate)
	{
		int textColor = this.getFGColor();
		FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
		
		this.blit(matrix, this.x, this.y, 25 + hoverstate * 15 - 15, 0, 15, 92);
		fontRenderer.func_243248_b(matrix, this.getMessage(), (float) (this.x + this.width / 2 - fontRenderer.func_238414_a_(this.getMessage()) / 2), (float) (this.y + (this.height - 8) / 2 + 36), textColor); //drawString
	}
	
	protected void drawBlackKey(MatrixStack matrix, int hoverstate)
	{
		this.blit(matrix, this.x, this.y, 55 - hoverstate * 9 + 18, 0, 9, 58);
	}
	
	@Override
	public int getFGColor()
	{
		int textColor = 0x000000;
		
		if(!this.active)
		{
			textColor = 0xA0A0A0;
		}
		else if(this.isHovered())
		{
			textColor = 0x8B8B8B;
		}
		
		return textColor;
	}
	
	@Override
	public void playDownSound(SoundHandler soundHandler)
	{
		soundHandler.play(SimpleSound.master(this.sound, this.pitch));
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
	
	@OnlyIn(Dist.CLIENT)
	public static enum Type
	{
		LEFT,
		NORMAL,
		RIGHT,
		BLACK;
	}
}
