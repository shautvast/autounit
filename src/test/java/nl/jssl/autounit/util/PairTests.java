package nl.jssl.autounit.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PairTests {
	@Test
	public void testDepth1() {
		Pair p = new Pair("1");
		assertEquals(1, p.depth());
	}

	@Test
	public void testDepth2() {
		Pair p = new Pair(new Pair("1"));
		assertEquals(2, p.depth());
	}

	@Test
	public void testDepth3() {
		Pair p = new Pair(new Pair(new Pair("1")));
		assertEquals(3, p.depth());
	}
}
