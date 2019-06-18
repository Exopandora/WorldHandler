package exopandora.worldhandler.gui.content;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.gui.content.impl.ContentContinue;
import exopandora.worldhandler.gui.content.impl.abstr.ContentChild;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Contents
{
	public static final Content MAIN;
	public static final Content CONTAINERS;
	public static final Content MULTIPLAYER;
	
	public static final Content SUMMON;
	
	public static final Content CUSTOM_ITEM;
	public static final Content ENCHANTMENT;
	
	public static final Content EDIT_BLOCKS;
	public static final Content SIGN_EDITOR;
	public static final Content NOTE_EDITOR;
	
	public static final Content WORLD_INFO;
	public static final Content GAMERULES;
	public static final Content RECIPES;
	
	public static final Content PLAYER;
	public static final Content EXPERIENCE;
	public static final Content ADVANCEMENTS;
	
	public static final Content SCOREBOARD_OBJECTIVES;
	public static final Content SCOREBOARD_TEAMS;
	public static final Content SCOREBOARD_PLAYERS;
	
	public static final ContentChild CHANGE_WORLD;
	public static final ContentContinue CONTINUE;
	
	public static final ContentChild POTIONS;
	public static final ContentChild BUTCHER;
	public static final ContentChild BUTCHER_SETTINGS;
	public static final ContentChild SETTINGS;
	
	static
	{
		MAIN = Contents.getRegisteredContent("main");
		CONTAINERS = Contents.getRegisteredContent("containers");
		MULTIPLAYER = Contents.getRegisteredContent("multiplayer");
		
		SUMMON = Contents.getRegisteredContent("summon");
		
		CUSTOM_ITEM = Contents.getRegisteredContent("custom_item");
		ENCHANTMENT = Contents.getRegisteredContent("enchantment");
		
		EDIT_BLOCKS = Contents.getRegisteredContent("edit_blocks");
		SIGN_EDITOR = Contents.getRegisteredContent("sign_editor");
		NOTE_EDITOR = Contents.getRegisteredContent("note_editor");
		
		WORLD_INFO = Contents.getRegisteredContent("world");
		GAMERULES = Contents.getRegisteredContent("gamerules");
		RECIPES = Contents.getRegisteredContent("recipes");
		
		PLAYER = Contents.getRegisteredContent("player");
		EXPERIENCE = Contents.getRegisteredContent("experience");
		ADVANCEMENTS = Contents.getRegisteredContent("advancements");
		
		SCOREBOARD_OBJECTIVES = Contents.getRegisteredContent("scoreboard_objectives");
		SCOREBOARD_TEAMS = Contents.getRegisteredContent("scoreboard_teams");
		SCOREBOARD_PLAYERS = Contents.getRegisteredContent("scoreboard_players");
		
		CHANGE_WORLD = (ContentChild) Contents.getRegisteredContent("change_world");
		CONTINUE = (ContentContinue) Contents.getRegisteredContent("continue");
		
		POTIONS = (ContentChild) Contents.getRegisteredContent("potions");
		BUTCHER = (ContentChild) Contents.getRegisteredContent("butcher");
		BUTCHER_SETTINGS = (ContentChild) Contents.getRegisteredContent("butcher_settings");
		SETTINGS = (ContentChild) Contents.getRegisteredContent("settings");
	}
	
	private static Content getRegisteredContent(String name)
	{
		Content content = Content.REGISTRY.getValue(new ResourceLocation(Main.MODID, name));
		
		if(content == null)
		{
			throw new IllegalStateException("Invalid Content requested: " + name);
		}
		
		return content;
	}
}