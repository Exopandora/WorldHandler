package exopandora.worldhandler.usercontent.model;

public enum ArgumentType
{
	SHORT,
	BYTE,
	INT,
	FLOAT,
	DOUBLE,
	LONG,
	BOOLEAN,
	WORD,
	STRING,
	GREEDY_STRING,
	RESOURCE_LOCATION,
	ITEM,
	BLOCKSTATE,
	BLOCKPREDICATE,
	NBT,
	COORDINATE_INT,
	COORDINATE_DOUBLE,
	PLAYER,
	RANGE_INT,
	RANGE_DOUBLE,
	ANGLE,
	ENCHANTMENT,
	ENTITY,
	GAMEMODE,
	TIME,
	EFFECT,
	AXIS,
	ANCHOR,
	DIFFICULTY,
	RENDER_TYPE,
	OPERATION,
	TEXT_COMPONENT,
	RELATION,
	TYPE,
	LINKAGE,
	NBT_PATH,
	ITEM_PREDICATE,
	CRITERIA;
	
	@Override
	public String toString()
	{
		return this.name().toLowerCase();
	}
}
