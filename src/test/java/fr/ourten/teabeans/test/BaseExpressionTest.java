package fr.ourten.teabeans.test;

import org.junit.Assert;
import org.junit.Test;

import fr.ourten.teabeans.binding.BaseExpression;
import fr.ourten.teabeans.value.BaseProperty;

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

        Assert.assertEquals("should be equals", (Integer) 8, transform.getValue());

        p1.setValue(null);

        Assert.assertNull("should be null", transform.getValue());
    }

    @Test
    public void testConstantExpression()
    {
        final BaseProperty<Integer> p1 = new BaseProperty<>(2, "integerPropertyTest1");

        final BaseExpression<Integer> multiplyConstant = BaseExpression.constantCombine(p1, 2, (n1, n2) -> n1 * n2);

        Assert.assertEquals("should be equals", (Integer) 4, multiplyConstant.getValue());

        p1.setValue(null);

        Assert.assertNull("should be null", multiplyConstant.getValue());
    }

    @Test
    public void testBiExpression()
    {
        final BaseProperty<Integer> p1 = new BaseProperty<>(2, "integerPropertyTest1");
        final BaseProperty<Integer> p2 = new BaseProperty<>(3, "integerPropertyTest2");

        final BaseExpression<Integer> multiply = BaseExpression.biCombine(p1, p2, (n1, n2) -> n1 * n2);

        Assert.assertEquals("should be equals", (Integer) 6, multiply.getValue());

        p2.setValue(null);

        Assert.assertNull("should be null", multiply.getValue());
    }

    @Test
    public void testTriExpression()
    {
        final BaseProperty<Integer> p1 = new BaseProperty<>(2, "integerPropertyTest1");
        final BaseProperty<Integer> p2 = new BaseProperty<>(3, "integerPropertyTest2");
        final BaseProperty<Integer> p3 = new BaseProperty<>(4, "integerPropertyTest3");

        final BaseExpression<Integer> multiply = BaseExpression.triCombine(p1, p2, p3, (n1, n2, n3) -> n1 * n2 * n3);

        Assert.assertEquals("should be equals", (Integer) 24, multiply.getValue());

        p2.setValue(null);

        Assert.assertNull("should be null", multiply.getValue());
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

        Assert.assertEquals("should be equals", (Integer) 120, multiply.getValue());

        p2.setValue(null);

        Assert.assertNull("should be null", multiply.getValue());
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

        Assert.assertEquals("should be equals", (Integer) 720, multiply.getValue());

        p2.setValue(null);

        Assert.assertNull("should be null", multiply.getValue());
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

        Assert.assertEquals("should be equals", (Integer) 5040, multiply.getValue());

        p2.setValue(null);

        Assert.assertNull("should be null", multiply.getValue());
    }
}