package exopandora.worldhandler.gui.content;

import java.util.HashMap;
import java.util.Map;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.gui.button.persistence.ButtonValue;
import exopandora.worldhandler.gui.content.impl.ContentAdvancements;
import exopandora.worldhandler.gui.content.impl.ContentButcher;
import exopandora.worldhandler.gui.content.impl.ContentChangeWorld;
import exopandora.worldhandler.gui.content.impl.ContentContainers;
import exopandora.worldhandler.gui.content.impl.ContentContinue;
import exopandora.worldhandler.gui.content.impl.ContentCustomItem;
import exopandora.worldhandler.gui.content.impl.ContentEditBlocks;
import exopandora.worldhandler.gui.content.impl.ContentEnchantment;
import exopandora.worldhandler.gui.content.impl.ContentExperience;
import exopandora.worldhandler.gui.content.impl.ContentGamerules;
import exopandora.worldhandler.gui.content.impl.ContentMain;
import exopandora.worldhandler.gui.content.impl.ContentMultiplayer;
import exopandora.worldhandler.gui.content.impl.ContentNoteEditor;
import exopandora.worldhandler.gui.content.impl.ContentPlayer;
import exopandora.worldhandler.gui.content.impl.ContentPlaysound;
import exopandora.worldhandler.gui.content.impl.ContentPotions;
import exopandora.worldhandler.gui.content.impl.ContentRecipes;
import exopandora.worldhandler.gui.content.impl.ContentScoreboardObjectives;
import exopandora.worldhandler.gui.content.impl.ContentScoreboardPlayers;
import exopandora.worldhandler.gui.content.impl.ContentScoreboardTeams;
import exopandora.worldhandler.gui.content.impl.ContentSignEditor;
import exopandora.worldhandler.gui.content.impl.ContentSummon;
import exopandora.worldhandler.gui.content.impl.ContentWorldInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class Content implements IContent
{
	public static final RegistryNamespaced<ResourceLocation, Content> REGISTRY = new RegistryNamespaced<ResourceLocation, Content>(); 
	
	public static void registerContents()
	{
		//MAIN
		registerContent(0, "main", new ContentMain());
		registerContent(1, "containers", new ContentContainers());
		registerContent(2, "multiplayer", new ContentMultiplayer());
		
		//ENTITIES
		registerContent(3, "summon", new ContentSummon());
		
		//ITEMS
		registerContent(5, "custom_item", new ContentCustomItem());
		registerContent(4, "enchantment", new ContentEnchantment());
		
		//BLOCKS
		registerContent(6, "edit_blocks", new ContentEditBlocks());
		registerContent(7, "sign_editor", new ContentSignEditor());
		registerContent(8, "note_editor", new ContentNoteEditor());
		
		//WORLD
		registerContent(9, "world", new ContentWorldInfo());
		registerContent(10, "gamerules", new ContentGamerules());
		registerContent(11, "recipes", new ContentRecipes());
		
		//PLAYER
		registerContent(12, "player", new ContentPlayer());
		registerContent(13, "experience", new ContentExperience());
		registerContent(14, "advancements", new ContentAdvancements());
		
		//SCOREBOARD
		registerContent(15, "scoreboard_objectives", new ContentScoreboardObjectives());
		registerContent(16, "scoreboard_teams", new ContentScoreboardTeams());
		registerContent(17, "scoreboard_players", new ContentScoreboardPlayers());
		
		//MISC
		registerContent(18, "change_world", new ContentChangeWorld());
		registerContent(19, "continue", new ContentContinue());
		
		//NO CATEGORY
		registerContent(20, "potions", new ContentPotions());
		registerContent(21, "butcher", new ContentButcher());
	}
	
    private static void registerContent(int id, String textualID, Content content)
    {
    	registerContent(id, new ResourceLocation(Main.MODID, textualID), content);
    }
    
    private static void registerContent(int id, ResourceLocation textualID, Content content)
    {
        REGISTRY.register(id, textualID, content);
    }
    
    private Map<Object, ButtonValue> persistence;
    
    public <T> ButtonValue<T> getPersistence(Object id)
    {
    	if(this.persistence == null)
    	{
    		this.persistence = new HashMap<Object, ButtonValue>();
    	}
    	
    	if(this.persistence.containsKey(id))
    	{
    		return this.persistence.get(id);
    	}
    	
    	ButtonValue<T> values = new ButtonValue<T>();
    	
    	this.persistence.put(id, values);
    	
    	return values;
    }
}
