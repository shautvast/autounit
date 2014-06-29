package nl.jssl.autounit.testclasses;

public class IntArguments {
	public String evenOrUneven(int number) {
		if (number % 2 == 0)
			return "even";
		else
			return "uneven";
	}

	public int randomOther(int x) {
		return (int) Math.random() * 1000;
	}
}
