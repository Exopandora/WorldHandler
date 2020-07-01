package exopandora.worldhandler.gui.button;

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
	public void func_230431_b_(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) //renderButton
	{
		switch(this.type)
		{
			case LEFT:
				this.field_230692_n_ = this.isHoveringLeft(mouseX, mouseY);
				break;
			case NORMAL:
				this.field_230692_n_ = this.isHoveringNormal(mouseX, mouseY);
				break;
			case RIGHT:
				this.field_230692_n_ = this.isHoveringRight(mouseX, mouseY);
				break;
			case BLACK:
				this.field_230692_n_ = this.isHoveringBlack(mouseX, mouseY);
				break;
			default:
				break;
		}
		
		int hovered = this.func_230989_a_(this.func_230449_g_());
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
		
		this.func_238474_b_(matrix, this.field_230690_l_, this.field_230691_m_, 25 + hoverstate * 15 - 15, 0, 15, 92);
		fontRenderer.func_238422_b_(matrix, this.func_230458_i_(), (float) (this.field_230690_l_ + this.field_230688_j_ / 2 - fontRenderer.func_238414_a_(this.func_230458_i_()) / 2), (float) (this.field_230691_m_ + (this.field_230689_k_ - 8) / 2 + 36), textColor); //drawString
	}
	
	protected void drawBlackKey(MatrixStack matrix, int hoverstate)
	{
		this.func_238474_b_(matrix, this.field_230690_l_, this.field_230691_m_, 55 + hoverstate * -9 + 18, 0, 9, 58); //blit
	}
	
	@Override
	public int getFGColor()
	{
		int textColor = 0x000000;
		
		if(!this.field_230693_o_)
		{
			textColor = 0xA0A0A0;
		}
		else if (this.func_230449_g_())
		{
			textColor = 0x8B8B8B;
		}
		
		return textColor;
	}
	
	@Override
	public void func_230988_a_(SoundHandler soundHandler) //playDownSound
	{
		soundHandler.play(SimpleSound.master(this.sound, this.pitch));
	}
	
	private boolean isHoveringBlack(double mouseX, double mouseY)
	{
		return mouseX >= this.field_230690_l_ && mouseY >= this.field_230691_m_ && mouseX < this.field_230690_l_ + this.field_230688_j_ && mouseY < this.field_230691_m_ + this.field_230689_k_;
	}
	
	private boolean isHoveringLeft(double mouseX, double mouseY)
	{
		return (mouseX >= this.field_230690_l_ && mouseY >= this.field_230691_m_ && mouseX < this.field_230690_l_ + 10 && mouseY < this.field_230691_m_ + 60) || (mouseX >= this.field_230690_l_ && mouseY >= this.field_230691_m_ + 58 && mouseX < this.field_230690_l_ + 14 && mouseY < this.field_230691_m_ + 93);
	}
	
	private boolean isHoveringNormal(double mouseX, double mouseY)
	{
		return (mouseX >= this.field_230690_l_ + 4 && mouseY >= this.field_230691_m_ && mouseX < this.field_230690_l_ + 10 && mouseY < this.field_230691_m_ + 60) || (mouseX >= this.field_230690_l_ && mouseY >= this.field_230691_m_ + 58 && mouseX < this.field_230690_l_ + 14 && mouseY < this.field_230691_m_ + 93);
	}
	
	private boolean isHoveringRight(double mouseX, double mouseY)
	{
		return (mouseX >= this.field_230690_l_ + 4 && mouseY >= this.field_230691_m_ && mouseX < this.field_230690_l_ + 14 && mouseY < this.field_230691_m_ + 60) || (mouseX >= this.field_230690_l_ && mouseY >= this.field_230691_m_ + 58 && mouseX < this.field_230690_l_ + 14 && mouseY < this.field_230691_m_ + 93);
	}
	
	@Override
	public boolean func_231047_b_(double mouseX, double mouseY) //isMouseOver
	{
		switch(this.type)
		{
			case LEFT:
				return this.field_230693_o_ && this.field_230694_p_ && this.isHoveringLeft(mouseX, mouseY);
			case NORMAL:
				return this.field_230693_o_ && this.field_230694_p_ && this.isHoveringNormal(mouseX, mouseY);
			case RIGHT:
				return this.field_230693_o_ && this.field_230694_p_ && this.isHoveringRight(mouseX, mouseY);
			case BLACK:
				return this.field_230693_o_ && this.field_230694_p_ && this.isHoveringBlack(mouseX, mouseY);
			default:
				return false;
		}
	}
	
	@Override
	protected boolean func_230992_c_(double mouseX, double mouseY) //clicked
	{
		return this.func_231047_b_(mouseX, mouseY);
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
