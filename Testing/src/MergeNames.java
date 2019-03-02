import java.util.TreeSet;

public class MergeNames
{

	public static String[] uniqueNames(String[] names1, String[] names2)
	{
		TreeSet<String> ts = new TreeSet<String>();

		for (String string : names2)
		{
			ts.add(string);
		}

		for (String string : names1)
		{
			ts.add(string);
		}

		String[] result = ts.toArray(new String[ts.size()]);

		return result;
	}

	public static void main(String[] args)
	{
		String[] names1 = new String[] {
			"Ava",
			"Emma",
			"Olivia"
		};
		String[] names2 = new String[] {
			"Olivia",
			"Sophia",
			"Emma"
		};
		System.out.println(String.join(", ", MergeNames.uniqueNames(names1, names2)));
		// should print Ava, Emma, Olivia, Sophia
	}
}
