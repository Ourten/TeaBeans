package fr.ourten.teabeans.test;

import java.util.function.Function;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.ourten.teabeans.function.HexaFunction;
import fr.ourten.teabeans.function.PetaFunction;
import fr.ourten.teabeans.function.TetraFunction;
import fr.ourten.teabeans.function.TriFunction;

public class FunctionTest
{
    Function<Boolean, Boolean> f;

    @Before
    public void setup()
    {
        f = (b) ->
        {
            return !b;
        };
    }

    @Test(expected = NullPointerException.class)
    public void testTriFunctionAndThenNull()
    {
        TriFunction<Boolean, Boolean, Byte, Boolean> function = (a, b, c) ->
        {
            return false;
        };

        function.andThen(null);
    }

    @Test()
    public void testTriFunctionAndThenNotNull()
    {
        TriFunction<Boolean, Boolean, Byte, Boolean> function = (a, b, c) ->
        {
            return false;
        };

        Boolean actual = function.andThen(f).apply(true, true, (byte) 1);

        Assert.assertTrue(actual);
    }

    @Test(expected = NullPointerException.class)
    public void testTetraFunctionAndThenNull()
    {
        TetraFunction<Boolean, Boolean, Boolean, Byte, Boolean> function = (a, b, c, d) ->
        {
            return false;
        };

        function.andThen(null);
    }

    @Test()
    public void testTetraFunctionAndThenNotNull()
    {
        TetraFunction<Boolean, Boolean, Boolean, Byte, Boolean> function = (a, b, c, d) ->
        {
            return false;
        };

        Boolean actual = function.andThen(f).apply(true, true, true, (byte) 1);

        Assert.assertTrue(actual);
    }

    @Test(expected = NullPointerException.class)
    public void testPetaFunctionAndThenNull()
    {
        PetaFunction<Boolean, Boolean, Boolean, Boolean, Byte, Boolean> function = (a, b, c, d, e) ->
        {
            return false;
        };

        function.andThen(null);
    }

    @Test()
    public void testPetaFunctionAndThenNotNull()
    {
        PetaFunction<Boolean, Boolean, Boolean, Boolean, Byte, Boolean> function = (a, b, c, d, e) ->
        {
            return false;
        };

        Boolean actual = function.andThen(f).apply(true, true, true, true, (byte) 1);

        Assert.assertTrue(actual);
    }

    @Test(expected = NullPointerException.class)
    public void testHexaFunctionAndThenNull()
    {
        HexaFunction<Boolean, Boolean, Boolean, Boolean, Boolean, Byte, Boolean> function = (a, b, c, d, e, f) ->
        {
            return false;
        };

        function.andThen(null);
    }

    @Test()
    public void testHexaFunctionAndThenNotNull()
    {
        HexaFunction<Boolean, Boolean, Boolean, Boolean, Boolean, Byte, Boolean> function = (a, b, c, d, e, f) ->
        {
            return false;
        };

        Boolean actual = function.andThen(f).apply(true, true, true, true, true, (byte) 1);

        Assert.assertTrue(actual);
    }
}
