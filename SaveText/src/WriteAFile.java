import java.io.FileWriter;

public class WriteAFile
{

	public static void main(String[] args)
	{
		try
		{
			FileWriter writer = new FileWriter("Foo.txt");

			writer.write("Hello foo!");

			writer.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}
}
