package fr.ourten.teabeans.property;

import fr.ourten.teabeans.listener.MapValueChangeListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MapPropertyTest
{
    Map<String, Integer>         map;
    MapProperty<String, Integer> property;
    int                          count;

    @BeforeEach
    public void setup()
    {
        map = new HashMap<>();
        map.put("0", 0);
        map.put("2", 2);
        map.put("3", 3);
        map.put("5", 5);

        property = new MapProperty<>(map);
        count = 0;
    }

    @Test
    public void testConstructorMapNull()
    {
        MapProperty<String, Integer> property = new MapProperty<>(null);

        int actual = property.size();

        assertThat(actual).isEqualTo(0);
        assertThat(property.isEmpty()).isTrue();
    }

    @Test
    public void testConstructorSupplier()
    {
        MapProperty<String, Integer> property = new MapProperty<>(LinkedHashMap::new, null);

        assertThat(property.getModifiableValue()).isInstanceOf(LinkedHashMap.class);
    }

    @Test
    public void testMapPropertySize()
    {
        int actual = property.size();
        assertThat(actual).isEqualTo(4);
    }

    @Test
    public void testMapPropertyValues()
    {
        Object[] expected = map.values().toArray();
        Object[] actual = property.values().toArray();

        assertThat(actual).containsExactly(expected);
    }

    @Test
    public void testMapPropertyEntrySet()
    {
        Object[] excepted = map.entrySet().toArray();
        Object[] actual = property.entrySet().toArray();

        assertThat(actual).containsExactly(excepted);
    }

    @Test
    public void testMapPropertyValue()
    {
        assertThat(property.getValue().keySet().toArray()).containsExactly(map.keySet().toArray());
        assertThat(property.getValue().values().toArray()).containsExactly(map.values().toArray());

        assertThat(property.get("2")).isEqualTo(2);

        assertThat(property.containsValue(5)).isTrue();
        assertThat(property.containsValue(7)).isFalse();

        assertThat(property.containsKey("5")).isTrue();
        assertThat(property.containsKey("7")).isFalse();

        assertThat(property.isEmpty()).isFalse();

        property.clear();

        assertThat(property.isEmpty()).isTrue();
    }

    @Test
    public void testMapRemoval()
    {
        Map<Integer, String> stringMap = new HashMap<>();
        stringMap.put(0, "test1");
        stringMap.put(1, "test2");
        stringMap.put(2, "test3");

        MapProperty<Integer, String> stringProperty = new MapProperty<>(stringMap);

        assertThat(stringProperty.removeValue("something")).isFalse();
        assertThat(stringProperty.removeValue("test2")).isTrue();
        assertThat(stringProperty.removeValue("test2")).isFalse();
    }

    @Test
    public void testMapAdd()
    {
        property.put("7", 7);
        assertThat(property.get("7")).isEqualTo(7);
    }

    @Test
    public void testMapGetNullWrong()
    {
        Integer actual1 = property.get("7");
        Integer actual2 = property.getOrDefault("7", 7);

        assertThat(actual1).isNull();
        assertThat(actual2).isEqualTo(7);
    }

    @Test
    public void testMapGetNullRight()
    {
        property.put("null", null);

        Integer actual1 = property.get("null");
        Integer actual2 = property.getOrDefault("null", 7);

        assertThat(actual1).isNull();
        assertThat(actual2).isNull();
    }

    @Test
    public void testMapGetNotNull()
    {
        Integer actual1 = property.get("3");
        Integer actual2 = property.getOrDefault("3", 4);

        assertThat(actual1).isEqualTo(3);
        assertThat(actual2).isEqualTo(3);
    }

    @Test
    public void testMapPropertyInvalidationListener()
    {
        property.addListener(observable -> count++);

        property.put("6", 6);
        assertThat(count).isEqualTo(1);

        property.removeValue(2);
        assertThat(count).isEqualTo(2);

        property.replace("0", 4);
        assertThat(count).isEqualTo(3);
    }

    @Test
    public void testMapPropertyListChangeListener()
    {

        MapValueChangeListener<String, Integer> removeListener = (observable, key, oldValue, newValue) ->
        {
            assertThat(oldValue).isNotNull();
            assertThat(newValue).isNull();
            assertThat(oldValue).isEqualTo(0);
        };

        property.addListener(removeListener);
        property.remove("0");
        property.removeListener(removeListener);

        MapValueChangeListener<String, Integer> changeListener = (observable, key, oldValue, newValue) ->
        {
            assertThat(oldValue).isNotNull();
            assertThat(newValue).isNotNull();
            assertThat(newValue).isNotEqualTo(oldValue);
            assertThat(oldValue).isEqualTo(2);
            assertThat(newValue).isEqualTo(5);
        };
        property.addListener(changeListener);
        property.replace("2", 5);
        property.removeListener(changeListener);

        MapValueChangeListener<String, Integer> addListener = (observable, key, oldValue, newValue) ->
        {
            assertThat(oldValue).isNull();
            assertThat(newValue).isNotNull();
            assertThat(newValue).isEqualTo(18);
        };

        Map<String, Integer> tempMap = new HashMap<>();
        tempMap.put("42", 18);
        tempMap.put("43", 18);
        tempMap.put("44", 18);

        property.addListener(addListener);
        property.put("18", 18);
        property.putAll(tempMap);
        property.removeListener(addListener);

        count = 0;
        property.clear();
        property.put("42", 18);
        property.put("38", 18);
        property.put("test", 18);
        property.put("none", 18);
        property.put("70", 18);
        MapValueChangeListener<String, Integer> clearListener = (observable, key, oldValue, newValue) ->
        {
            assertThat(oldValue).isNotNull();
            assertThat(newValue).isNull();
            assertThat(oldValue).isEqualTo(18);
            count++;
        };
        property.addListener(clearListener);

        property.clear();
        assertThat(count).isEqualTo(5);
    }

    @Test
    void getValue_givenSimpleMap_thenShouldCreateImmutableView()
    {
        MapProperty<String, Integer> property = new MapProperty<>(IntStream.range(0, 10).boxed()
                .collect(toMap(String::valueOf, identity())));

        Map<String, Integer> immutableValues = property.getValue();

        assertThat(immutableValues).containsExactlyInAnyOrderEntriesOf(IntStream.range(0, 10).boxed()
                .collect(toMap(String::valueOf, identity())));

        assertThatThrownBy(() -> immutableValues.put("0", 0)).isInstanceOf(UnsupportedOperationException.class);

        property.put("10", 10);

        assertThat(immutableValues).containsExactlyInAnyOrderEntriesOf(IntStream.range(0, 11).boxed()
                .collect(toMap(String::valueOf, identity())));
    }

    @Test
    void getValue_givenSimpleMapThenReplaced_thenShouldReCreateImmutableView()
    {
        MapProperty<String, Integer> property = new MapProperty<>(IntStream.range(0, 10).boxed()
                .collect(toMap(String::valueOf, identity())));

        Map<String, Integer> immutableValues = property.getValue();
        assertThat(immutableValues).isSameAs(property.getValue());

        property.setValue(IntStream.range(10, 20).boxed()
                .collect(toMap(String::valueOf, identity())));
        assertThat(immutableValues).containsExactlyInAnyOrderEntriesOf(IntStream.range(0, 10).boxed()
                .collect(toMap(String::valueOf, identity())));

        assertThat(immutableValues).isNotSameAs(property.getValue());
        assertThat(property.getValue()).containsExactlyInAnyOrderEntriesOf(IntStream.range(10, 20).boxed()
                .collect(toMap(String::valueOf, identity())));
    }
}
