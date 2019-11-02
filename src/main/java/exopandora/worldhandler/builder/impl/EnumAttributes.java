package exopandora.worldhandler.builder.impl;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum EnumAttributes
{
	MAX_HEALTH("generic.maxHealth", EnumOperation.ADDITIVE, Applyable.BOTH),
	FOLLOW_RANGE("generic.followRange", EnumOperation.ADDITIVE, Applyable.MOB),
	KNOCKBACK_RESISTANCE("generic.knockbackResistance", EnumOperation.PERCENTAGE, Applyable.BOTH),
	MOVEMENT_SPEED("generic.movementSpeed", EnumOperation.PERCENTAGE, Applyable.BOTH),
	ATTACK_DAMAGE("generic.attackDamage", EnumOperation.ADDITIVE, Applyable.BOTH),
	ARMOR("generic.armor", EnumOperation.ADDITIVE, Applyable.BOTH),
	ARMOR_TOUGHNESS("generic.armorToughness", EnumOperation.ADDITIVE, Applyable.BOTH),
	ATTACK_SPEED("generic.attackSpeed", EnumOperation.PERCENTAGE, Applyable.BOTH),
	LUCK("generic.luck", EnumOperation.PERCENTAGE, Applyable.PLAYER),
	HORSE_JUMP_STRENGTH("horse.jumpStrength", EnumOperation.PERCENTAGE, Applyable.MOB),
	ZOMBIE_SPAWN_REINFORCEMENTS("zombie.spawnReinforcements", EnumOperation.PERCENTAGE, Applyable.MOB);
	
	private String attribute;
	private EnumOperation operation;
	private Applyable applyable;

	@OnlyIn(Dist.CLIENT)
	public static enum Applyable
	{
		BOTH,
		PLAYER,
		MOB
	}
	
	private EnumAttributes(String attribute, EnumOperation operation, Applyable applyable)
	{
		this.attribute = attribute;
		this.operation = operation;
		this.applyable = applyable;
	}
	
	public String getAttribute()
	{
		return this.attribute;
	}
	
	public String getTranslationKey()
	{
		return "attribute.name." + this.attribute;
	}
	
	public String getTranslation()
	{
		return I18n.format(this.getTranslationKey(), new Object[0]);
	}
	
	public EnumOperation getOperation()
	{
		return this.operation;
	}
	
	public Applyable getApplyable()
	{
		return this.applyable;
	}
	
	public double calculate(Double value)
	{
		return this.operation.getOperation().apply(value);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static enum EnumOperation
	{
		ADDITIVE(value -> value, "(+)"),
		PERCENTAGE(value -> value / 100, "%");
		
		private final Function<Double, Double> operation;
		private final String declaration;
		
		private EnumOperation(Function<Double, Double> operation, String declaration)
		{
			this.operation = operation;
			this.declaration = declaration;
		}
		
		public Function<Double, Double> getOperation()
		{
			return this.operation;
		}
		
		public String getDeclaration()
		{
			return this.declaration;
		}
	}
	
	public static List<EnumAttributes> getAttributesFor(Applyable applyable)
	{
		return Arrays.stream(EnumAttributes.values()).filter(attribute -> attribute.getApplyable().equals(applyable)).collect(Collectors.toList());
	}
}
