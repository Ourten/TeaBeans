package fr.ourten.teabeans.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.BaseListProperty;
import fr.ourten.teabeans.value.ListValueChangeListener;

public class BaseListPropertyTest
{
    List<Integer>             list;
    BaseListProperty<Integer> property;

    @Before
    public void setup()
    {
        list = Arrays.asList(0, 2, 3, 5);
        property = new BaseListProperty<>(list, "testIntegerListProperty");
    }

    @Test
    public void testListPropertyValue()
    {
        Assert.assertArrayEquals("list content should be identical", list.toArray(), property.getValue().toArray());
    }

    int count;

    @Test
    public void testListPropertyInvalidationListener()
    {
        this.count = 0;

        property.addListener((ValueInvalidationListener) observable -> BaseListPropertyTest.this.count++);

        property.add(6);
        Assert.assertEquals("should be equals", 1, this.count);

        property.remove(2);
        Assert.assertEquals("should be equals", 2, this.count);

        property.set(0, 4);
        Assert.assertEquals("should be equals", 3, this.count);

        property.sort();
        Assert.assertEquals("should be equals", 4, this.count);
    }

    @Test
    public void testListPropertyListChangeListener()
    {

        final ListValueChangeListener<Integer> removeListener = (observable, oldValue, newValue) ->
        {
            Assert.assertNotNull("should not be null", oldValue);
            Assert.assertNull("should be null", newValue);
            Assert.assertEquals("should be equals", (Integer) 0, oldValue);
        };

        property.addListener(removeListener);
        property.remove(0);
        property.removeListener(removeListener);

        final ListValueChangeListener<Integer> changeListener = (observable, oldValue, newValue) ->
        {
            Assert.assertNotNull("should not be null", oldValue);
            Assert.assertNotNull("should not be null", newValue);
            Assert.assertNotEquals("should not be equals", oldValue, newValue);
            Assert.assertEquals("should be equals", (Integer) 2, oldValue);
            Assert.assertEquals("should be equals", (Integer) 5, newValue);
        };
        property.addListener(changeListener);
        property.set(0, 5);
        property.removeListener(changeListener);

        final ListValueChangeListener<Integer> addListener = (observable, oldValue, newValue) ->
        {
            Assert.assertNull("should be null", oldValue);
            Assert.assertNotNull("should not be null", newValue);
            Assert.assertEquals("should be equals", (Integer) 18, newValue);
        };
        property.addListener(addListener);
        property.add(18);
        property.removeListener(addListener);
    }

    @Test
    public void testListPropertySort()
    {
        final List<Integer> unsorted = Arrays.asList(1, 5, 6, 4, 10);
        final BaseListProperty<Integer> property = new BaseListProperty<>(unsorted, "testIntegerListProperty");

        property.sort();

        Assert.assertArrayEquals("should be equals", new Integer[] { 1, 4, 5, 6, 10 }, property.getValue().toArray());

        property.sort(Collections.reverseOrder());
        Assert.assertArrayEquals("should be equals", new Integer[] { 10, 6, 5, 4, 1 }, property.getValue().toArray());
    }
}