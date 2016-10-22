package fr.ourten.teabeans.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.ourten.teabeans.listener.ListValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.BaseListProperty;

public class BaseListPropertyTest
{
    List<Integer>             list;
    BaseListProperty<Integer> property;
    int                       count;

    @Before
    public void setup()
    {
        this.list = Arrays.asList(0, 2, 3, 5);
        this.property = new BaseListProperty<>(this.list, "testIntegerListProperty");
        this.count = 0;
    }

    @Test
    public void testConstructorListOnly()
    {
        String expected = "";

        BaseListProperty<Integer> property = new BaseListProperty<>(this.list);

        String actual = property.getName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testListPropertyValue()
    {
        Assert.assertArrayEquals("list content should be identical", this.list.toArray(),
                this.property.getValue().toArray());

        Assert.assertEquals("should be equals", (Integer) 2, this.property.get(1));

        Assert.assertTrue("should be true", this.property.contains(5));
        Assert.assertFalse("should be false", this.property.contains(7));

        Assert.assertEquals("should be equals", 1, this.property.indexOf(2));

        Assert.assertFalse("should be false", this.property.isEmpty());

        this.property.clear();

        Assert.assertTrue("should be true", this.property.isEmpty());
    }

    @Test
    public void testListRemoval()
    {
        final List<String> stringList = Arrays.asList("test1", "test2", "test3");
        final BaseListProperty<String> stringProperty = new BaseListProperty<>(stringList, "testStringListProperty");

        Assert.assertFalse("should be false", stringProperty.remove("something"));
        Assert.assertTrue("should be true", stringProperty.remove("test2"));
        Assert.assertFalse("should be false", stringProperty.contains("test2"));
    }

    @Test
    public void testListAdd()
    {

    }

    @Test
    public void testListPropertyInvalidationListener()
    {
        this.property.addListener((ValueInvalidationListener) observable -> BaseListPropertyTest.this.count++);

        this.property.add(6);
        Assert.assertEquals("should be equals", 1, this.count);

        this.property.remove(2);
        Assert.assertEquals("should be equals", 2, this.count);

        this.property.set(0, 4);
        Assert.assertEquals("should be equals", 3, this.count);

        this.property.sort();
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

        this.property.addListener(removeListener);
        this.property.remove(0);
        this.property.removeListener(removeListener);

        final ListValueChangeListener<Integer> changeListener = (observable, oldValue, newValue) ->
        {
            Assert.assertNotNull("should not be null", oldValue);
            Assert.assertNotNull("should not be null", newValue);
            Assert.assertNotEquals("should not be equals", oldValue, newValue);
            Assert.assertEquals("should be equals", (Integer) 2, oldValue);
            Assert.assertEquals("should be equals", (Integer) 5, newValue);
        };
        this.property.addListener(changeListener);
        this.property.set(0, 5);
        this.property.removeListener(changeListener);

        final ListValueChangeListener<Integer> addListener = (observable, oldValue, newValue) ->
        {
            Assert.assertNull("should be null", oldValue);
            Assert.assertNotNull("should not be null", newValue);
            Assert.assertEquals("should be equals", (Integer) 18, newValue);
        };
        this.property.addListener(addListener);
        this.property.add(18);
        this.property.add(2, 18);
        this.property.addAll(Arrays.asList(18, 18, 18));
        this.property.removeListener(addListener);
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