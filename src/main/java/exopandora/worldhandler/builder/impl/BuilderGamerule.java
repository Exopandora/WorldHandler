package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ArgumentType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BuilderGamerule extends CommandBuilder
{
	public BuilderGamerule()
	{
		
	}
	
	public BuilderGamerule(String rule, String value)
	{
		this.setRule(rule);
		this.setValue(value);
	}
	
	public void setRule(String rule)
	{
		this.setNode(0, rule);
	}
	
	public String getRule()
	{
		return this.getNodeAsString(0);
	}
	
	public void setValue(String value)
	{
		this.setNode(1, value);
	}
	
	public String getValue()
	{
		return this.getNodeAsString(1);
	}
	
	public BuilderGamerule getBuilderForValue(String value)
	{
		return new BuilderGamerule(this.getRule(), value);
	}
	
	@Override
	public String getCommandName()
	{
		return "gamerule";
	}
	
	@Override
	public final CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addOptional("rule", ArgumentType.STRING);
		syntax.addOptional("true|false|value", ArgumentType.STRING);
		
		return syntax;
	}
}
