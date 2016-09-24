package fr.ourten.teabeans.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.ourten.teabeans.binding.BaseBinding;
import fr.ourten.teabeans.value.BaseProperty;
import fr.ourten.teabeans.value.Observable;

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
    public void testChainedBinding()
    {
        final BaseProperty<String> p1 = new BaseProperty<>("none", "testStringProperty");
        final BaseProperty<String> p2 = new BaseProperty<>("nothing", "testStringProperty2");

        final BaseProperty<String> p3 = new BaseProperty<>("", "testStringProperty3");

        p3.bind(new BaseBinding<String>()
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
        });

        Assert.assertEquals("should be equals", "nonenothing", p3.getValue());

        Assert.assertEquals("should be equals", 1, this.count);
    }

    @Test
    public void testBindingUnbinding()
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

        Assert.assertArrayEquals(binding.getDependencies().toArray(), new Observable[] { p1, p2 });

        binding.unbind(p1, p2);

        Assert.assertArrayEquals(binding.getDependencies().toArray(), new Observable[] {});
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
    public void testInvalidationBinding()
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

        Assert.assertFalse("should be false", binding.isValid());

        Assert.assertEquals("should be equals", binding.getValue(), "nonenothing");

        Assert.assertTrue("should be true", binding.isValid());

        p1.setValue("another");

        Assert.assertFalse("should be false", binding.isValid());
    }
}