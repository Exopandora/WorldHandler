package exopandora.worldhandler.config;

import java.util.Arrays;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

@OnlyIn(Dist.CLIENT)
public class ConfigCategorySkin
{
	private int iconSize;
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
	
	private final ConfigValue<Integer> valueIconSize;
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
				.comment(I18n.format("gui.worldhandler.config.comment.skin.icon_size"))
				.translation("gui.worldhandler.config.key.skin.icon_size")
				.defineInList("icon_size", 16, Arrays.asList(16, 32, 64));
		this.valueLabelColor = builder
				.comment(I18n.format("gui.worldhandler.config.comment.skin.label_color"))
				.translation("gui.worldhandler.config.key.skin.label_color")
				.defineInRange("label_color", 0x1F1F1F, 0x80000000, 0x7FFFFFFF);
		this.valueHeadlineColor = builder
				.comment(I18n.format("gui.worldhandler.config.comment.skin.headline_color"))
				.translation("gui.worldhandler.config.key.skin.headline_color")
				.defineInRange("headline_color", 0x4F4F4F, 0x80000000, 0x7FFFFFFF);
		
		this.valueBackgroundRed = builder
				.comment(I18n.format("gui.worldhandler.config.comment.skin.background_red"))
				.translation("gui.worldhandler.config.key.skin.background_red")
				.defineInRange("background_red", 255, 0, 255);
		this.valueBackgroundGreen = builder
				.comment(I18n.format("gui.worldhandler.config.comment.skin.background_green"))
				.translation("gui.worldhandler.config.key.skin.background_green")
				.defineInRange("background_green", 255, 0, 255);
		this.valueBackgroundBlue = builder
				.comment(I18n.format("gui.worldhandler.config.comment.skin.background_blue"))
				.translation("gui.worldhandler.config.key.skin.background_blue")
				.defineInRange("background_blue", 255, 0, 255);
		this.valueBackgroundAlpha = builder
				.comment(I18n.format("gui.worldhandler.config.comment.skin.background_alpha"))
				.translation("gui.worldhandler.config.key.skin.background_alpha")
				.defineInRange("background_alpha", 255, 0, 255);
		
		this.valueButtonRed = builder
				.comment(I18n.format("gui.worldhandler.config.comment.skin.button_red"))
				.translation("gui.worldhandler.config.key.skin.button_red")
				.defineInRange("button_red", 255, 0, 255);
		this.valueButtonGreen = builder
				.comment(I18n.format("gui.worldhandler.config.comment.skin.button_green"))
				.translation("gui.worldhandler.config.key.skin.button_green")
				.defineInRange("button_green", 255, 0, 255);
		this.valueButtonBlue = builder
				.comment(I18n.format("gui.worldhandler.config.comment.skin.button_blue"))
				.translation("gui.worldhandler.config.key.skin.button_blue")
				.defineInRange("button_blue", 255, 0, 255);
		this.valueButtonAlpha = builder
				.comment(I18n.format("gui.worldhandler.config.comment.skin.button_alpha"))
				.translation("gui.worldhandler.config.key.skin.button_alpha")
				.defineInRange("button_alpha", 255, 0, 255);

		this.valueType = builder
				.comment(I18n.format("gui.worldhandler.config.comment.skin.textures"))
				.translation("gui.worldhandler.config.key.skin.textures")
				.defineInList("textures", "resourcepack", Arrays.asList("resourcepack", "vanilla"));
		
		this.valueSharpEdges = builder
				.comment(I18n.format("gui.worldhandler.config.comment.settings.sharp_tab_edges"))
				.translation("gui.worldhandler.config.key.settings.sharp_tab_edges")
				.define("sharp_tab_edges", false);
		this.valueDrawBackground = builder
				.comment(I18n.format("gui.worldhandler.config.comment.settings.draw_background"))
				.translation("gui.worldhandler.config.key.settings.draw_background")
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
	
	public int getIconSize()
	{
		return this.iconSize;
	}
	
	public void setIconSize(int size)
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
}
