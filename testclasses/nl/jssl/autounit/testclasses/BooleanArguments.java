package nl.jssl.autounit.testclasses;

public class BooleanArguments {
	private String getText(boolean b) {
		return b ? "true" : "false";
	}

	public String getText(boolean a, boolean b) {
		return getText(a) + getText(b);
	}
}
