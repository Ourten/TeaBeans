package fr.ourten.teabeans.test;

import fr.ourten.teabeans.value.ListProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckerListPropertyTest
{
    ListProperty<String> property;

    @BeforeEach
    public void setup()
    {
        property = new ListProperty<>(new ArrayList<>());
    }

    @Test
    public void testCheckerNull()
    {
        assertThat(property.getElementChecker()).isNull();
    }

    @Test
    public void testCheckerNotNull()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() ? newValue : oldValue;

        property.setElementChecker(checker);
        BiFunction<String, String, String> actual = property.getElementChecker();

        assertThat(actual).isEqualTo(checker);
    }

    @Test
    public void testCheckerAddNull1()
    {
        property.setElementChecker(null);
    }

    @Test
    public void testCheckerAddValid()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        property.setElementChecker(checker);
        property.add("42");

        String actual = property.get(0);

        assertThat(actual).isEqualTo("42");
    }

    @Test
    public void testCheckerAddNotValid1()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        property.setElementChecker(checker);
        property.add("");

        String actual = property.get(0);

        assertThat(actual).isNull();
    }

    @Test
    public void testCheckerAddNotValid2()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        property.setElementChecker(checker);
        property.add(null);

        String actual = property.get(0);

        assertThat(actual).isNull();
    }

    @Test
    public void testCheckerAddNotValid3()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        property.setElementChecker(checker);
        property.add("2");

        String actual = property.get(0);

        assertThat(actual).isNull();
    }

    @Test
    public void testCheckerSetValid()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        property.add("0");

        property.setElementChecker(checker);
        property.set(0, "42");

        String actual = property.get(0);

        assertThat(actual).isEqualTo("42");
    }

    @Test
    public void testCheckerSetNotValid1()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        property.add("0");

        property.setElementChecker(checker);
        property.set(0, "");

        String actual = property.get(0);

        assertThat(actual).isEqualTo("0");
    }

    @Test
    public void testCheckerSetNotValid2()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        property.add("0");

        property.setElementChecker(checker);
        property.set(0, null);

        String actual = property.get(0);

        assertThat(actual).isEqualTo("0");
    }

    @Test
    public void testCheckerSetNotValid3()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        property.add("0");

        property.setElementChecker(checker);
        property.set(0, "2");

        String actual = property.get(0);

        assertThat(actual).isEqualTo("0");
    }
}
