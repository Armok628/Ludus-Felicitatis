import static java.lang.System.*;
import java.lang.IndexOutOfBoundsException;
public class Battle
{
	private int min=0,max=0,pow=0,var=0;
	public int size=0;
	public static int dead=0;
	private static boolean exception=true;
	public static Creature[] enemies;
	public Battle(int min,int max,int pow,int var)
	{
		size = Roll.dice(min,max);
		enemies = new Creature[size];
		for (int n=0;n<enemies.length;n++)
		{
			enemies[n]=new Creature(pow,var,String.valueOf(n+1));
		}
		out.println(toString());
		strengthCheck();
	}
	public static void damageCreature(int target,int dmg)
	{
		do
		{
			try
			{
				target--;
				/* Put / after this asterisk to enable debug insta-kill *
				if (Debug.isSet())
				{
					out.println("\nDEBUG : Tu necas hostem!");
					enemies[target].hit(20);
					enemies[target].strength--;
					dead++;
				}
				else
				/**/
				if (enemies[target].health==0)
				{
					if (Translation.language.equals("Latina"))
						out.println("Hostis quam oppugnas defunctus est!");
					else if (Translation.language.equals("English"))
						out.println("The enemy which you attack is already gone!");
				}
				else if (dmg<1)
				{
					if (Translation.language.equals("Latina"))
						out.println("Tu oppugnas sed dees!");
					else if (Translation.language.equals("English"))
						out.println("You attack but you miss!");
				}
				else if (enemies[target].dodge())
				{
					if (Translation.language.equals("Latina"))
						out.println("Tu oppugnas sed hostis devitat!");
					else if (Translation.language.equals("English"))
						out.println("You attack but the enemy dodges!");
				}
				else
					{
						if (Translation.language.equals("Latina"))
							out.println("\nTu oppugnas, et efficis "+dmg+" detrimenta!");
						else if (Translation.language.equals("English"))
							out.println("\nYou attack, and you cause "+dmg+" damage!");
						enemies[target].hit(dmg);
						enemies[target].strength--;
						if (enemies[target].health==0)
						{
							if (Translation.language.equals("Latina"))
								out.println("Hostis tuus nunc mortuus est!");
							else if (Translation.language.equals("English"))
								out.println("Your enemy is now dead!");
							dead++;
						}
					}
				exception=false;
			}
			catch (IndexOutOfBoundsException e)
			{
				if (Translation.language.equals("Latina"))
				out.print("Scribe numerum inter 1 et "+(Attempt.currentFight.size)+" : ");
				else if (Translation.language.equals("English"))
				out.print("Enter a number between 1 and "+(Attempt.currentFight.size)+" : ");
				target = GetInput.integer();
			}
		}
		while (exception);
	}
	public static void enemiesFlee()
	{
		for (Creature c : enemies)
			if (c.health>0&&!c.fled&&c.flees(/**/20-(5*dead)+c.strength/**/))														//see to-do 1
			{
				c.health=0;
				if (Translation.language.equals("Latina"))
					out.println("Hostis fugit feliciter!");
				else if (Translation.language.equals("English"))
					out.println("An enemy successfully flees!");
				c.fled=true;
			}
	}
	public static int enemiesLoot()
	{
		int stolenGold=0;
		for (Creature c : Attempt.currentFight.enemies)
		{
			if (c.health>0)
			{
				Ludus.player.gold-=c.strength;
				stolenGold+=c.strength;
			}
			if (Ludus.player.gold<0)
			{
				stolenGold-=(0-Ludus.player.gold);
				Ludus.player.gold=0;
				break;
			}
		}
		return stolenGold;//but not really
	}
	public static boolean allGone()
	{
		for (Creature e : enemies)
			if (e.health>0)
				return false;
		return true;
	}
	public static void strengthCheck()
	{
		boolean redisplay=false;
		for (Creature c : enemies)
		{
			if (c.strength<=0&&c.health>0)
			{
				c.strength=0;
				c.health=0;
				if (Translation.language.equals("Latina"))
					out.println("Hostis cadit et moritur!");
				else if (Translation.language.equals("English"))
					out.println("An enemy falls and dies!");
				redisplay=true;
			}
		}
		out.println();
		if (redisplay)
			out.println(display());
	}
	public static String display()
	{
		String data="";
		for (Creature c : enemies)
		{
			data+=c.returnData()+"\n";
		}
		return data;
	}
	public String toString()
	{
		String data="";
		for (Creature c : enemies)
		{
			data+=c.returnData()+"\n";
		}
		strengthCheck();
		return data+"\n"+Ludus.player+"\n";
	}
}