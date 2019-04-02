package exopandora.worldhandler.format.text;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SignText
{
	private static final Gson GSON = new GsonBuilder().registerTypeAdapter(JsonSignLine.class, new JsonSignLineSerializer()).create();
	
	private ColoredString text = new ColoredString();
	private String command;
	private final int line;
	
	public SignText(int line)
	{
		this.line = line;
	}
	
	public int getLine()
	{
		return this.line;
	}
	
	public ColoredString getColoredString()
	{
		return this.text;
	}
	
	public void setColoredString(ColoredString coloredString)
	{
		this.text = coloredString;
	}
	
	public String getCommand()
	{
		return this.command;
	}
	
	public void setCommand(String command)
	{
		this.command = command;
	}
	
	public boolean hasCommand()
	{
		return this.command != null && !this.command.isEmpty();
	}
	
	@Override
	public String toString()
	{
		if(!this.text.isSpecial() && !this.hasCommand())
		{
			return this.text.getText();
		}
		
		JsonSignLine line = new JsonSignLine(this.text);
		
		if(this.hasCommand())
		{
			line.setClickEvent(new JsonClickEvent("run_command", FormattedString.getPreformattedString(this.command)));
		}
		
		return GSON.toJson(line);
	}
}
