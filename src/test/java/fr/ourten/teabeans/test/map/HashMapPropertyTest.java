package fr.ourten.teabeans.test.map;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Maps;

import fr.ourten.teabeans.value.map.HashMapProperty;
import fr.ourten.teabeans.value.map.MapProperty;

public class HashMapPropertyTest
{
    Map<String, Integer>         map;
    MapProperty<String, Integer> property;

    @Before
    public void setup()
    {
        this.map = Maps.newHashMap();
        map.put("0", 0);
        map.put("2", 2);
        map.put("3", 3);
        map.put("5", 5);

        this.property = new HashMapProperty<>(this.map, "testIntegerMapProperty");
    }

    @Test
    public void testConstructorMapOnly()
    {
        String expected = "";

        MapProperty<String, Integer> property = new HashMapProperty<>(this.map);

        String actual = property.getName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testConstructorMapNull()
    {
        int expected = 0;

        MapProperty<String, Integer> property = new HashMapProperty<>(null);

        int actual = property.size();

        Assert.assertEquals(expected, actual);
        Assert.assertTrue(property.isEmpty());
    }
}
