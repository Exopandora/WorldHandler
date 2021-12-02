package exopandora.worldhandler.gui.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.widget.IWidget;
import net.minecraft.network.chat.MutableComponent;

public interface IContent extends IWidget
{
	Category getCategory();
	
	MutableComponent getTitle();
	MutableComponent getTabTitle();
	
	Content getActiveContent();
	
	@Nonnull
	default Content getBackContent()
	{
		return Contents.MAIN;
	}
	
	@Nullable
	default CommandPreview getCommandPreview()
	{
		return null;
	}
	
	default void onGuiClosed()
	{
		
	}
	
	public static class CommandPreview
	{
		private final List<Entry> builders = new ArrayList<Entry>();
		
		public CommandPreview()
		{
			super();
		}
		
		public CommandPreview(ICommandBuilder builder, Object label)
		{
			this.add(builder, label);
		}
		
		public CommandPreview add(ICommandBuilder builder, Object label)
		{
			this.builders.add(new Entry(builder, label));
			return this;
		}
		
		@Override
		public String toString()
		{
			if(this.builders.isEmpty())
			{
				return null;
			}
			
			return this.builders.stream().map(entry -> entry.builder().toCommand(entry.label(), true)).collect(Collectors.joining(" | "));
		}
		
		private record Entry(ICommandBuilder builder, Object label)
		{
			public Entry
			{
				Objects.requireNonNull(builder);
			}
		}
	}
}
