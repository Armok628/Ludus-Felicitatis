import java.util.Scanner;
import static java.lang.System.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
public class SaveGame
{
	public static Scanner scan;
	public static File save;
	public static FileWriter saver;
	public static void save() throws IOException
	{
		save = new File(Ludus.player.name+".sav");
		try
		{
			if (save.createNewFile())
			{
				save.delete();
				save.createNewFile();
			}
			saver = new FileWriter(save);
			saver.write(	Ludus.player.health+"\n"
					+Ludus.player.strength+"\n"
					+Ludus.player.gold+"\n"
					+Ludus.player.maxHealth+"\n"
					+Ludus.player.maxStrength);
			saver.flush();
			saver.close();
		}
		catch (IOException e)
		{
			if (Translation.language.equals("Latina"))
				out.println("Error erat dum tu conatus es servavisse!\n"+e+"\n");
			else if (Translation.language.equals("English"))
				out.println("There was an error while saving!\n"+e+"\n");
		}
	}
	public static void load() throws FileNotFoundException,InputMismatchException
	{
		try
		{
			scan = new Scanner(new File(Ludus.player.name+".sav"));
			Ludus.player.health=scan.nextInt();
			Ludus.player.strength=scan.nextInt();
			Ludus.player.gold=scan.nextInt();
			Ludus.player.maxHealth=scan.nextInt();
			Ludus.player.maxStrength=scan.nextInt();
			if (Translation.language.equals("Latina"))
				out.println("\nSalve iterum!");
			else if (Translation.language.equals("English"))
				out.println("\nHello again!");
		}
		catch (FileNotFoundException e)
		{
			if (Translation.language.equals("Latina"))
				out.println("\nSalve, "+Ludus.player.name+"!");
			else if (Translation.language.equals("English"))
				out.println("\nHello, "+Ludus.player.name+"!");
		}
		catch (InputMismatchException e)
		{
			if (Translation.language.equals("Latina"))
				out.println("Error erat dum tu conatus es rementus esse!\n"+e+"\n");
			if (Translation.language.equals("English"))
				out.println("There was an error while loading!\n"+e+"\n");
		}
		catch (NoSuchElementException e)
		{
			if (Translation.language.equals("Latina"))
				out.println("Error erat dum tu conatus es rementus esse!\n"+e+"\n");
			if (Translation.language.equals("English"))
				out.println("There was an error while loading!\n"+e+"\n");
		}
	}
}