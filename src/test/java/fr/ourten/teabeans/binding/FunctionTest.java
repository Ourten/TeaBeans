package fr.ourten.teabeans.binding;

import fr.ourten.teabeans.function.HexaFunction;
import fr.ourten.teabeans.function.PetaFunction;
import fr.ourten.teabeans.function.TetraFunction;
import fr.ourten.teabeans.function.TriFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FunctionTest
{
    Function<Boolean, Boolean> f;

    @BeforeEach
    public void setup()
    {
        f = (b) -> !b;
    }

    @Test
    public void testTriFunctionAndThenNull()
    {
        TriFunction<Boolean, Boolean, Byte, Boolean> function = (a, b, c) -> false;

        assertThrows(NullPointerException.class, () -> function.andThen(null));
    }

    @Test()
    public void testTriFunctionAndThenNotNull()
    {
        TriFunction<Boolean, Boolean, Byte, Boolean> function = (a, b, c) -> false;

        Boolean actual = function.andThen(f).apply(true, true, (byte) 1);

        assertThat(actual).isTrue();
    }

    @Test
    public void testTetraFunctionAndThenNull()
    {
        TetraFunction<Boolean, Boolean, Boolean, Byte, Boolean> function = (a, b, c, d) -> false;

        assertThrows(NullPointerException.class, () -> function.andThen(null));
    }

    @Test()
    public void testTetraFunctionAndThenNotNull()
    {
        TetraFunction<Boolean, Boolean, Boolean, Byte, Boolean> function = (a, b, c, d) -> false;

        Boolean actual = function.andThen(f).apply(true, true, true, (byte) 1);

        assertThat(actual).isTrue();
    }

    @Test
    public void testPetaFunctionAndThenNull()
    {
        PetaFunction<Boolean, Boolean, Boolean, Boolean, Byte, Boolean> function = (a, b, c, d, e) -> false;

        assertThrows(NullPointerException.class, () -> function.andThen(null));
    }

    @Test()
    public void testPetaFunctionAndThenNotNull()
    {
        PetaFunction<Boolean, Boolean, Boolean, Boolean, Byte, Boolean> function = (a, b, c, d, e) -> false;

        Boolean actual = function.andThen(f).apply(true, true, true, true, (byte) 1);

        assertThat(actual).isTrue();
    }

    @Test
    public void testHexaFunctionAndThenNull()
    {
        HexaFunction<Boolean, Boolean, Boolean, Boolean, Boolean, Byte, Boolean> function = (a, b, c, d, e, f) -> false;

        assertThrows(NullPointerException.class, () -> function.andThen(null));
    }

    @Test()
    public void testHexaFunctionAndThenNotNull()
    {
        HexaFunction<Boolean, Boolean, Boolean, Boolean, Boolean, Byte, Boolean> function = (a, b, c, d, e, f) -> false;

        Boolean actual = function.andThen(f).apply(true, true, true, true, true, (byte) 1);

        assertThat(actual).isTrue();
    }
}
