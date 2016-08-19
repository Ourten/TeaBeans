package fr.ourten.teabeans.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.ourten.teabeans.binding.BaseBinding;
import fr.ourten.teabeans.value.BaseProperty;

public class BindingTest
{
    private int count;

    @Before
    public void setup()
    {
        this.count = 0;
    }

    @Test
    public void testBaseBinding()
    {
        final BaseProperty<String> p1 = new BaseProperty<>("none", "testStringProperty");
        final BaseProperty<String> p2 = new BaseProperty<>("nothing", "testStringProperty2");

        final BaseBinding<String> binding = new BaseBinding<String>()
        {
            {
                super.bind(p1);
                super.bind(p2);
            }

            @Override
            public String computeValue()
            {
                BindingTest.this.count++;
                return p1.getValue() + p2.getValue();
            }
        };

        // Only here to check lazy evaluation
        Assert.assertNotNull("should not be null", binding.getValue());

        Assert.assertEquals("should be equals", "nonenothing", binding.getValue());

        p1.setValue("lala");
        Assert.assertEquals("should be equals", "lalanothing", binding.getValue());

        p2.setValue("toto");
        Assert.assertEquals("should be equals", "lalatoto", binding.getValue());

        Assert.assertEquals("should be equals", 3, this.count);
    }

    @Test
    public void testComplexBinding()
    {
        final BaseProperty<String> p1 = new BaseProperty<>("none", "testStringProperty");
        final BaseProperty<String> p2 = new BaseProperty<>("nothing", "testStringProperty2");

        final BaseProperty<String> p3 = new BaseProperty<>("toy", "testStringProperty3");

        final BaseBinding<String> binding = new BaseBinding<String>()
        {
            {
                super.bind(p1);
                super.bind(p2);
            }

            @Override
            public String computeValue()
            {
                BindingTest.this.count++;
                return p1.getValue() + p2.getValue();
            }
        };

        p2.bindBidirectional(p3);

        Assert.assertEquals("should be equals", "nonetoy", binding.getValue());

        p1.bind(p3);

        Assert.assertEquals("should be equals", "toytoy", binding.getValue());

        Assert.assertEquals("should be equals", 2, this.count);
    }

    @Test
    public void testUnidirectionnalUnbinding()
    {
        final BaseProperty<String> p1 = new BaseProperty<>("none", "testStringProperty");
        final BaseProperty<String> p2 = new BaseProperty<>("nothing", "testStringProperty2");

        p1.bind(p2);

        Assert.assertEquals("should be equals", p1.getValue(), p2.getValue());

        p2.setValue("lalala");

        Assert.assertEquals("should be equals", p1.getValue(), p2.getValue());

        p1.unbind();

        p2.setValue("another value");

        Assert.assertNotEquals("should not be equals", p1.getValue(), p2.getValue());
    }

    @Test
    public void testBidirectionnalUnbinding()
    {
        final BaseProperty<String> p1 = new BaseProperty<>("none", "testStringProperty");
        final BaseProperty<String> p2 = new BaseProperty<>("nothing", "testStringProperty2");

        p1.bindBidirectional(p2);

        Assert.assertEquals("should be equals", p1.getValue(), p2.getValue());

        p2.setValue("lalala");

        Assert.assertEquals("should be equals", p1.getValue(), p2.getValue());

        p1.unbindBidirectional(p2);

        p2.setValue("another value");

        Assert.assertNotEquals("should not be equals", p1.getValue(), p2.getValue());
    }
}