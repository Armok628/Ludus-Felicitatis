import static java.lang.System.*;
public class Creature
{
	public String name = "";
	public int health=20,strength;
	public boolean fled=false;
	public Creature(int pow,int var,String num)
	{
		strength = Roll.dice(pow-var,pow+var);
		name = num;
	}
	public void hit(int dmg)
	{
		health-=dmg;
		if (health<0)
			health=0;
	}
	public boolean dodge()
	{
		if (Roll.d20()>=20-strength/2)
			return true;
		return false;
	}
	public String returnData()
	{
		if (fled)
		{
			if (Translation.language.equals("English"))
				return name+" -\t\tFled";
			return name+" -\t\tFugavit";
		}
		if (Translation.language.equals("English"))
			return name+" -\tHealth: "+health+"\tStrength: "+strength;
		return name+" -\tSanitas: "+health+"\tFortitudo: "+strength;
	}
	public boolean hurtPlayer()
	{
		int dmg = this.damage();
		boolean playerKilled=false;
		/* Put / after this asterisk to enable debug enemy pacification /**/
		if (Debug.isSet()&&health!=0)
			{
				out.println("DEBUG : Hostis volat oppugnare, sed facit nihil!");
			}
		else
		/**/
		if (health!=0)
		{
			if (dmg<1)
			{
				if (Translation.language.equals("Latina"))
					out.println("Hostis oppugnat sed deest!");
				else if (Translation.language.equals("English"))
					out.println("The enemy attacks but misses!");
			}
			else if (Ludus.player.dodge())
			{
				if (Translation.language.equals("Latina"))
					out.println("Hostis oppugnat sed tu devitas!");
				else if (Translation.language.equals("English"))
					out.println("The enemy attacks but you dodge!");
			}
			else
				{
					if (Translation.language.equals("Latina"))
						out.println("Hostis oppugnat, et efficit "+dmg+" detrimenta tibi!");
					else if (Translation.language.equals("English"))
						out.println("The enemy attacks, and causes "+dmg+" damage to you!");
					Ludus.player.hit(dmg);
					Ludus.player.strength--;
					if (Ludus.player.health==0)
					{
						if (Translation.language.equals("Latina"))
							out.println("Hostis te debilitat!\n");
						else if (Translation.language.equals("English"))
							out.println("The enemy incapacitates you!\n");
						playerKilled=true;
					}
				}
		}
		return playerKilled;
	}
	public boolean flees(int needs)
	{
		/* Put / after this asterisk to enable easy fleeing debug /**
		if (Debug.isSet())
			needs=5;
		/**/
		if (Roll.d20()>=needs)
		{
			this.fled=true;
			this.health=0;
			return true;
		}
		return false;
	}
	public int damage()
	{
		return (Roll.d20()-(10-this.strength));
	}
	public String toString()
	{
		return returnData();
	}
}