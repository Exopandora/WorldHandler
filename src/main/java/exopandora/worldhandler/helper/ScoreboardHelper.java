package exopandora.worldhandler.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.base.Predicates;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.scoreboard.IScoreCriteria;
import net.minecraft.scoreboard.Team.CollisionRule;
import net.minecraft.scoreboard.Team.EnumVisible;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ScoreboardHelper
{
	private final Node objectives = new Node();
	private final Node slots = new Node();
	private final Node options = new Node();
	
	public ScoreboardHelper()
	{
		this.init();
	}
	
	private void init()
	{
		//Lists
		
		final List<Node> colors = this.createList(ChatFormatting.values(), ChatFormatting::getName, ChatFormatting::isColor);
		final List<Node> visibility = this.createList(EnumVisible.values(), value -> value.internalName);
		final List<Node> collision = this.createList(CollisionRule.values(), value -> value.name);
		final List<Node> bool = this.createList(new Boolean[] {true, false}, String::valueOf);
		
		//Objectives
		
		for(String criteria : IScoreCriteria.INSTANCES.keySet())
		{
			this.objectives.insertNode(criteria.split("[.]"));
		}
		
		this.objectives.merge("stat", (parent, child) -> parent + "." + child);
		this.objectives.mergeItems();
		this.objectives.sort();
		
		//Slots
		
		this.slots.addNode("belowName");
		this.slots.addNode("list");
		this.slots.addNode("sidebar");
		this.slots.addNode("sidebar.team", colors);
		this.slots.sort();
		
		//Options
		
		this.options.addNode("color", colors);
		this.options.addNode("nametagVisibility", visibility);
		this.options.addNode("deathMessageVisibility", visibility);
		this.options.addNode("friendlyfire", bool);
		this.options.addNode("seeFriendlyInvisibles", bool);
		this.options.addNode("collisionRule", collision);
		this.options.sort();
	}
	
	private <T> List<Node> createList(T[] array, Function<T, String> mapper)
	{
		return this.createList(array, mapper, Predicates.<T>alwaysTrue());
	}
	
	private <T> List<Node> createList(T[] array, Function<T, String> mapper, Predicate<T> predicate)
	{
		List<Node> list = new ArrayList<Node>();
		
		for(T index : array)
		{
			if(predicate.test(index))
			{
				list.add(new Node(mapper.apply(index)));
			}
		}
		
		return list;
	}
	
	public List<Node> getObjectives()
	{
		if(this.objectives != null)
		{
			return this.objectives.getEntries();
		}
		
		return null;
	}
	
	public List<Node> getSlots()
	{
		if(this.slots != null)
		{
			return this.slots.getEntries();
		}
		
		return null;
	}
	
	public List<Node> getOptions()
	{
		if(this.options != null)
		{
			return this.options.getEntries();
		}
		
		return null;
	}
}
