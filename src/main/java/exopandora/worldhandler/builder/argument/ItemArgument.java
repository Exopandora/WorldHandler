package exopandora.worldhandler.builder.argument;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import java.util.ArrayList;
import java.util.List;

import exopandora.worldhandler.builder.argument.tag.IItemComponentProvider;
import exopandora.worldhandler.util.ItemPredicateParser;
import exopandora.worldhandler.util.RegistryHelper;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.BuiltInRegistries;

public class ItemArgument extends TagArgument
{
	private Item item;
	private List<IItemComponentProvider> componentProviders;
	
	protected ItemArgument()
	{
		super();
	}
	
	public void set(@Nullable Item item)
	{
		this.item = item;
	}
	
	public void set(@Nullable ResourceLocation item)
	{
		if(item != null)
		{
			this.set(BuiltInRegistries.ITEM.get(item));
		}
		else
		{
			this.item = null;
		}
	}
	
	public Item getItem()
	{
		return this.item;
	}

	public void addComponentProvider(IItemComponentProvider provider)
	{
		if(this.componentProviders == null)
		{
			this.componentProviders = new ArrayList<IItemComponentProvider>();
		}

		this.componentProviders.add(provider);
	}
	
	@Override
	public void deserialize(@Nullable String string)
	{
		if(string != null)
		{
			try
			{
				ItemPredicateParser parser = new ItemPredicateParser(string);
				parser.parse(false);
				parser.getItem().ifPresentOrElse(item ->
				{
					this.item = item;
					this.setTag(parser.getNbt());
				}, () ->
				{
					this.item = null;
					this.setTag(null);
				});
			}
			catch(CommandSyntaxException e)
			{
				this.item = null;
				this.setTag(null);
			}
		}
		else
		{
			this.item = null;
			this.setTag(null);
		}
	}
	
	@Override
	@Nullable
	public String serialize()
	{
		if(this.item == null)
		{
			return null;
		}

		DataComponentPatch.Builder components = DataComponentPatch.builder();

		if(this.componentProviders != null)
		{
			for(IItemComponentProvider provider : this.componentProviders)
			{
				provider.addItemComponents(components);
			}
		}

		DataComponentPatch componentPatch = components.build();

		if(!componentPatch.isEmpty())
		{
			return new ItemInput(BuiltInRegistries.ITEM.wrapAsHolder(this.item), componentPatch).serialize(RegistryHelper.registryAccess());
		}
		
		String tag = super.serialize();
		
		if(tag != null)
		{
			return BuiltInRegistries.ITEM.getKey(this.item).toString() + tag;
		}
		
		return BuiltInRegistries.ITEM.getKey(this.item).toString();
	}
	
	@Override
	public boolean isDefault()
	{
		return super.isDefault() && this.item == null;
	}
}
