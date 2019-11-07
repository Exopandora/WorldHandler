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
	private final ConfigValue<EnumIconSize> iconSize;
	private final IntValue labelColor;
	private final IntValue headlineColor;
	private final IntValue backgroundRed;
	private final IntValue backgroundGreen;
	private final IntValue backgroundBlue;
	private final IntValue backgroundAlpha;
	private final IntValue buttonRed;
	private final IntValue buttonGreen;
	private final IntValue buttonBlue;
	private final IntValue buttonAlpha;
	private final ConfigValue<String> type;
	private final BooleanValue sharpEdges;
	private final BooleanValue drawBackground;
	
	public ConfigCategorySkin(ForgeConfigSpec.Builder builder)
	{
		builder.push("skin");
		
		this.iconSize = builder
				.translation("gui.worldhandler.config.skin.icon_size")
				.comment("Size of the icons")
				.defineEnum("icon_size", EnumIconSize.x16, EnumIconSize.values());
		this.labelColor = builder
				.translation("gui.worldhandler.config.skin.label_color")
				.comment("Label color")
				.defineInRange("label_color", 0x1F1F1F, 0x80000000, 0x7FFFFFFF);
		this.headlineColor = builder
				.translation("gui.worldhandler.config.skin.headline_color")
				.comment("Headline color")
				.defineInRange("headline_color", 0x4F4F4F, 0x80000000, 0x7FFFFFFF);
		
		this.backgroundRed = builder
				.translation("gui.worldhandler.config.skin.background_red")
				.comment("Background red")
				.defineInRange("background_red", 255, 0, 255);
		this.backgroundGreen = builder
				.translation("gui.worldhandler.config.skin.background_green")
				.comment("Background green")
				.defineInRange("background_green", 255, 0, 255);
		this.backgroundBlue = builder
				.translation("gui.worldhandler.config.skin.background_blue")
				.comment("Background blue")
				.defineInRange("background_blue", 255, 0, 255);
		this.backgroundAlpha = builder
				.translation("gui.worldhandler.config.skin.background_alpha")
				.comment("Background alpha")
				.defineInRange("background_alpha", 255, 0, 255);
		
		this.buttonRed = builder
				.translation("gui.worldhandler.config.skin.button_red")
				.comment("Button red")
				.defineInRange("button_red", 255, 0, 255);
		this.buttonGreen = builder
				.translation("gui.worldhandler.config.skin.button_green")
				.comment("Button green")
				.defineInRange("button_green", 255, 0, 255);
		this.buttonBlue = builder
				.translation("gui.worldhandler.config.skin.button_blue")
				.comment("Button blue")
				.defineInRange("button_blue", 255, 0, 255);
		this.buttonAlpha = builder
				.translation("gui.worldhandler.config.skin.button_alpha")
				.comment("Button alpha")
				.defineInRange("button_alpha", 255, 0, 255);

		this.type = builder
				.translation("gui.worldhandler.config.skin.textures")
				.comment("Background texture (resourcepack, vanilla)")
				.defineInList("textures", "resourcepack", Arrays.asList("resourcepack", "vanilla"));
		
		this.sharpEdges = builder
				.translation("gui.worldhandler.config.settings.sharp_tab_edges")
				.comment("Whether or not the gui has sharp or smooth tab edges")
				.define("sharp_tab_edges", false);
		this.drawBackground = builder
				.translation("gui.worldhandler.config.settings.draw_background")
				.comment("Whether or not to enable background drawing")
				.define("draw_background", true);
		
		builder.pop();
	}
	
	public EnumIconSize getIconSize()
	{
		return this.iconSize.get();
	}
	
	public void setIconSize(EnumIconSize size)
	{
		Config.set(this.iconSize, size);
	}
	
	public int getLabelColor()
	{
		return this.labelColor.get();
	}
	
	public void setLabelColor(int color)
	{
		Config.set(this.labelColor, color);
	}
	
	public int getHeadlineColor()
	{
		return this.headlineColor.get();
	}
	
	public void setHeadlineColor(int color)
	{
		Config.set(this.headlineColor, color);
	}
	
	private int getBackgroundRed()
	{
		return this.backgroundRed.get();
	}
	
	public float getBackgroundRedF()
	{
		return this.getBackgroundRed() / 255F;
	}
	
	public void setBackgroundRed(int red)
	{
		Config.set(this.backgroundRed, red);
	}
	
	private int getBackgroundGreen()
	{
		return this.backgroundGreen.get();
	}
	
	public float getBackgroundGreenF()
	{
		return this.getBackgroundGreen() / 255F;
	}
	
	public void setBackgroundGreen(int green)
	{
		Config.set(this.backgroundGreen, green);
	}
	
	private int getBackgroundBlue()
	{
		return this.backgroundBlue.get();
	}
	
	public float getBackgroundBlueF()
	{
		return this.getBackgroundBlue() / 255F;
	}
	
	public void setBackgroundBlue(int blue)
	{
		Config.set(this.backgroundBlue, blue);
	}
	
	private int getButtonRed()
	{
		return this.buttonRed.get();
	}
	
	public float getButtonRedF()
	{
		return this.getButtonRed() / 255F;
	}
	
	public void setButtonRed(int red)
	{
		Config.set(this.backgroundRed, red);
	}
	
	private int getButtonGreen()
	{
		return this.buttonGreen.get();
	}
	
	public float getButtonGreenF()
	{
		return this.getButtonGreen() / 255F;
	}
	
	public void setButtonGreen(int green)
	{
		Config.set(this.buttonGreen, green);
	}
	
	private int getButtonBlue()
	{
		return this.buttonBlue.get();
	}
	
	public float getButtonBlueF()
	{
		return this.getButtonBlue() / 255F;
	}
	
	public void setButtonBlue(int blue)
	{
		Config.set(this.buttonBlue, blue);
	}
	
	public String getTextureType()
	{
		return this.type.get();
	}
	
	public void setTextureType(String type)
	{
		Config.set(this.type, type);
	}
	
	public boolean sharpEdges()
	{
		return this.sharpEdges.get();
	}
	
	public void setSharpEdges(boolean enabled)
	{
		Config.set(this.sharpEdges, enabled);
	}
	
	public boolean drawBackground()
	{
		return this.drawBackground.get();
	}
	
	public void setDrawBackground(boolean enabled)
	{
		Config.set(this.drawBackground, enabled);
	}
	
	private int getBackgroundAlpha()
	{
		return this.backgroundAlpha.get();
	}
	
	public float getBackgroundAlphaF()
	{
		return this.getBackgroundAlpha() / 255F;
	}
	
	public void setBackgroundAlpha(int alpha)
	{
		Config.set(this.backgroundAlpha, alpha);
	}
	
	private int getButtonAlpha()
	{
		return this.buttonAlpha.get();
	}
	
	public float getButtonAlphaF()
	{
		return this.getButtonAlpha() / 255F;
	}
	
	public void setButtonAlpha(int alpha)
	{
		Config.set(this.buttonAlpha, alpha);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static enum EnumIconSize
	{
		x16,
		x32,
		x64;
	}
}
