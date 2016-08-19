package fr.ourten.teabeans.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.BaseProperty;

public class ValueListenerTest
{
    private boolean               used;
    private BaseProperty<Integer> property;

    @Before
    public void setup()
    {
        this.property = new BaseProperty<>(5, "testIntegerProperty");
        this.used = false;
    }

    @After
    public void after()
    {
        this.property.setValue(6);
        Assert.assertTrue("should be true", this.used);
    }

    @Test
    public void testInvalidationListener()
    {
        this.property.addListener((ValueInvalidationListener) observable -> ValueListenerTest.this.used = true);
    }

    @Test
    public void testChangeListener()
    {
        this.property.addListener((ValueChangeListener<Integer>) (observable, oldValue, newValue) ->
        {
            ValueListenerTest.this.used = true;
            Assert.assertEquals("should be same", (Integer) 5, oldValue);
            Assert.assertEquals("should be same", (Integer) 6, newValue);
        });
    }
}