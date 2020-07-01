package exopandora.worldhandler.gui.button;

import java.util.Objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.util.ILogic;
import exopandora.worldhandler.util.RenderUtils;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiSlider extends GuiButtonBase
{
	private final Persistence persistence;
	private final ILogicSlider logic;
	private final Container container;
	
	public GuiSlider(int x, int y, int widthIn, int heightIn, double min, double max, double start, Container container, ILogicSlider logic)
	{
		super(x, y, widthIn, heightIn, StringTextComponent.field_240750_d_, null);
		this.logic = Objects.requireNonNull(logic);
		this.container = Objects.requireNonNull(container);
		this.persistence = this.container.getContent().getPersistence(this.logic.getId(), () -> new Persistence(min, max, min == max ? 0.0 : ((start - min) / (max - min))));
		this.persistence.validate(min, max);
		this.logic.onChangeSliderValue(this.persistence.getValueInt());
		this.updateDisplayString();
	}
	
	@Override
	protected void func_230441_a_(MatrixStack matrix, Minecraft minecraft, int mouseX, int mouseY) //renderBg
	{
		super.func_230441_a_(matrix, minecraft, mouseX, mouseY); //renderBg
		
		int hovered = super.func_230989_a_(this.func_230449_g_());
		int textureOffset = (Config.getSkin().getTextureType().equals("resourcepack") ? 46 : 0) + hovered * 20;
		
		RenderSystem.enableBlend();
		RenderUtils.color(Config.getSkin().getButtonRedF(), Config.getSkin().getButtonGreenF(), Config.getSkin().getButtonBlueF(), Config.getSkin().getButtonAlphaF());
		
		this.func_238474_b_(matrix, this.field_230690_l_ + (int) (this.persistence.getValue() * (float) (this.field_230688_j_ - 8)), this.field_230691_m_, 0, textureOffset, 4, 20); //blit
		this.func_238474_b_(matrix, this.field_230690_l_ + (int) (this.persistence.getValue() * (float) (this.field_230688_j_ - 8)) + 4, this.field_230691_m_, 196, textureOffset, 4, 20); //blit
		
		RenderSystem.disableBlend();
	}
	
	@Override
	public void func_230982_a_(double mouseX, double mouseY) //onClick
	{
		this.updateSlider(mouseX);
	}
	
	@Override
	protected void func_230983_a_(double mouseX, double mouseY, double deltaX, double deltaY) //onDrag
	{
		this.updateSlider(mouseX);
		super.func_230983_a_(mouseX, mouseY, deltaX, deltaY); //onDrag
	}
	
	protected void updateSlider(double mouseX)
	{
		this.persistence.setValue((mouseX - (this.field_230690_l_ + 4)) / (float) (this.field_230688_j_ - 8));
		
		if(this.persistence.getValue() < 0.0F)
		{
			this.persistence.setValue(0.0F);
		}
		
		if(this.persistence.getValue() > 1.0F)
		{
			this.persistence.setValue(1.0F);
		}
		
		this.updateDisplayString();
		this.logic.onChangeSliderValue(this.persistence.getValueInt());
	}
	
	@Override
	protected int func_230989_a_(boolean mouseOver) //getYImage
	{
		return 0;
	}
	
	private void updateDisplayString()
	{
		int value = this.persistence.getValueInt();
		IFormattableTextComponent suffix = this.logic.formatValue(value).func_230529_a_(this.logic.formatSuffix(value));
		FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
		IFormattableTextComponent text = TextUtils.stripText(this.logic.formatPrefix(value), this.field_230688_j_ - fontRenderer.func_238414_a_(suffix), fontRenderer).func_230529_a_(suffix);
		this.func_238482_a_(text); //setMessage
	}
	
	@OnlyIn(Dist.CLIENT)
	public static interface ILogicSlider extends ILogic
	{
		IFormattableTextComponent formatPrefix(int value);
		IFormattableTextComponent formatSuffix(int value);
		IFormattableTextComponent formatValue(int value);
		
		void onChangeSliderValue(int value);
	}
	
	@OnlyIn(Dist.CLIENT)
	public class Persistence
	{
		private double min;
		private double max;
		private double value;
		
		private Persistence(double min, double max)
		{
			this.min = min;
			this.max = max;
		}
		
		public Persistence(double min, double max, double value)
		{
			this(min, max);
			this.value = value;
		}
		
		public double getMin()
		{
			return this.min;
		}
		
		public double getMax()
		{
			return this.max;
		}
		
		public double getValue()
		{
			return this.value;
		}
		
		public void setValue(double value)
		{
			this.value = value;
		}
		
		public int getValueInt()
		{
			return (int) Math.round(this.min + (this.max - this.min) * this.value);
		}
		
		public void setValueInt(int value)
		{
			this.value = this.intToValue(value);
		}
		
		public void validate(double min, double max)
		{
			if(this.getMin() != min || this.getMax() != max)
			{
				this.min = min;
				this.max = max;
			}
		}
		
		private double intToValue(int value)
		{
			if(this.min == this.max)
			{
				return 0;
			}
			
			return (value - this.min) / (this.max - this.min);
		}
	}
}
