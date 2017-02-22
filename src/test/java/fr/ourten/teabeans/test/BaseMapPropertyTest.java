package fr.ourten.teabeans.test;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Maps;

import fr.ourten.teabeans.listener.MapValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.BaseMapProperty;

public class BaseMapPropertyTest
{
    Map<String, Integer>             map;
    BaseMapProperty<String, Integer> property;
    int                              count;

    @Before
    public void setup()
    {
        this.map = Maps.newHashMap();
        this.map.put("0", 0);
        this.map.put("2", 2);
        this.map.put("3", 3);
        this.map.put("5", 5);

        this.property = new BaseMapProperty<>(this.map, "testIntegerMapProperty");
        this.count = 0;
    }

    @Test
    public void testConstructorMapOnly()
    {
        final String expected = "";

        final BaseMapProperty<String, Integer> property = new BaseMapProperty<>(this.map);

        final String actual = property.getName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testConstructorMapNull()
    {
        final int expected = 0;

        final BaseMapProperty<String, Integer> property = new BaseMapProperty<>(null);

        final int actual = property.size();

        Assert.assertEquals(expected, actual);
        Assert.assertTrue(property.isEmpty());
    }

    @Test
    public void testConstructorSupplier()
    {
        final BaseMapProperty<String, Integer> property = new BaseMapProperty<>(() -> Maps.newLinkedHashMap(), null);

        Assert.assertTrue("should be true", property.getModifiableValue() instanceof LinkedHashMap);
    }

    @Test
    public void testMapPropertySize()
    {
        final int expected = 4;
        final int actual = this.property.size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testMapPropertyValues()
    {
        final Object[] excepted = this.map.values().toArray();
        final Object[] actual = this.property.values().toArray();

        Assert.assertArrayEquals(excepted, actual);
    }

    @Test
    public void testMapPropertyEntrySet()
    {
        final Object[] excepted = this.map.entrySet().toArray();
        final Object[] actual = this.property.entrySet().toArray();

        Assert.assertArrayEquals(excepted, actual);
    }

    @Test
    public void testMapPropertyValue()
    {
        Assert.assertArrayEquals("map key should be identical", this.map.keySet().toArray(),
                this.property.getValue().keySet().toArray());
        Assert.assertArrayEquals("map content should be identical", this.map.values().toArray(),
                this.property.getValue().values().toArray());

        Assert.assertEquals("should be equals", (Integer) 2, this.property.get("2"));

        Assert.assertTrue("should be true", this.property.containsValue(5));
        Assert.assertFalse("should be false", this.property.containsValue(7));

        Assert.assertTrue("should be true", this.property.containsKey("5"));
        Assert.assertFalse("should be false", this.property.containsKey("7"));

        Assert.assertFalse("should be false", this.property.isEmpty());

        this.property.clear();

        Assert.assertTrue("should be true", this.property.isEmpty());
    }

    @Test
    public void testMapRemoval()
    {
        final Map<Integer, String> stringMap = Maps.newHashMap();
        stringMap.put(0, "test1");
        stringMap.put(1, "test2");
        stringMap.put(2, "test3");

        final BaseMapProperty<Integer, String> stringProperty = new BaseMapProperty<>(stringMap,
                "testStringMapProperty");

        Assert.assertFalse("should be false", stringProperty.removeValue("something"));
        Assert.assertTrue("should be true", stringProperty.removeValue("test2"));
        Assert.assertFalse("should be false", stringProperty.removeValue("test2"));
    }

    @Test
    public void testMapAdd()
    {
        final Integer expected = 7;
        this.property.put("7", 7);
        Assert.assertEquals(expected, this.property.get("7"));
    }

    @Test
    public void testMapGetNullWrong()
    {
        final Integer expected1 = null;
        final Integer expected2 = 7;

        final Integer actual1 = this.property.get("7");
        final Integer actual2 = this.property.getOrDefault("7", 7);

        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
    }

    @Test
    public void testMapGetNullRight()
    {
        this.property.put("null", null);
        final Integer expected1 = null;
        final Integer expected2 = null;

        final Integer actual1 = this.property.get("null");
        final Integer actual2 = this.property.getOrDefault("null", 7);

        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
    }

    @Test
    public void testMapGetNotNull()
    {
        final Integer expected1 = 3;
        final Integer expected2 = 3;

        final Integer actual1 = this.property.get("3");
        final Integer actual2 = this.property.getOrDefault("3", 4);

        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
    }

    @Test
    public void testMapPropertyInvalidationListener()
    {
        this.property.addListener((ValueInvalidationListener) observable -> BaseMapPropertyTest.this.count++);

        this.property.put("6", 6);
        Assert.assertEquals("should be equals", 1, this.count);

        this.property.removeValue(2);
        Assert.assertEquals("should be equals", 2, this.count);

        this.property.replace("0", 4);
        Assert.assertEquals("should be equals", 3, this.count);
    }

    @Test
    public void testMapPropertyListChangeListener()
    {

        final MapValueChangeListener<String, Integer> removeListener = (observable, key, oldValue, newValue) ->
        {
            Assert.assertNotNull("should not be null", oldValue);
            Assert.assertNull("should be null", newValue);
            Assert.assertEquals("should be equals", (Integer) 0, oldValue);
        };

        this.property.addListener(removeListener);
        this.property.remove("0");
        this.property.removeListener(removeListener);

        final MapValueChangeListener<String, Integer> changeListener = (observable, key, oldValue, newValue) ->
        {
            Assert.assertNotNull("should not be null", oldValue);
            Assert.assertNotNull("should not be null", newValue);
            Assert.assertNotEquals("should not be equals", oldValue, newValue);
            Assert.assertEquals("should be equals", (Integer) 2, oldValue);
            Assert.assertEquals("should be equals", (Integer) 5, newValue);
        };
        this.property.addListener(changeListener);
        this.property.replace("2", 5);
        this.property.removeListener(changeListener);

        final MapValueChangeListener<String, Integer> addListener = (observable, key, oldValue, newValue) ->
        {
            Assert.assertNull("should be null", oldValue);
            Assert.assertNotNull("should not be null", newValue);
            Assert.assertEquals("should be equals", (Integer) 18, newValue);
        };

        final Map<String, Integer> tempMap = Maps.newHashMap();
        tempMap.put("42", 18);
        tempMap.put("43", 18);
        tempMap.put("44", 18);

        this.property.addListener(addListener);
        this.property.put("18", 18);
        this.property.putAll(tempMap);
        this.property.removeListener(addListener);

        this.count = 0;
        this.property.clear();
        this.property.put("42", 18);
        this.property.put("38", 18);
        this.property.put("test", 18);
        this.property.put("none", 18);
        this.property.put("70", 18);
        final MapValueChangeListener<String, Integer> clearListener = (observable, key, oldValue, newValue) ->
        {
            Assert.assertNotNull("should not be null", oldValue);
            Assert.assertNull("should be null", newValue);
            Assert.assertEquals("should be equals", (Integer) 18, oldValue);
            this.count++;
        };
        this.property.addListener(clearListener);

        this.property.clear();
        Assert.assertEquals("should be equals", 5, this.count);
    }
}
