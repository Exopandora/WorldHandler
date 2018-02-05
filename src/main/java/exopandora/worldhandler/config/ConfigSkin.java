package exopandora.worldhandler.config;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ConfigSkin
{
	private static int ICON_SIZE;
	private static int LABEL_COLOR;
	private static int HEADLINE_COLOR;
	
	private static int BACKGROUND_RED;
	private static int BACKGROUND_GREEN;
	private static int BACKGROUND_BLUE;
	private static int BACKGROUND_ALPHA;
	
	private static int BUTTON_RED;
	private static int BUTTON_GREEN;
	private static int BUTTON_BLUE;
	private static int BUTTON_ALPHA;
	
	private static String TYPE;
	
	private static boolean SHARP_EDGES;
	private static boolean DRAW_BACKGROUND;
	
	public static final String CATEGORY = "skin";
	
	public static void load(Configuration config)
	{
		ICON_SIZE = Integer.valueOf(config.getString("icon_size", CATEGORY, "16", I18n.format("gui.worldhandler.config.comment.skin.icons"), new String[]{"16", "32", "64"}, "gui.worldhandler.config.key.skin.icons"));
		LABEL_COLOR = config.getInt("label_color", CATEGORY, 0x1F1F1F, 0x80000000, 0x7FFFFFFF, I18n.format("gui.worldhandler.config.comment.skin.label_color"), "gui.worldhandler.config.key.skin.label_color");
		HEADLINE_COLOR = config.getInt("headline_color", CATEGORY, 0x4F4F4F, 0x80000000, 0x7FFFFFFF, I18n.format("gui.worldhandler.config.comment.skin.headline_color"), "gui.worldhandler.config.key.skin.headline_color");
		
		BACKGROUND_RED = config.getInt("background_red", CATEGORY, 255, 0, 255, I18n.format("gui.worldhandler.config.comment.skin.background_red"), "gui.worldhandler.config.key.skin.background_red");
		BACKGROUND_GREEN = config.getInt("background_green", CATEGORY, 255, 0, 255, I18n.format("gui.worldhandler.config.comment.skin.background_green"), "gui.worldhandler.config.key.skin.background_green");
		BACKGROUND_BLUE = config.getInt("background_blue", CATEGORY, 255, 0, 255, I18n.format("gui.worldhandler.config.comment.skin.background_blue"), "gui.worldhandler.config.key.skin.background_blue");
		BACKGROUND_ALPHA = config.getInt("background_alpha", CATEGORY, 255, 0, 255, I18n.format("gui.worldhandler.config.comment.skin.background_alpha"), "gui.worldhandler.config.key.skin.background_alpha");
		
		BUTTON_RED = config.getInt("button_red", CATEGORY, 255, 0, 255, I18n.format("gui.worldhandler.config.comment.skin.button_red"), "gui.worldhandler.config.key.skin.button_red");
		BUTTON_GREEN = config.getInt("button_green", CATEGORY, 255, 0, 255, I18n.format("gui.worldhandler.config.comment.skin.button_green"), "gui.worldhandler.config.key.skin.button_green");
		BUTTON_BLUE = config.getInt("button_blue", CATEGORY, 255, 0, 255, I18n.format("gui.worldhandler.config.comment.skin.button_blue"), "gui.worldhandler.config.key.skin.button_blue");
		BUTTON_ALPHA = config.getInt("button_alpha", CATEGORY, 255, 0, 255, I18n.format("gui.worldhandler.config.comment.skin.button_alpha"), "gui.worldhandler.config.key.skin.button_alpha");
		
		TYPE = config.getString("textures", CATEGORY, "resourcepack", I18n.format("gui.worldhandler.config.comment.skin.textures"), new String[]{"resourcepack", "vanilla"}, "gui.worldhandler.config.key.skin.textures");
		SHARP_EDGES = config.getBoolean("sharp_tab_edges", CATEGORY, false, I18n.format("gui.worldhandler.config.comment.skin.sharp_tab_edges"), "gui.worldhandler.config.key.skin.sharp_tab_edges");
		DRAW_BACKGROUND = config.getBoolean("draw_background", CATEGORY, true, I18n.format("gui.worldhandler.config.comment.skin.draw_background"), "gui.worldhandler.config.key.skin.draw_background");
		
		if(config.hasChanged())
		{
			config.save();
		}
	}
	
	public static int getIconSize()
	{
		return ICON_SIZE;
	}
	
	public static int getLabelColor()
	{
		return LABEL_COLOR;
	}
	
	public static int getHeadlineColor()
	{
		return HEADLINE_COLOR;
	}
	
	public static int getBackgroundRed()
	{
		return BACKGROUND_RED;
	}
	
	public static int getBackgroundGreen()
	{
		return BACKGROUND_GREEN;
	}
	
	public static int getBackgroundBlue()
	{
		return BACKGROUND_BLUE;
	}
	
	public static int getButtonRed()
	{
		return BUTTON_RED;
	}
	
	public static int getButtonGreen()
	{
		return BUTTON_GREEN;
	}
	
	public static int getButtonBlue()
	{
		return BUTTON_BLUE;
	}
	
	public static String getTextureType()
	{
		return TYPE;
	}
	
	public static boolean areSharpEdgesEnabled()
	{
		return SHARP_EDGES;
	}
	
	public static boolean isBackgroundDrawingEnabled()
	{
		return DRAW_BACKGROUND;
	}
	
	public static int getBackgroundAlpha()
	{
		return BACKGROUND_ALPHA;
	}
	
	public static int getButtonAlpha()
	{
		return BUTTON_ALPHA;
	}
}
