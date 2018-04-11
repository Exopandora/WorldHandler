package exopandora.worldhandler.gui.content;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.gui.content.impl.ContentContinue;
import exopandora.worldhandler.gui.content.impl.abstr.ContentChild;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
	
	static
	{
		MAIN = Contents.getRegisteredContainer("main");
		CONTAINERS = Contents.getRegisteredContainer("containers");
		MULTIPLAYER = Contents.getRegisteredContainer("multiplayer");
		
		SUMMON = Contents.getRegisteredContainer("summon");
		
		CUSTOM_ITEM = Contents.getRegisteredContainer("custom_item");
		ENCHANTMENT = Contents.getRegisteredContainer("enchantment");
		
		EDIT_BLOCKS = Contents.getRegisteredContainer("edit_blocks");
		SIGN_EDITOR = Contents.getRegisteredContainer("sign_editor");
		NOTE_EDITOR = Contents.getRegisteredContainer("note_editor");
		
		WORLD_INFO = Contents.getRegisteredContainer("world");
		GAMERULES = Contents.getRegisteredContainer("gamerules");
		
		PLAYER = Contents.getRegisteredContainer("player");
		EXPERIENCE = Contents.getRegisteredContainer("experience");
		ADVANCEMENTS = Contents.getRegisteredContainer("advancements");
		
		SCOREBOARD_OBJECTIVES = Contents.getRegisteredContainer("scoreboard_objectives");
		SCOREBOARD_TEAMS = Contents.getRegisteredContainer("scoreboard_teams");
		SCOREBOARD_PLAYERS = Contents.getRegisteredContainer("scoreboard_players");
		
		CHANGE_WORLD = Contents.getRegisteredContainer("change_world");
		CONTINUE = Contents.getRegisteredContainer("continue");
		
		POTIONS = Contents.getRegisteredContainer("potions");
		BUTCHER = Contents.getRegisteredContainer("butcher");
	}
	
	private static <T extends Content> T getRegisteredContainer(String name)
	{
		Content container = Content.REGISTRY.getObject(new ResourceLocation(Main.MODID, name));
		
		if(container == null)
		{
			throw new IllegalStateException("Invalid Container requested: " + name);
		}
		
		return (T) container;
	}
}