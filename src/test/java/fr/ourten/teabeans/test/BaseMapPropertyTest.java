package fr.ourten.teabeans.test;

import fr.ourten.teabeans.listener.MapValueChangeListener;
import fr.ourten.teabeans.value.BaseMapProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseMapPropertyTest
{
    Map<String, Integer>             map;
    BaseMapProperty<String, Integer> property;
    int                              count;

    @BeforeEach
    public void setup()
    {
        this.map = new HashMap<>();
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
        final BaseMapProperty<String, Integer> property = new BaseMapProperty<>(this.map);

        final String actual = property.getName();

        assertThat(actual).isEmpty();
    }

    @Test
    public void testConstructorMapNull()
    {
        final BaseMapProperty<String, Integer> property = new BaseMapProperty<>(null);

        final int actual = property.size();

        assertThat(actual).isEqualTo(0);
        assertThat(property.isEmpty()).isTrue();
    }

    @Test
    public void testConstructorSupplier()
    {
        final BaseMapProperty<String, Integer> property = new BaseMapProperty<>(LinkedHashMap::new, null);

        assertThat(property.getModifiableValue()).isInstanceOf(LinkedHashMap.class);
    }

    @Test
    public void testMapPropertySize()
    {
        final int actual = this.property.size();
        assertThat(actual).isEqualTo(4);
    }

    @Test
    public void testMapPropertyValues()
    {
        final Object[] expected = this.map.values().toArray();
        final Object[] actual = this.property.values().toArray();

        assertThat(actual).containsExactly(expected);
    }

    @Test
    public void testMapPropertyEntrySet()
    {
        final Object[] excepted = this.map.entrySet().toArray();
        final Object[] actual = this.property.entrySet().toArray();

        assertThat(actual).containsExactly(excepted);
    }

    @Test
    public void testMapPropertyValue()
    {
        assertThat(this.property.getValue().keySet().toArray()).containsExactly(this.map.keySet().toArray());
        assertThat(this.property.getValue().values().toArray()).containsExactly(this.map.values().toArray());

        assertThat(this.property.get("2")).isEqualTo(2);

        assertThat(this.property.containsValue(5)).isTrue();
        assertThat(this.property.containsValue(7)).isFalse();

        assertThat(this.property.containsKey("5")).isTrue();
        assertThat(this.property.containsKey("7")).isFalse();

        assertThat(this.property.isEmpty()).isFalse();

        this.property.clear();

        assertThat(this.property.isEmpty()).isTrue();
    }

    @Test
    public void testMapRemoval()
    {
        final Map<Integer, String> stringMap = new HashMap<>();
        stringMap.put(0, "test1");
        stringMap.put(1, "test2");
        stringMap.put(2, "test3");

        final BaseMapProperty<Integer, String> stringProperty = new BaseMapProperty<>(stringMap,
                "testStringMapProperty");

        assertThat(stringProperty.removeValue("something")).isFalse();
        assertThat(stringProperty.removeValue("test2")).isTrue();
        assertThat(stringProperty.removeValue("test2")).isFalse();
    }

    @Test
    public void testMapAdd()
    {
        this.property.put("7", 7);
        assertThat(this.property.get("7")).isEqualTo(7);
    }

    @Test
    public void testMapGetNullWrong()
    {
        final Integer actual1 = this.property.get("7");
        final Integer actual2 = this.property.getOrDefault("7", 7);

        assertThat(actual1).isNull();
        assertThat(actual2).isEqualTo(7);
    }

    @Test
    public void testMapGetNullRight()
    {
        this.property.put("null", null);

        final Integer actual1 = this.property.get("null");
        final Integer actual2 = this.property.getOrDefault("null", 7);

        assertThat(actual1).isNull();
        assertThat(actual2).isNull();
    }

    @Test
    public void testMapGetNotNull()
    {
        final Integer actual1 = this.property.get("3");
        final Integer actual2 = this.property.getOrDefault("3", 4);

        assertThat(actual1).isEqualTo(3);
        assertThat(actual2).isEqualTo(3);
    }

    @Test
    public void testMapPropertyInvalidationListener()
    {
        this.property.addListener(observable -> BaseMapPropertyTest.this.count++);

        this.property.put("6", 6);
        assertThat(this.count).isEqualTo(1);

        this.property.removeValue(2);
        assertThat(this.count).isEqualTo(2);

        this.property.replace("0", 4);
        assertThat(this.count).isEqualTo(3);
    }

    @Test
    public void testMapPropertyListChangeListener()
    {

        final MapValueChangeListener<String, Integer> removeListener = (observable, key, oldValue, newValue) ->
        {
            assertThat(oldValue).isNotNull();
            assertThat(newValue).isNull();
            assertThat(oldValue).isEqualTo(0);
        };

        this.property.addListener(removeListener);
        this.property.remove("0");
        this.property.removeListener(removeListener);

        final MapValueChangeListener<String, Integer> changeListener = (observable, key, oldValue, newValue) ->
        {
            assertThat(oldValue).isNotNull();
            assertThat(newValue).isNotNull();
            assertThat(newValue).isNotEqualTo(oldValue);
            assertThat(oldValue).isEqualTo(2);
            assertThat(newValue).isEqualTo(5);
        };
        this.property.addListener(changeListener);
        this.property.replace("2", 5);
        this.property.removeListener(changeListener);

        final MapValueChangeListener<String, Integer> addListener = (observable, key, oldValue, newValue) ->
        {
            assertThat(oldValue).isNull();
            assertThat(newValue).isNotNull();
            assertThat(newValue).isEqualTo(18);
        };

        final Map<String, Integer> tempMap = new HashMap<>();
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
            assertThat(oldValue).isNotNull();
            assertThat(newValue).isNull();
            assertThat(oldValue).isEqualTo(18);
            this.count++;
        };
        this.property.addListener(clearListener);

        this.property.clear();
        assertThat(this.count).isEqualTo(5);
    }
}
