package exopandora.worldhandler.config;

import java.util.Arrays;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

@OnlyIn(Dist.CLIENT)
public class ConfigCategorySkin
{
	private EnumIconSize iconSize;
	private int labelColor;
	private int headlineColor;
	private int backgroundRed;
	private int backgroundGreen;
	private int backgroundBlue;
	private int backgroundAlpha;
	private int buttonRed;
	private int buttonGreen;
	private int buttonBlue;
	private int buttonAlpha;
	private String type;
	private boolean sharpEdges;
	private boolean drawBackground;
	
	private final ConfigValue<EnumIconSize> valueIconSize;
	private final IntValue valueLabelColor;
	private final IntValue valueHeadlineColor;
	private final IntValue valueBackgroundRed;
	private final IntValue valueBackgroundGreen;
	private final IntValue valueBackgroundBlue;
	private final IntValue valueBackgroundAlpha;
	private final IntValue valueButtonRed;
	private final IntValue valueButtonGreen;
	private final IntValue valueButtonBlue;
	private final IntValue valueButtonAlpha;
	private final ConfigValue<String> valueType;
	private final BooleanValue valueSharpEdges;
	private final BooleanValue valueDrawBackground;
	
	public ConfigCategorySkin(ForgeConfigSpec.Builder builder)
	{
		builder.push("skin");
		
		this.valueIconSize = builder
				.translation("gui.worldhandler.config.skin.icon_size")
				.comment("Size of the icons")
				.defineEnum("icon_size", EnumIconSize.x16, EnumIconSize.values());
		this.valueLabelColor = builder
				.translation("gui.worldhandler.config.skin.label_color")
				.comment("Label color")
				.defineInRange("label_color", 0x1F1F1F, 0x80000000, 0x7FFFFFFF);
		this.valueHeadlineColor = builder
				.translation("gui.worldhandler.config.skin.headline_color")
				.comment("Headline color")
				.defineInRange("headline_color", 0x4F4F4F, 0x80000000, 0x7FFFFFFF);
		
		this.valueBackgroundRed = builder
				.translation("gui.worldhandler.config.skin.background_red")
				.comment("Background red")
				.defineInRange("background_red", 255, 0, 255);
		this.valueBackgroundGreen = builder
				.translation("gui.worldhandler.config.skin.background_green")
				.comment("Background green")
				.defineInRange("background_green", 255, 0, 255);
		this.valueBackgroundBlue = builder
				.translation("gui.worldhandler.config.skin.background_blue")
				.comment("Background blue")
				.defineInRange("background_blue", 255, 0, 255);
		this.valueBackgroundAlpha = builder
				.translation("gui.worldhandler.config.skin.background_alpha")
				.comment("Background alpha")
				.defineInRange("background_alpha", 255, 0, 255);
		
		this.valueButtonRed = builder
				.translation("gui.worldhandler.config.skin.button_red")
				.comment("Button eed")
				.defineInRange("button_red", 255, 0, 255);
		this.valueButtonGreen = builder
				.translation("gui.worldhandler.config.skin.button_green")
				.comment("Button green")
				.defineInRange("button_green", 255, 0, 255);
		this.valueButtonBlue = builder
				.translation("gui.worldhandler.config.skin.button_blue")
				.comment("Button blue")
				.defineInRange("button_blue", 255, 0, 255);
		this.valueButtonAlpha = builder
				.translation("gui.worldhandler.config.skin.button_alpha")
				.comment("Button alpha")
				.defineInRange("button_alpha", 255, 0, 255);

		this.valueType = builder
				.translation("gui.worldhandler.config.skin.textures")
				.comment("Background texture (resourcepack, vanilla)")
				.defineInList("textures", "resourcepack", Arrays.asList("resourcepack", "vanilla"));
		
		this.valueSharpEdges = builder
				.translation("gui.worldhandler.config.settings.sharp_tab_edges")
				.comment("Whether or not the gui has sharp or smooth tab edges")
				.define("sharp_tab_edges", false);
		this.valueDrawBackground = builder
				.translation("gui.worldhandler.config.settings.draw_background")
				.comment("Whether or not to enable background drawing")
				.define("draw_background", true);
		
		builder.pop();
	}
	
	public void read()
	{
		this.iconSize = this.valueIconSize.get();
		this.labelColor = this.valueLabelColor.get();
		this.headlineColor = this.valueHeadlineColor.get();
		this.backgroundRed = this.valueBackgroundRed.get();
		this.backgroundGreen = this.valueBackgroundGreen.get();
		this.backgroundBlue = this.valueBackgroundBlue.get();
		this.backgroundAlpha = this.valueBackgroundAlpha.get();
		this.buttonRed = this.valueButtonRed.get();
		this.buttonGreen = this.valueButtonGreen.get();
		this.buttonBlue = this.valueButtonBlue.get();
		this.buttonAlpha = this.valueButtonAlpha.get();
		this.type = this.valueType.get();
		this.sharpEdges = this.valueSharpEdges.get();
		this.drawBackground = this.valueDrawBackground.get();
	}
	
