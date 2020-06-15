package fr.ourten.teabeans.test;

import fr.ourten.teabeans.property.Property;
import fr.ourten.teabeans.value.ObservableValue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ourten 15 oct. 2016
 */
public class ExpressionTest
{
    @Test
    public void testTransformExpression()
    {
        Property<Integer> property = new Property<>(2);

        ObservableValue<Integer> transform = property.map(value -> value * 4);

        assertThat(transform.getValue()).isEqualTo(8);

        testExpressionNullability(transform, singletonList(property));
    }

    @Test
    public void testConstantExpression()
    {
        Property<Integer> property = new Property<>(2);

        ObservableValue<Integer> multiplyConstant = property.map(2, (value, constant) -> value * constant);

        assertThat(multiplyConstant.getValue()).isEqualTo(4);

        testExpressionNullability(multiplyConstant, singletonList(property));
    }

    @Test
    public void testBiExpression()
    {
        Property<Integer> property1 = new Property<>(2);
        Property<Integer> property2 = new Property<>(3);

        ObservableValue<Integer> multiply = property1.combine(property2, (value1, value2) -> value1 * value2);

        assertThat(multiply.getValue()).isEqualTo(6);

        testExpressionNullability(multiply, asList(property1, property2));
    }

    @Test
    public void testTriExpression()
    {
        Property<Integer> property1 = new Property<>(2);
        Property<Integer> property2 = new Property<>(3);
        Property<Integer> property3 = new Property<>(4);

        ObservableValue<Integer> multiply = property1.combine(property2, property3,
                (value1, value2, value3) -> value1 * value2 * value3);

        assertThat(multiply.getValue()).isEqualTo(24);

        testExpressionNullability(multiply, asList(property1, property2, property3));
    }

    @Test
    public void testTetraExpression()
    {
        Property<Integer> property1 = new Property<>(2);
        Property<Integer> property2 = new Property<>(3);
        Property<Integer> property3 = new Property<>(4);
        Property<Integer> property4 = new Property<>(5);

        ObservableValue<Integer> multiply = property1.combine(property2, property3, property4,
                (value1, value2, value3, value4) -> value1 * value2 * value3 * value4);

        assertThat(multiply.getValue()).isEqualTo(120);

        testExpressionNullability(multiply, asList(property1, property2, property3, property4));
    }

    @Test
    public void testPetaExpression()
    {
        Property<Integer> property1 = new Property<>(2);
        Property<Integer> property2 = new Property<>(3);
        Property<Integer> property3 = new Property<>(4);
        Property<Integer> property4 = new Property<>(5);
        Property<Integer> property5 = new Property<>(6);

        ObservableValue<Integer> multiply = property1.combine(property2, property3, property4, property5,
                (value1, value2, value3, value4, value5) -> value1 * value2 * value3 * value4 * value5);

        assertThat(multiply.getValue()).isEqualTo(720);

        testExpressionNullability(multiply, asList(property1, property2, property3, property4, property5));
    }

    @Test
    public void testHexaExpression()
    {
        Property<Integer> property1 = new Property<>(2);
        Property<Integer> property2 = new Property<>(3);
        Property<Integer> property3 = new Property<>(4);
        Property<Integer> property4 = new Property<>(5);
        Property<Integer> property5 = new Property<>(6);
        Property<Integer> property6 = new Property<>(7);

        ObservableValue<Integer> multiply = property1.combine(property2, property3, property4, property5, property6,
                (value1, value2, value3, value4, value5, value6) -> value1 * value2 * value3 * value4 * value5 * value6);

        assertThat(multiply.getValue()).isEqualTo(5040);

        testExpressionNullability(multiply, asList(property1, property2, property3, property4, property5, property6));
    }

    private void testExpressionNullability(ObservableValue<Integer> expression, List<Property<Integer>> properties)
    {
        for (Property<Integer> property : properties)
        {
            int previous = property.getValue();
            property.setValue(null);

            assertThat(expression.getValue()).isNull();
            property.setValue(previous);
        }
    }
}