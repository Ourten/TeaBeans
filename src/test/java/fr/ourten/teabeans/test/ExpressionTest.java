package fr.ourten.teabeans.test;

import fr.ourten.teabeans.binding.Expression;
import fr.ourten.teabeans.property.Property;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ourten 15 oct. 2016
 */
public class ExpressionTest
{
    @Test
    public void testTransformExpression()
    {
        Property<Integer> p1 = new Property<>(2);

        Expression<Integer> transform = Expression.transform(p1, (n1) -> n1 * 4);

        assertThat(transform.getValue()).isEqualTo(8);

        p1.setValue(null);

        assertThat(transform.getValue()).isNull();
    }

    @Test
    public void testConstantExpression()
    {
        Property<Integer> p1 = new Property<>(2);

        Expression<Integer> multiplyConstant = Expression.constantCombine(p1, 2, (n1, n2) -> n1 * n2);

        assertThat(multiplyConstant.getValue()).isEqualTo(4);

        p1.setValue(null);

        assertThat(multiplyConstant.getValue()).isNull();
    }

    @Test
    public void testBiExpression()
    {
        Property<Integer> p1 = new Property<>(2);
        Property<Integer> p2 = new Property<>(3);

        Expression<Integer> multiply = Expression.biCombine(p1, p2, (n1, n2) -> n1 * n2);

        assertThat(multiply.getValue()).isEqualTo(6);

        p2.setValue(null);

        assertThat(multiply.getValue()).isNull();
    }

    @Test
    public void testTriExpression()
    {
        Property<Integer> p1 = new Property<>(2);
        Property<Integer> p2 = new Property<>(3);
        Property<Integer> p3 = new Property<>(4);

        Expression<Integer> multiply = Expression.triCombine(p1, p2, p3, (n1, n2, n3) -> n1 * n2 * n3);

        assertThat(multiply.getValue()).isEqualTo(24);

        p2.setValue(null);

        assertThat(multiply.getValue()).isNull();
    }

    @Test
    public void testTetraExpression()
    {
        Property<Integer> p1 = new Property<>(2);
        Property<Integer> p2 = new Property<>(3);
        Property<Integer> p3 = new Property<>(4);
        Property<Integer> p4 = new Property<>(5);

        Expression<Integer> multiply = Expression.tetraCombine(p1, p2, p3, p4,
                (n1, n2, n3, n4) -> n1 * n2 * n3 * n4);

        assertThat(multiply.getValue()).isEqualTo(120);

        p2.setValue(null);

        assertThat(multiply.getValue()).isNull();
    }

    @Test
    public void testPetaExpression()
    {
        Property<Integer> p1 = new Property<>(2);
        Property<Integer> p2 = new Property<>(3);
        Property<Integer> p3 = new Property<>(4);
        Property<Integer> p4 = new Property<>(5);
        Property<Integer> p5 = new Property<>(6);

        Expression<Integer> multiply = Expression.petaCombine(p1, p2, p3, p4, p5,
                (n1, n2, n3, n4, n5) -> n1 * n2 * n3 * n4 * n5);

        assertThat(multiply.getValue()).isEqualTo(720);

        p2.setValue(null);

        assertThat(multiply.getValue()).isNull();
    }

    @Test
    public void testHexaExpression()
    {
        Property<Integer> p1 = new Property<>(2);
        Property<Integer> p2 = new Property<>(3);
        Property<Integer> p3 = new Property<>(4);
        Property<Integer> p4 = new Property<>(5);
        Property<Integer> p5 = new Property<>(6);
        Property<Integer> p6 = new Property<>(7);

        Expression<Integer> multiply = Expression.hexaCombine(p1, p2, p3, p4, p5, p6,
                (n1, n2, n3, n4, n5, n6) -> n1 * n2 * n3 * n4 * n5 * n6);

        assertThat(multiply.getValue()).isEqualTo(5040);

        p2.setValue(null);

        assertThat(multiply.getValue()).isNull();
    }
}