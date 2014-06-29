package nl.jssl.autounit;
import junit.framework.Assert;

import nl.jssl.autounit.testclasses.IntArguments;

import org.junit.Test;

public class ManualTest {
    @Test
    public void testEven() {
        Assert.assertEquals("even", new IntArguments().evenOrUneven(2));
    }

    @Test
    public void testUneven() {
        Assert.assertEquals("uneven", new IntArguments().evenOrUneven(3));
    }

}
