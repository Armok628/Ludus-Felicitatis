import static java.lang.System.*;
public class Translation
{
	public static String language="Latina";
	public static void setLang(String lang)
	{
		if (lang.equals("English")||lang.equals("Latina"))
			language=lang;
		else
		{
			out.println("Hic ludus non habet "+lang+" lingua.");
		}
	}
}
