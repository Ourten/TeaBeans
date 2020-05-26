package fr.ourten.teabeans.test;

import fr.ourten.teabeans.value.BaseMapProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckerMapPropertyTest
{
    BaseMapProperty<Integer, String> property;

    @BeforeEach
    public void setup()
    {
        this.property = new BaseMapProperty<>(new HashMap<>(), "test");
    }

    @Test
    public void testCheckerNull()
    {
        assertThat(this.property.getElementChecker()).isNull();
    }

    @Test
    public void testCheckerNotNull()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() ? newValue : oldValue;

        this.property.setElementChecker(checker);
        BiFunction<String, String, String> actual = this.property.getElementChecker();

        assertThat(actual).isEqualTo(checker);
    }

    @Test
    public void testCheckerAddNull1()
    {
        this.property.setElementChecker(null);
    }

    @Test
    public void testCheckerPutValid()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        this.property.setElementChecker(checker);
        this.property.put(42, "42");

        String actual = this.property.get(42);

        assertThat(actual).isEqualTo("42");
    }

    @Test
    public void testCheckerAddNotValid1()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        this.property.setElementChecker(checker);
        this.property.put(-1, "");

        String actual = this.property.get(0);

        assertThat(actual).isNull();
    }

    @Test
    public void testCheckerAddNotValid2()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        this.property.setElementChecker(checker);
        this.property.put(-1, null);

        String actual = this.property.get(0);

        assertThat(actual).isNull();
    }

    @Test
    public void testCheckerAddNotValid3()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        this.property.setElementChecker(checker);
        this.property.put(-1, "2");

        String actual = this.property.get(0);

        assertThat(actual).isNull();
    }

    @Test
    public void testCheckerSetValid()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        this.property.put(0, "0");

        this.property.setElementChecker(checker);
        this.property.replace(0, "42");

        String actual = this.property.get(0);

        assertThat(actual).isEqualTo("42");
    }

    @Test
    public void testCheckerSetNotValid1()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        this.property.put(0, "0");

        this.property.setElementChecker(checker);
        this.property.replace(0, "");

        String actual = this.property.get(0);

        assertThat(actual).isEqualTo("0");
    }

    @Test
    public void testCheckerSetNotValid2()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        this.property.put(0, "0");

        this.property.setElementChecker(checker);
        this.property.replace(0, null);

        String actual = this.property.get(0);

        assertThat(actual).isEqualTo("0");
    }

    @Test
    public void testCheckerSetNotValid3()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        this.property.put(0, "0");

        this.property.setElementChecker(checker);
        this.property.replace(0, "2");

        String actual = this.property.get(0);

        assertThat(actual).isEqualTo("0");
    }
}
