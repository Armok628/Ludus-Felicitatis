import static java.lang.System.*;
public class Attempt
{
	public static Battle currentFight;
	public static int searchRoll;
	public static void toFindEnemies()
	{
		searchRoll=Roll.d20();
		Ludus.player.rest();
		out.println(Ludus.player+"\n");
		/*Put / after this asterisk to enable debug test battle*
		if (Debug.isSet())
		{
			if (Translation.language.equals("Latina"))
				out.print("Volasne testaris proelium? ");
			else if (Translation.language.equals("English"))
				out.print("Do you want to test a battle? ");
			if (GetInput.yes())
			{
				out.println("min, max, pow, var");
				int min = GetInput.integer();
				int max = GetInput.integer();
				int pow = GetInput.integer();
				int var = GetInput.integer();
				currentFight = new Battle(min,max,pow,var);
				Attempt.toDoBattle();
				out.println("fin\n\n");
			}
		}
		/**/
		/*Put / after this asterisk to enable debug roll setting*/
		if (Debug.isSet())
		{
			out.print("\nsearchRoll=");
			searchRoll=GetInput.integer();
			out.println();
		}
		/**/
		if (searchRoll>=9&&searchRoll<=11)
		{
			if (Translation.language.equals("Latina"))
				out.println("\nDispicis neminem.\n");
			else if (Translation.language.equals("English"))
				out.println("\nYou find nobody.\n");
		}
		else if (searchRoll>15&&searchRoll!=20)
		{
			if (Translation.language.equals("Latina"))
			{
				out.println("Dispicis infirmum inimicum.");
				out.print("Volas intrare proelium? ");
			}
			else if (Translation.language.equals("English"))
			{
				out.println("You find a weak enemy.");
				out.print("Do you want to enter battle? ");
			}
			if (GetInput.yes())
			{
				out.println();
				currentFight = new Battle(1,1,(20-searchRoll),1);
				Attempt.toDoBattle();
			}
		}
		else if (searchRoll<5&&searchRoll!=1)
		{
			if (Translation.language.equals("Latina"))
			{
				out.println("Dispicis nonnullos inimicos!!");
				out.print("Volas conari fugere? ");
			}
			else if (Translation.language.equals("English"))
			{
				out.println("You find some enemies!!");
				out.print("Do you want to try to flee? ");
			}
			if (GetInput.no())
			{
				out.println();
				currentFight = new Battle(2,(6-searchRoll),(20-searchRoll),2);
				Attempt.toDoBattle();
			}
			else
				if (!Ludus.player.flees(20-searchRoll))
				{
					out.println();
					currentFight = new Battle(1,(6-searchRoll),(20-searchRoll),2);
					Attempt.toDoBattle();
				}
		}
		else if (searchRoll==20)
		{
			if (Translation.language.equals("Latina"))
				out.println("Dispicis mortum inimicum...\n");
			if (Translation.language.equals("English"))
				out.println("You find a dead enemy...\n");
			int foundGold = Roll.d20();
			if (Translation.language.equals("Latina"))
				out.println("Praedaris "+foundGold+" denarii.\n");
			if (Translation.language.equals("English"))
				out.println("You loot "+foundGold+" gold.\n");
			Ludus.player.gold+=foundGold;
		}
		else if (searchRoll==1)
		{
			if (Translation.language.equals("Latina"))
				out.println("Dispicis multissimos inimicos!\nInsidia accidit! Tu non potes fugere nunc!\n");
			else if (Translation.language.equals("English"))
				out.println("You find a great many enemies!\nAn ambush occurs! You are not able to flee now!\n");
			currentFight = new Battle(5,15,(20-searchRoll),3);
			Attempt.toDoBattle();
		}
		else
		{
			if (Translation.language.equals("Latina"))
			{
				out.println("Dispicis inimicum!");
				out.print("Volas intrare proelium aut conari fugere? (pugna/fuge) ");
				if (GetInput.string().equals("pugna")||!Ludus.player.flees(20-searchRoll))
				{
					out.println();
					currentFight = new Battle(1,1,(20-searchRoll),3);
					Attempt.toDoBattle();
				}
			}
			else if (Translation.language.equals("English"))
			{
				out.println("You find an enemy!");
				out.print("Do you want to enter battle or to try to flee? (fight/flee) ");
				if (GetInput.string().equals("fight")||!Ludus.player.flees(20-searchRoll))
				{
					out.println();
					currentFight = new Battle(1,1,(20-searchRoll),3);
					Attempt.toDoBattle();
				}
			}
		}
	}
	public static void toDoBattle()
	{
		boolean playerDead=false;
		Ludus.player.fled=false;
		if (currentFight.allGone())
		{
			int foundGold = Roll.d20();
			if (Translation.language.equals("Latina"))
				out.println("Omnes hostes iam mortui sunt!\nPraedaris "+foundGold+" denarii.\n");
			else if (Translation.language.equals("English"))
				out.println("All of the enemies are already dead!\nYou loot "+foundGold+" gold.\n");
			Ludus.player.gold+=foundGold;
		}
		else
		{
			if (Translation.language.equals("Latina"))
				out.print("Volasne oppugnare? ");
			else if (Translation.language.equals("English"))
				out.print("Do you want to attack? ");
			if (GetInput.yes()||!Ludus.player.flees(20-searchRoll))
			{
				int tgt=1;
				do
				{
					out.println();
					if (currentFight.size>1)
					{
						if (Translation.language.equals("Latina"))
							out.print("Qualem hostem oppugnas? (1-"+(currentFight.size)+") ");
						else if (Translation.language.equals("English"))
							out.print("Which enemy do you attack? (1-"+(currentFight.size)+") ");
						tgt = GetInput.integer();
					}
					currentFight.damageCreature(tgt,Ludus.player.damage());
					out.println();
					currentFight.strengthCheck();
					currentFight.enemiesFlee();
					if (currentFight.allGone())
					{
						if (Translation.language.equals("Latina"))
							out.println("\nOmnes hostes mortui sunt aut fugerunt!\n");
						if (Translation.language.equals("English"))
							out.println("\nAll of the enemies are dead or have fled!\n");
						break;
					}
					else
						for (Creature c : currentFight.enemies)
						{
							if (c.hurtPlayer())
							{
								playerDead=true;
								break;
							}
						}
					if (playerDead)
						break;
					out.println("\n"+currentFight);
					if (Translation.language.equals("Latina"))
						out.print("Iterum? ");
					if (Translation.language.equals("English"))
						out.print("Again? ");
				}
				while (GetInput.yes()||!Ludus.player.flees(20-searchRoll));
				if (!playerDead&&!Ludus.player.fled)
				{
					if (Translation.language.equals("Latina"))
						out.println("Praedaris "+Ludus.player.loot(Roll.d20())+" denarii.\n");
					else if (Translation.language.equals("English"))
						out.println("You loot "+Ludus.player.loot(Roll.d20())+" gold.\n");
				}
				else if (!Ludus.player.fled)
				{
					if (Translation.language.equals("Latina"))
						out.println("Hostes praedatur "+Attempt.currentFight.enemiesLoot()+" denarii.\n");
					else if (Translation.language.equals("English"))
						out.println("The enemies loot "+Attempt.currentFight.enemiesLoot()+" gold.\n");
				}
			}
		}
	}
	public static void toUpgrade()
	{
		if (Ludus.player.gold>20)
		{
			Ludus.player.maxStrength+=(Ludus.player.gold-20)*(Roll.d20()/10);
			if (Translation.language.equals("Latina"))
				out.println("Maximum fortitudinis habere potes est "+Ludus.player.maxStrength+" nunc!");
			else if (Translation.language.equals("English"))
				out.println("The maximum strength you are able to have is "+Ludus.player.maxStrength+" now!");
			Ludus.player.gold=0;
		}
		else
		{
			if (Translation.language.equals("Latina"))
				out.println("Tu non habes satis denarios pro melius fieri!");
			else if (Translation.language.equals("English"))
				out.println("You do not have enough money to upgrade yourself!");
		}
	}
}