import static java.lang.System.*;
import java.util.Scanner;
import java.util.InputMismatchException;
public class GetInput
{
	static Scanner input = new Scanner(System.in);
	private static boolean exception = true;
	private static int num = 0;
	public static String string()
	{
		return input.next();
	}
	public static int integer()
	{
		exception=true;
		do
		{
			try
			{
				num = input.nextInt();
				exception=false;
			}
			catch (InputMismatchException e)
			{
				if (Translation.language.equals("Latina"))
					out.print("Scribe numerum! ");
				else if (Translation.language.equals("English"))
					out.print("Write a number! ");
				input.next();
			}
		}
		while (exception);
		return num;
	}
	public static boolean yes()
	{
		String s = input.next();
		if (Translation.language.equals("Latina"))
		{
			if (!s.equals("ita")&&!s.equals("minime")&&!s.equals("Ita")&&!s.equals("Minime")&&!s.equals("i")&&!s.equals("m")&&!s.equals("I")&&!s.equals("M"))
				do
				{
					if (s.equals("yes")||s.equals("no")||s.equals("Yes")||s.equals("No")||s.equals("y")||s.equals("n")||s.equals("Y")||s.equals("N"))
						out.print("Dice in latine! ");
					else
						out.print("Quid dicabas? ");
					s = input.next();
				}
				while (!s.equals("ita")&&!s.equals("minime")&&!s.equals("Ita")&&!s.equals("Minime")&&!s.equals("i")&&!s.equals("m")&&!s.equals("I")&&!s.equals("M"));
			if (s.equals("ita")||s.equals("Ita")||s.equals("i")||s.equals("I"))
				return true;
			else
				return false;
		}
		else if (Translation.language.equals("English"))
		{
			if (!s.equals("yes")&&!s.equals("no")&&!s.equals("Yes")&&!s.equals("No")&&!s.equals("y")&&!s.equals("n")&&!s.equals("Y")&&!s.equals("N"))
				do
				{
					if (s.equals("ita")||s.equals("minime")||s.equals("Ita")||s.equals("Minime")||s.equals("i")||s.equals("m")||s.equals("I")||s.equals("M"))
						out.print("Speak English! ");
					else
						out.print("What did you say? ");
					s = input.next();
				}
				while (!s.equals("yes")&&!s.equals("no")&&!s.equals("Yes")&&!s.equals("No")&&!s.equals("y")&&!s.equals("n")&&!s.equals("Y")&&!s.equals("N"));
			if (s.equals("yes")||s.equals("Yes")||s.equals("y")||s.equals("Y"))
				return true;
			else
				return false;
		}
		return false;
	}
	public static boolean no()
	{
		return !yes();
	}
}