package nl.jssl.autounit.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PairTests {
	@Test
	public void testDepth1() {
		LinkedList p = new LinkedList("1");
		assertEquals(1, p.depth());
	}

	@Test
	public void testDepth2() {
		LinkedList p = new LinkedList(new LinkedList("1"));
		assertEquals(2, p.depth());
	}

	@Test
	public void testDepth3() {
		LinkedList p = new LinkedList(new LinkedList(new LinkedList("1")));
		assertEquals(3, p.depth());
	}
}
