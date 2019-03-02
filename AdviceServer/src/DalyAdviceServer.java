import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DalyAdviceServer
{

	String[] adviceList = {
		"Morda pedaços menores.",
		"Use o jeans apertado. Não, ele NÃO faz você parecer gorda.",
		"Só vou dizer uma palavra: inapropriado.",
		"Pelo menos hoje, seja honesta. Diga a seu chefe o que realmente pensa.",
		"Reconsidere esse corte de cabelo."
	};

	public static void main(String[] args)
	{
		DalyAdviceServer server = new DalyAdviceServer();
		server.go();
	}

	public void go()
	{
		try
		{
			ServerSocket serverSock = new ServerSocket(4242);

			while (true)
			{
				Socket sock = serverSock.accept();

				PrintWriter writer = new PrintWriter(sock.getOutputStream());
				String advice = getAdvice();
				writer.println(advice);
				writer.close();
				System.out.println(advice);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private String getAdvice()
	{
		int random = (int) (Math.random() * adviceList.length);
		return adviceList[random];
	}
}
