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
import exopandora.worldhandler.gui.content.impl.ContentPlayer;
import exopandora.worldhandler.gui.content.impl.ContentPotions;
import exopandora.worldhandler.gui.content.impl.ContentRecipes;
import exopandora.worldhandler.gui.content.impl.ContentScoreboardObjectives;
import exopandora.worldhandler.gui.content.impl.ContentScoreboardPlayers;
import exopandora.worldhandler.gui.content.impl.ContentScoreboardTeams;
import exopandora.worldhandler.gui.content.impl.ContentSettings;
import exopandora.worldhandler.gui.content.impl.ContentSignEditor;
import exopandora.worldhandler.gui.content.impl.ContentSummon;
import exopandora.worldhandler.gui.content.impl.ContentWorldInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@OnlyIn(Dist.CLIENT)
public abstract class Content extends ForgeRegistryEntry<Content> implements IContent
{
	public static final IForgeRegistry<Content> REGISTRY = new RegistryBuilder<Content>()
			.setType(Content.class)
			.setName(new ResourceLocation(String.join("_", new String[] {Main.MODID, "content"})))
			.disableSync()
			.disableSaving()
			.create();
	
	public static void registerContents()
	{
		//MAIN
		Content.register("main", new ContentMain());
		Content.register("containers", new ContentContainers());
		Content.register("multiplayer", new ContentMultiplayer());
		
		//ENTITIES
		Content.register("summon", new ContentSummon());
		
		//ITEMS
		Content.register("custom_item", new ContentCustomItem());
		Content.register("enchantment", new ContentEnchantment());
		
		//BLOCKS
		Content.register("edit_blocks", new ContentEditBlocks());
		Content.register("sign_editor", new ContentSignEditor());
		Content.register("note_editor", new ContentNoteEditor());
		
		//WORLD
		Content.register("world", new ContentWorldInfo());
		Content.register("gamerules", new ContentGamerules());
		Content.register("recipes", new ContentRecipes());
		
		//PLAYER
		Content.register("player", new ContentPlayer());
		Content.register("experience", new ContentExperience());
		Content.register("advancements", new ContentAdvancements());
		
		//SCOREBOARD
		Content.register("scoreboard_objectives", new ContentScoreboardObjectives());
		Content.register("scoreboard_teams", new ContentScoreboardTeams());
		Content.register("scoreboard_players", new ContentScoreboardPlayers());
		
		//MISC
		Content.register("change_world", new ContentChangeWorld());
		Content.register("continue", new ContentContinue());
		
		//NO CATEGORY
		Content.register("potions", new ContentPotions());
		Content.register("butcher", new ContentButcher());
		Content.register("butcher_settings", new ContentButcherSettings());
		Content.register("settings", new ContentSettings());
	}
	
	private static void register(String name, Content content)
	{
		Content.registerContent(new ResourceLocation(Main.MODID, name), content);
	}
	
	private static void registerContent(ResourceLocation name, Content content)
	{
		content.setRegistryName(name);
		REGISTRY.register(content);
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
