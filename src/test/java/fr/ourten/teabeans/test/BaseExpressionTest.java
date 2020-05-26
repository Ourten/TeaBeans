package fr.ourten.teabeans.test;

import fr.ourten.teabeans.binding.BaseExpression;
import fr.ourten.teabeans.value.BaseProperty;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ourten 15 oct. 2016
 */
public class BaseExpressionTest
{
    @Test
    public void testTransformExpression()
    {
        final BaseProperty<Integer> p1 = new BaseProperty<>(2, "integerPropertyTest1");

        final BaseExpression<Integer> transform = BaseExpression.transform(p1, (n1) -> n1 * 4);

        assertThat(transform.getValue()).isEqualTo(8);

        p1.setValue(null);

        assertThat(transform.getValue()).isNull();
    }

    @Test
    public void testConstantExpression()
    {
        final BaseProperty<Integer> p1 = new BaseProperty<>(2, "integerPropertyTest1");

        final BaseExpression<Integer> multiplyConstant = BaseExpression.constantCombine(p1, 2, (n1, n2) -> n1 * n2);

        assertThat(multiplyConstant.getValue()).isEqualTo(4);

        p1.setValue(null);

        assertThat(multiplyConstant.getValue()).isNull();
    }

    @Test
    public void testBiExpression()
    {
        final BaseProperty<Integer> p1 = new BaseProperty<>(2, "integerPropertyTest1");
        final BaseProperty<Integer> p2 = new BaseProperty<>(3, "integerPropertyTest2");

        final BaseExpression<Integer> multiply = BaseExpression.biCombine(p1, p2, (n1, n2) -> n1 * n2);

        assertThat(multiply.getValue()).isEqualTo(6);

        p2.setValue(null);

        assertThat(multiply.getValue()).isNull();
    }

    @Test
    public void testTriExpression()
    {
        final BaseProperty<Integer> p1 = new BaseProperty<>(2, "integerPropertyTest1");
        final BaseProperty<Integer> p2 = new BaseProperty<>(3, "integerPropertyTest2");
        final BaseProperty<Integer> p3 = new BaseProperty<>(4, "integerPropertyTest3");

        final BaseExpression<Integer> multiply = BaseExpression.triCombine(p1, p2, p3, (n1, n2, n3) -> n1 * n2 * n3);

        assertThat(multiply.getValue()).isEqualTo(24);

        p2.setValue(null);

        assertThat(multiply.getValue()).isNull();
    }

    @Test
    public void testTetraExpression()
    {
        final BaseProperty<Integer> p1 = new BaseProperty<>(2, "integerPropertyTest1");
        final BaseProperty<Integer> p2 = new BaseProperty<>(3, "integerPropertyTest2");
        final BaseProperty<Integer> p3 = new BaseProperty<>(4, "integerPropertyTest3");
        final BaseProperty<Integer> p4 = new BaseProperty<>(5, "integerPropertyTest4");

        final BaseExpression<Integer> multiply = BaseExpression.tetraCombine(p1, p2, p3, p4,
                (n1, n2, n3, n4) -> n1 * n2 * n3 * n4);

        assertThat(multiply.getValue()).isEqualTo(120);

        p2.setValue(null);

        assertThat(multiply.getValue()).isNull();
    }

    @Test
    public void testPetaExpression()
    {
        final BaseProperty<Integer> p1 = new BaseProperty<>(2, "integerPropertyTest1");
        final BaseProperty<Integer> p2 = new BaseProperty<>(3, "integerPropertyTest2");
        final BaseProperty<Integer> p3 = new BaseProperty<>(4, "integerPropertyTest3");
        final BaseProperty<Integer> p4 = new BaseProperty<>(5, "integerPropertyTest4");
        final BaseProperty<Integer> p5 = new BaseProperty<>(6, "integerPropertyTest5");

        final BaseExpression<Integer> multiply = BaseExpression.petaCombine(p1, p2, p3, p4, p5,
                (n1, n2, n3, n4, n5) -> n1 * n2 * n3 * n4 * n5);

        assertThat(multiply.getValue()).isEqualTo(720);

        p2.setValue(null);

        assertThat(multiply.getValue()).isNull();
    }

    @Test
    public void testHexaExpression()
    {
        final BaseProperty<Integer> p1 = new BaseProperty<>(2, "integerPropertyTest1");
        final BaseProperty<Integer> p2 = new BaseProperty<>(3, "integerPropertyTest2");
        final BaseProperty<Integer> p3 = new BaseProperty<>(4, "integerPropertyTest3");
        final BaseProperty<Integer> p4 = new BaseProperty<>(5, "integerPropertyTest4");
        final BaseProperty<Integer> p5 = new BaseProperty<>(6, "integerPropertyTest5");
        final BaseProperty<Integer> p6 = new BaseProperty<>(7, "integerPropertyTest5");

        final BaseExpression<Integer> multiply = BaseExpression.hexaCombine(p1, p2, p3, p4, p5, p6,
                (n1, n2, n3, n4, n5, n6) -> n1 * n2 * n3 * n4 * n5 * n6);

        assertThat(multiply.getValue()).isEqualTo(5040);

        p2.setValue(null);

        assertThat(multiply.getValue()).isNull();
    }
}