package fr.ourten.teabeans.test;

import org.junit.Assert;
import org.junit.Test;

import fr.ourten.teabeans.value.BaseProperty;

public class BasePropertyTest
{
    @Test
    public void testPropertyValue()
    {
        final BaseProperty<Float> property = new BaseProperty<>(5f, "testFloatProperty");

        final float expected = 5f;
        final float actual = property.getValue();
        Assert.assertEquals("should be equals", expected, actual, 0);
    }

    @Test
    public void testPropertyBinding()
    {
        final BaseProperty<Integer> property1 = new BaseProperty<>(3, "testIntegerProperty1");
        final BaseProperty<Integer> property2 = new BaseProperty<>(5, "testIntegerProperty1");

        property2.bind(property1);
        Assert.assertEquals("should be equals", property1.getValue(), property2.getValue());

        property1.setValue(10);
        Assert.assertEquals("should be equals", (Integer) 10, property2.getValue());
    }

    @Test
    public void testBidirectionnalPropertyBinding()
    {
        final BaseProperty<Integer> property1 = new BaseProperty<>(3, "testIntegerProperty1");
        final BaseProperty<Integer> property2 = new BaseProperty<>(5, "testIntegerProperty1");

        property2.bindBidirectional(property1);

        Assert.assertEquals("should be equals", property1.getValue(), property2.getValue());

        property1.setValue(10);
        Assert.assertEquals("should be equals", (Integer) 10, property2.getValue());

        property2.setValue(15);
        Assert.assertEquals("should be equals", (Integer) 15, property1.getValue());
    }
}