import static java.lang.System.*;
public class Player extends Creature
{
	public static int maxHealth=20,maxStrength=10,gold=0;
	public Player(String name)
	{
		super(10,0,name);
	}
	public static void rest()
	{
		int heal = Roll.d20();
		Ludus.player.health+=heal;
		if (Ludus.player.health>maxHealth)
			Ludus.player.health=maxHealth;
		if (Translation.language.equals("Latina"))
			out.println("Tu quiescas et tu curas "+heal+" detrimentos.");
		else if (Translation.language.equals("English"))
			out.println("You rest and you heal from "+heal+" damage.");
		Ludus.player.strength+=heal;
		if (Ludus.player.strength>maxStrength)
			Ludus.player.strength=maxStrength;
		Ludus.player.fled=false;
	}
	public static int loot(int bonus)
	{
		int lootedGold=0+bonus;
		if (Ludus.player.fled==false)
			for (Creature c : Attempt.currentFight.enemies)
			{
				if (!c.fled)
				{
					lootedGold+=c.strength;
					break;
				}
		}
		gold+=lootedGold;
		return lootedGold;
	}
	@Override
	public String toString()
	{
		if (Translation.language.equals("English"))
			return (returnData()+"\tGold: "+gold);
		return (returnData()+"\tDenarii: "+gold);
	}
	@Override
	public int damage()
	{
		return (Roll.d20()-(10-strength));
	}
	@Override
	public boolean flees(int needs)
	{
		if (Roll.d20()<needs)
		{
			if (Translation.language.equals("Latina"))
				out.println("Tu conaris fugere, sed dees!");
			else if (Translation.language.equals("English"))
				out.println("You try to flee, but you fail!");
			return false;
		}
		if (Translation.language.equals("Latina"))
			out.println("Tu fuges feliciter.");
		else if (Translation.language.equals("English"))
			out.println("You flee successfully.");
		fled=true;
		return true;
	}
	@Override
	public boolean hurtPlayer()
	{
		return false; // No.
	}
}