package fr.ourten.teabeans.test;

import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

import fr.ourten.teabeans.value.BaseProperty;
import fr.ourten.teabeans.value.IProperty;

/**
 * @author Ourten 11 oct. 2016
 */
public class IPropertyTest
{
    @Test(expected = NoSuchElementException.class)
    public void testThrow()
    {
        final IProperty<String> property = new BaseProperty<>(null, "throwTestProperty");

        property.getOrThrow();
    }

    @Test
    public void testDefault()
    {
        final IProperty<String> property = new BaseProperty<>(null, "defaultTestProperty");

        Assert.assertEquals("should be equals", property.getOrDefault("default"), "default");

        property.setValue("test");

        Assert.assertEquals("should be equals", property.getOrDefault("default"), "test");
    }
}
