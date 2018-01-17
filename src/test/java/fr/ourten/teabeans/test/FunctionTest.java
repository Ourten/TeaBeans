package fr.ourten.teabeans.test;

import fr.ourten.teabeans.function.HexaFunction;
import fr.ourten.teabeans.function.PetaFunction;
import fr.ourten.teabeans.function.TetraFunction;
import fr.ourten.teabeans.function.TriFunction;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctionTest
{
    Function<Boolean, Boolean> f;

    @Before
    public void setup()
    {
        f = (b) -> !b;
    }

    @Test(expected = NullPointerException.class)
    public void testTriFunctionAndThenNull()
    {
        TriFunction<Boolean, Boolean, Byte, Boolean> function = (a, b, c) -> false;

        function.andThen(null);
    }

    @Test()
    public void testTriFunctionAndThenNotNull()
    {
        TriFunction<Boolean, Boolean, Byte, Boolean> function = (a, b, c) -> false;

        Boolean actual = function.andThen(f).apply(true, true, (byte) 1);

        assertThat(actual).isTrue();
    }

    @Test(expected = NullPointerException.class)
    public void testTetraFunctionAndThenNull()
    {
        TetraFunction<Boolean, Boolean, Boolean, Byte, Boolean> function = (a, b, c, d) -> false;

        function.andThen(null);
    }

    @Test()
    public void testTetraFunctionAndThenNotNull()
    {
        TetraFunction<Boolean, Boolean, Boolean, Byte, Boolean> function = (a, b, c, d) -> false;

        Boolean actual = function.andThen(f).apply(true, true, true, (byte) 1);

        assertThat(actual).isTrue();
    }

    @Test(expected = NullPointerException.class)
    public void testPetaFunctionAndThenNull()
    {
        PetaFunction<Boolean, Boolean, Boolean, Boolean, Byte, Boolean> function = (a, b, c, d, e) -> false;

        function.andThen(null);
    }

    @Test()
    public void testPetaFunctionAndThenNotNull()
    {
        PetaFunction<Boolean, Boolean, Boolean, Boolean, Byte, Boolean> function = (a, b, c, d, e) -> false;

        Boolean actual = function.andThen(f).apply(true, true, true, true, (byte) 1);

        assertThat(actual).isTrue();
    }

    @Test(expected = NullPointerException.class)
    public void testHexaFunctionAndThenNull()
    {
        HexaFunction<Boolean, Boolean, Boolean, Boolean, Boolean, Byte, Boolean> function = (a, b, c, d, e, f) -> false;

        function.andThen(null);
    }

    @Test()
    public void testHexaFunctionAndThenNotNull()
    {
        HexaFunction<Boolean, Boolean, Boolean, Boolean, Boolean, Byte, Boolean> function = (a, b, c, d, e, f) -> false;

        Boolean actual = function.andThen(f).apply(true, true, true, true, true, (byte) 1);

        assertThat(actual).isTrue();
    }
}
