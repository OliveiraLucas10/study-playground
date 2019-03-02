package exer_4;

public class prog
{

	private static boolean open = true;

	private static boolean close = false;

	private static int numLockers = 100;

	static boolean[] lockersArray = new boolean[numLockers]; // automaticamente ele já é criado com valores false

	public static void main(String[] arg)
	{
		System.out.println("Hello world");

		// inicializando o array
		for (int x = 1; x <= numLockers; x++)
		{
			if (x == 1) // estudante 1
			{
				for (int y = 1; y <= numLockers; y++)
				{
					setValue(y, open);
				}
			}
			else if (x == 2) // estudante 2
			{
				for (int k = 2; k <= numLockers; k++)
				{
					setValue(k, close);
				}
			}
			else
			{
				for (int z = x; z <= numLockers; z += x) // estudante x e vai de x em x (Ex: 3, 6, 9)
				{
					setValue(z);
				}
			}

		}

		printResult();

	}

	public static void setValue(int position, boolean value) // seta o boolean do parametro na posicao do parametro
	{
		// obs: posicao - 1, pq o array comeca em 0 e vai até 99, mas estamos usando o 1 para inicializar o for
		lockersArray[position - 1] = value;
	}

	public static void setValue(int position)
	{
		// obs: posicao - 1, pq o array comeca em 0 e vai até 99, mas estamos usando o 1 para inicializar o for
		boolean b = lockersArray[position - 1]; // busca o boolean da posicao do parametro
		setValue(position, !b); // chama o metodo mais abaixo repasando a posicao por parametro e o valor anterior negado
	}

	public static void printResult()
	{

		for (int x = 1; x <= numLockers; x++)
		{
			System.out.println("Locker " + x + " is " + (lockersArray[x - 1] == true ? "open" : "close"));
		}
	}

}
