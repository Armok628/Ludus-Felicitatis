/* Put / after this asterisk and cut and paste somewhere to debug output * out.println("\n************\nOUTPUT DEBUG\n************\n");/**/
/**
 *		To-do list:
 *Urgency (3 - gamebreaking, 2 - hinders gameplay features, 1 - minor gameplay issue, 0 - non-issue task)
 *	2(wip)	1.	Write better fleeing mechanism less dependent on player strength
 *	1(wip)	2.	Write better upgrading mechanism (with chance to fail) less likely to approximate Thor
 *					More strength --> more difficulty upgrading (maximum?)
 *	1(done)	3.	Change debug system to be enabled when player name equals "DEBUG"
 *	0		4.	Comment confusing parts of code
 *					What parts, though?
 *	0		5.	Make finding multiple enemies more common
 *	0		6.	Modularize damage infliction and fleeing (BIG JOB)
 *					(id est) Attempt.toDoDamage(Creature source,Creature target)
 *	1		7.	Allow enemies to try to attack or try to flee, but never both
 *					Add possibility that enemies try to flee and fail
/**/
import static java.lang.System.*;
import java.io.FileNotFoundException;
import java.io.IOException;
public class Ludus
{
	public static Player player;
	public boolean inBattle = false;
	public static void main(String[] args)
	{
		out.println("Ludus Felicitatis, a M. Johanifilii \nJanuarius A.D. MMXVI ad nunc\n926 ordines scriptorum adhuc\n");
		/*Put / after this asterisk to enable debug features (DEPRECATED)*
		out.print("Volasne vidare si errores sunt? ");
		if (GetInput.yes())
			Debug.set(true);
			out.println();
		/**/
		out.print("Latina: Quid est tua lingua?\nEnglish: What is your language?\n");
		Translation.setLang(GetInput.string());
		if (Translation.language.equals("Latina"))
			out.print("\nQuid est nomen tuus? ");
		else if (Translation.language.equals("English"))
			out.print("\nWhat is your name? ");
		player = new Player(GetInput.string());
		/*Put / after this asterisk to enable debug features only for "DEBUG" player*/
		if (player.name.equals("DEBUG"))
			Debug.set(true);
		/**/
		try
		{
			SaveGame.load();
		}
		catch (IOException e)
		{
		}
		int menuChoice=0;
		if (Translation.language.equals("Latina"))
			out.print("\nVolas ludere? ");
		else if (Translation.language.equals("English"))
			out.print("\nDo you want to play? ");
		if (GetInput.yes())
			do
			{
				if (Translation.language.equals("Latina"))
					out.println("\n\n"
								+"\tScribe:\n"
								+"0 pro exire\n"
								+"1 pro quaerere inimicos\n"
								+"2 pro fortitudinem tuum melius facere\n"
								+"\n");
				else if (Translation.language.equals("English"))
					out.println("\n\n"
								+"\tType:\n"
								+"0 to exit\n"
								+"1 to seek enemies\n"
								+"2 to upgrade your strength\n"
								+"\n");
				menuChoice = GetInput.integer();
				switch(menuChoice)
				{
					case 0:
						break;
					case 1:
						do
						{
							out.println();
							Attempt.toFindEnemies();
							if (Translation.language.equals("Latina"))
								out.print("Quaerasne inimicos iterum? ");
							else if (Translation.language.equals("English"))
								out.print("Seek enemies again? ");
						}
						while (GetInput.yes());
						break;
					case 2:
						Attempt.toUpgrade();
						break;
					default:
						if (Translation.language.equals("Latina"))
							out.println("Recitatus tuus invalidus est!");
						else if (Translation.language.equals("English"))
							out.println("Your response is invalid!");
				}
				if (Translation.language.equals("Latina"))
					out.print("Ludesne iterum? ");
				else if (Translation.language.equals("English"))
					out.print("Play again? ");
			}
			while (GetInput.yes());
		/*Put / after this asterisk to disable saving for "DEBUG" player*/
		if (!player.name.equals("DEBUG"))
		/**/
			try
			{
				SaveGame.save();
			}
			catch (FileNotFoundException e)
			{
				if (Translation.language.equals("Latina"))
					out.println("Tu dees servare!");
				else if (Translation.language.equals("English"))
					out.println("You fail to save!");
			}
			catch (IOException e)
			{
				if (Translation.language.equals("Latina"))
					out.println("Tu dees servare!");
				else if (Translation.language.equals("English"))
					out.println("You fail to save!");
			}
		if (Translation.language.equals("Latina"))
			out.println("\nLudus concludatur!\n");
		else if (Translation.language.equals("English"))
			out.println("\nThe game is over!");
	}
}
