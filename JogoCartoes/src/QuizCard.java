
public class QuizCard
{

	private String question;

	private String answer;

	public QuizCard(String q, String a)
	{
		setQuestion(q);
		setAnswer(a);
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion(String question)
	{
		this.question = question;
	}

	public String getAnswer()
	{
		return answer;
	}

	public void setAnswer(String answer)
	{
		this.answer = answer;
	}
}