	private void write()
	{
		Config.set(this.valueIconSize, this.iconSize);
		Config.set(this.valueLabelColor, this.labelColor);
		Config.set(this.valueHeadlineColor, this.headlineColor);
		Config.set(this.valueBackgroundRed, this.backgroundRed);
		Config.set(this.valueBackgroundGreen, this.backgroundGreen);
		Config.set(this.valueBackgroundBlue, this.backgroundBlue);
		Config.set(this.valueBackgroundAlpha, this.backgroundAlpha);
		Config.set(this.valueButtonRed, this.buttonRed);
		Config.set(this.valueButtonGreen, this.buttonGreen);
		Config.set(this.valueButtonBlue, this.buttonBlue);
		Config.set(this.valueButtonAlpha, this.buttonAlpha);
		Config.set(this.valueType, this.type);
		Config.set(this.valueSharpEdges, this.sharpEdges);
		Config.set(this.valueDrawBackground, this.drawBackground);
	}
	
	public EnumIconSize getIconSize()
	{
		return this.iconSize;
	}
	
	public void setIconSize(EnumIconSize size)
	{
		this.iconSize = size;
		this.write();
	}
	
	public int getLabelColor()
	{
		return this.labelColor;
	}
	
	public void setLabelColor(int color)
	{
		this.labelColor = color;
		this.write();
	}
	
	public int getHeadlineColor()
	{
		return this.headlineColor;
	}
	
	public void setHeadlineColor(int color)
	{
		this.headlineColor = color;
		this.write();
	}
	
	private int getBackgroundRed()
	{
		return this.backgroundRed;
	}
	
	public float getBackgroundRedF()
	{
		return this.getBackgroundRed() / 255F;
	}
	
	public void setBackgroundRed(int red)
	{
		this.backgroundRed = red;
		this.write();
	}
	
	private int getBackgroundGreen()
	{
		return this.backgroundGreen;
	}
	
	public float getBackgroundGreenF()
	{
		return this.getBackgroundGreen() / 255F;
	}
	
	public void setBackgroundGreen(int green)
	{
		this.backgroundGreen = green;
		this.write();
	}
	
	private int getBackgroundBlue()
	{
		return this.backgroundBlue;
	}
	
	public float getBackgroundBlueF()
	{
		return this.getBackgroundBlue() / 255F;
	}
	
	public void setBackgroundBlue(int blue)
	{
		this.backgroundBlue = blue;
		this.write();
	}
	
	private int getButtonRed()
	{
		return this.buttonRed;
	}
	
	public float getButtonRedF()
	{
		return this.getButtonRed() / 255F;
	}
	
	public void setButtonRed(int red)
	{
		this.backgroundRed = red;
		this.write();
	}
	
	private int getButtonGreen()
	{
		return this.buttonGreen;
	}
	
	public float getButtonGreenF()
	{
		return this.getButtonGreen() / 255F;
	}
	
	public void setButtonGreen(int green)
	{
		this.buttonGreen = green;
		this.write();
	}
	
	private int getButtonBlue()
	{
		return this.buttonBlue;
	}
	
	public float getButtonBlueF()
	{
		return this.getButtonBlue() / 255F;
	}
	
	public void setButtonBlue(int blue)
	{
		this.buttonBlue = blue;
		this.write();
	}
	
	public String getTextureType()
	{
		return this.type;
	}
	
	public void setTextureType(String type)
	{
		this.type = type;
		this.write();
	}
	
	public boolean sharpEdges()
	{
		return this.sharpEdges;
	}
	
	public void setSharpEdges(boolean enabled)
	{
		this.sharpEdges = enabled;
		this.write();
	}
	
	public boolean drawBackground()
	{
		return this.drawBackground;
	}
	
	public void setDrawBackground(boolean enabled)
	{
		this.drawBackground = enabled;
		this.write();
	}
	
	private int getBackgroundAlpha()
	{
		return this.backgroundAlpha;
	}
	
	public float getBackgroundAlphaF()
	{
		return this.getBackgroundAlpha() / 255F;
	}
	
	public void setBackgroundAlpha(int alpha)
	{
		this.backgroundAlpha = alpha;
		this.write();
	}
	
	private int getButtonAlpha()
	{
		return this.buttonAlpha;
	}
	
	public float getButtonAlphaF()
	{
		return this.getButtonAlpha() / 255F;
	}
	
	public void setButtonAlpha(int alpha)
	{
		this.buttonAlpha = alpha;
		this.write();
	}
	
	@OnlyIn(Dist.CLIENT)
	public static enum EnumIconSize
	{
		x16,
		x32,
		x64;
	}
}
