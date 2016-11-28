package fr.ourten.teabeans.test.list;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.ourten.teabeans.value.list.ArrayListProperty;
import fr.ourten.teabeans.value.list.ListProperty;

public class ArrayListPropertyTest
{
    List<Integer>         list;
    ListProperty<Integer> property;
    int                   count;

    @Before
    public void setup()
    {
        this.list = Arrays.asList(0, 2, 3, 5);
        this.property = new ArrayListProperty<>(this.list, "testIntegerListProperty");
        this.count = 0;
    }

    @Test
    public void testConstructorListOnly()
    {
        final String expected = "";

        final ListProperty<Integer> property = new ArrayListProperty<>(this.list);

        final String actual = property.getName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testConstructorListNull()
    {
        final int expected = 0;

        final ListProperty<Integer> property = new ArrayListProperty<>(null);

        final int actual = property.size();

        Assert.assertEquals(expected, actual);
        Assert.assertTrue(property.isEmpty());
    }
}