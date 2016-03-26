public class Roll
{
	public static int dice(int min,int max)
	{
		return (int)(Math.random()*(max-min+1)+min);
	}
	public static int d20()
	{
		return dice(1,20);
	}
}