package exopandora.worldhandler.gui.content;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.gui.content.impl.ContentButcherPresets;
import exopandora.worldhandler.gui.content.impl.ContentChild;
import exopandora.worldhandler.gui.content.impl.ContentContinue;
import net.minecraft.resources.ResourceLocation;

public class Contents
{
	public static final Content MAIN = Contents.getRegisteredContent("main");
	public static final Content CONTAINERS = Contents.getRegisteredContent("containers");
	public static final Content MULTIPLAYER = Contents.getRegisteredContent("multiplayer");
	
	public static final Content SUMMON = Contents.getRegisteredContent("summon");
	public static final Content BUTCHER = Contents.getRegisteredContent("butcher");
	public static final ContentChild BUTCHER_SETTINGS = (ContentChild) Contents.getRegisteredContent("butcher_settings");
	public static final ContentButcherPresets BUTCHER_PRESETS = (ContentButcherPresets) Contents.getRegisteredContent("butcher_presets");
	
	public static final Content CUSTOM_ITEM = Contents.getRegisteredContent("custom_item");
	public static final Content ENCHANTMENT = Contents.getRegisteredContent("enchantment");
	public static final Content RECIPES = Contents.getRegisteredContent("recipes");
	
	public static final Content EDIT_BLOCKS = Contents.getRegisteredContent("edit_blocks");
	public static final Content SIGN_EDITOR = Contents.getRegisteredContent("sign_editor");
	public static final Content NOTE_EDITOR = Contents.getRegisteredContent("note_editor");
	
	public static final Content WORLD_INFO = Contents.getRegisteredContent("world");
	public static final Content GAMERULES = Contents.getRegisteredContent("gamerules");
	public static final Content LOCATE = Contents.getRegisteredContent("locate");
	
	public static final Content PLAYER = Contents.getRegisteredContent("player");
	public static final Content EXPERIENCE = Contents.getRegisteredContent("experience");
	public static final Content ADVANCEMENTS = Contents.getRegisteredContent("advancements");
	
	public static final Content SCOREBOARD_OBJECTIVES = Contents.getRegisteredContent("scoreboard_objectives");
	public static final Content SCOREBOARD_TEAMS = Contents.getRegisteredContent("scoreboard_teams");
	public static final Content SCOREBOARD_PLAYERS = Contents.getRegisteredContent("scoreboard_players");
	
	public static final ContentChild CHANGE_WORLD = (ContentChild) Contents.getRegisteredContent("change_world");
	public static final ContentContinue CONTINUE = (ContentContinue) Contents.getRegisteredContent("continue");
	
	public static final ContentChild POTIONS = (ContentChild) Contents.getRegisteredContent("potions");
	public static final ContentChild COMMAND_STACK = (ContentChild) Contents.getRegisteredContent("command_stack");
	public static final ContentChild SETTINGS = (ContentChild) Contents.getRegisteredContent("settings");
	
	public static Content getRegisteredContent(String name)
	{
		Content content = Content.REGISTRY.getValue(new ResourceLocation(Main.MODID, name));
		
		if(content == null)
		{
			throw new IllegalStateException("Requested missing content: " + name);
		}
		
		return content;
	}
	
	public static boolean isRegistered(String name)
	{
		return Content.REGISTRY.containsKey(new ResourceLocation(Main.MODID, name));
	}
}