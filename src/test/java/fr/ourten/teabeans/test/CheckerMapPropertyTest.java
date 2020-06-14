package fr.ourten.teabeans.test;

import fr.ourten.teabeans.value.MapProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckerMapPropertyTest
{
    MapProperty<Integer, String> property;

    @BeforeEach
    public void setup()
    {
        property = new MapProperty<>(new HashMap<>());
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
    public void testCheckerPutValid()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        property.setElementChecker(checker);
        property.put(42, "42");

        String actual = property.get(42);

        assertThat(actual).isEqualTo("42");
    }

    @Test
    public void testCheckerAddNotValid1()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        property.setElementChecker(checker);
        property.put(-1, "");

        String actual = property.get(0);

        assertThat(actual).isNull();
    }

    @Test
    public void testCheckerAddNotValid2()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        property.setElementChecker(checker);
        property.put(-1, null);

        String actual = property.get(0);

        assertThat(actual).isNull();
    }

    @Test
    public void testCheckerAddNotValid3()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        property.setElementChecker(checker);
        property.put(-1, "2");

        String actual = property.get(0);

        assertThat(actual).isNull();
    }

    @Test
    public void testCheckerSetValid()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        property.put(0, "0");

        property.setElementChecker(checker);
        property.replace(0, "42");

        String actual = property.get(0);

        assertThat(actual).isEqualTo("42");
    }

    @Test
    public void testCheckerSetNotValid1()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        property.put(0, "0");

        property.setElementChecker(checker);
        property.replace(0, "");

        String actual = property.get(0);

        assertThat(actual).isEqualTo("0");
    }

    @Test
    public void testCheckerSetNotValid2()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        property.put(0, "0");

        property.setElementChecker(checker);
        property.replace(0, null);

        String actual = property.get(0);

        assertThat(actual).isEqualTo("0");
    }

    @Test
    public void testCheckerSetNotValid3()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        property.put(0, "0");

        property.setElementChecker(checker);
        property.replace(0, "2");

        String actual = property.get(0);

        assertThat(actual).isEqualTo("0");
    }
}
