package fr.ourten.teabeans.test;

import fr.ourten.teabeans.listener.ListValueChangeListener;
import fr.ourten.teabeans.property.SetProperty;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SetPropertyTest
{
    Set<Integer>         set;
    SetProperty<Integer> property;
    int                  count;

    @BeforeEach
    public void setup()
    {
        set = Sets.newHashSet(Arrays.asList(0, 2, 3, 5));
        property = new SetProperty<>(set);
        count = 0;
    }

    @Test
    public void testConstructorSetNull()
    {
        SetProperty<Integer> property = new SetProperty<>(null);

        int actual = property.size();

        assertThat(actual).isEqualTo(0);
        assertThat(property.isEmpty()).isTrue();
    }

    @Test
    public void testConstructorSupplier()
    {
        SetProperty<Integer> property = new SetProperty<>(LinkedHashSet::new, null);

        assertThat(property.getModifiableValue()).isInstanceOf(LinkedHashSet.class);
    }

    @Test
    public void testSetPropertySize()
    {
        int actual = property.size();
        assertThat(actual).isEqualTo(4);
    }

    @Test
    public void testSetPropertyValue()
    {
        assertThat(property.getValue().toArray()).containsExactly(set.toArray());

        assertThat(property.contains(5)).isTrue();
        assertThat(property.contains(7)).isFalse();

        assertThat(property.isEmpty()).isFalse();

        property.clear();

        assertThat(property.isEmpty()).isTrue();
    }

    @Test
    public void testSetRemoval()
    {
        Set<String> stringSet = new HashSet<>(Arrays.asList("test1", "test2", "test3"));
        SetProperty<String> stringProperty = new SetProperty<>(stringSet);

        assertThat(stringProperty.remove("something")).isFalse();
        assertThat(stringProperty.remove("test2")).isTrue();
        assertThat(stringProperty.contains("test2")).isFalse();
    }

    @Test
    public void testSetAdd()
    {
        property.add(7);
        assertThat(property.getValue()).contains(7);
    }

    @Test
    public void testSetPropertyInvalidationListener()
    {
        property.addListener(observable -> count++);

        property.add(6);
        assertThat(count).isEqualTo(1);

        property.remove(2);
        assertThat(count).isEqualTo(2);
    }

    @Test
    public void testSetPropertyListChangeListener()
    {
        ListValueChangeListener<Integer> removeListener = (observable, oldValue, newValue) ->
        {
            assertThat(oldValue).isNotNull();
            assertThat(newValue).isNull();
            assertThat(oldValue).isEqualTo(0);
        };

        property.addListener(removeListener);
        property.remove(0);
        property.removeListener(removeListener);

        ListValueChangeListener<Integer> addListener = (observable, oldValue, newValue) ->
        {
            assertThat(oldValue).isNull();
            assertThat(newValue).isNotNull();
            assertThat(newValue).isIn(18, 20, 22, 24);
            count++;
        };

        property.addListener(addListener);
        property.add(18);
        property.addAll(Arrays.asList(20, 22, 24));
        assertThat(count).isEqualTo(4);
        property.removeListener(addListener);

        count = 0;
        property.clear();
        property.addAll(Arrays.asList(18, 20, 22, 24, 26));
        ListValueChangeListener<Integer> clearListener = (observable, oldValue, newValue) ->
        {
            assertThat(oldValue).isNotNull();
            assertThat(newValue).isNull();
            assertThat(oldValue).isIn(18, 20, 22, 24, 26);
            count++;
        };
        property.addListener(clearListener);

        property.clear();
        assertThat(count).isEqualTo(5);
    }

    @Test
    public void testListPropertyReplace()
    {
        property.replace(3, 9);

        assertThat(property.contains(9)).isTrue();
        assertThat(property.contains(3)).isFalse();
    }
}
