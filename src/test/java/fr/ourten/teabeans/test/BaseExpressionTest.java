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
    public void testSimpleExpression()
    {
        final BaseProperty<Integer> p1 = new BaseProperty<>(2, "integerPropertyTest1");
        final BaseProperty<Integer> p2 = new BaseProperty<>(3, "integerPropertyTest2");

        final BaseExpression<Integer> multiply = BaseExpression.combine(p1, p2, (n1, n2) -> n1 * n2);

        Assert.assertEquals("should be equals", (Integer) 6, multiply.getValue());

        p2.setValue(null);

        Assert.assertNull("should be null", multiply.getValue());
    }
}