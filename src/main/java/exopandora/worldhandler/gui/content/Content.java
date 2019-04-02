package exopandora.worldhandler.gui.content;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.gui.content.impl.ContentAdvancements;
import exopandora.worldhandler.gui.content.impl.ContentButcher;
import exopandora.worldhandler.gui.content.impl.ContentButcherSettings;
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
import exopandora.worldhandler.gui.content.impl.ContentScoreboardObjectives;
import exopandora.worldhandler.gui.content.impl.ContentPlayer;
import exopandora.worldhandler.gui.content.impl.ContentScoreboardPlayers;
import exopandora.worldhandler.gui.content.impl.ContentPotions;
import exopandora.worldhandler.gui.content.impl.ContentRecipes;
import exopandora.worldhandler.gui.content.impl.ContentSettings;
import exopandora.worldhandler.gui.content.impl.ContentSignEditor;
import exopandora.worldhandler.gui.content.impl.ContentSummon;
import exopandora.worldhandler.gui.content.impl.ContentScoreboardTeams;
import exopandora.worldhandler.gui.content.impl.ContentWorldInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.registry.RegistryNamespacedDefaultedByKey;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistryEntry;

@OnlyIn(Dist.CLIENT)
public abstract class Content extends ForgeRegistryEntry<Content> implements IContent
{
	public static final String NAMESPACE = String.join("_", new String[] {Main.MODID, "content"});
	public static final IRegistry<Content> REGISTRY = IRegistry.func_212610_a(NAMESPACE, new RegistryNamespacedDefaultedByKey<Content>(new ResourceLocation(NAMESPACE, "main"))); 
	
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
		registerContent(22, "butcher_settings", new ContentButcherSettings());
		registerContent(23, "settings", new ContentSettings());
	}
	
	private static void registerContent(int id, String textualID, Content content)
	{
		registerContent(id, new ResourceLocation(Main.MODID, textualID), content);
	}
	
	private static void registerContent(int id, ResourceLocation textualID, Content content)
	{
		REGISTRY.register(id, textualID, content);
	}
	
	private Map<String, Object> persistence;
	
	@SuppressWarnings("unchecked")
	public <T> T getPersistence(String id, Supplier<T> supplier)
	{
		if(this.persistence == null)
		{
			this.persistence = new HashMap<String, Object>();
		}
		
		if(this.persistence.containsKey(id))
		{
			return (T) this.persistence.get(id);
		}
		
		T object = supplier.get();
		this.persistence.put(id, object);
		
		return object;
	}
}
