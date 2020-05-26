package fr.ourten.teabeans.test;

import fr.ourten.teabeans.value.BaseListProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckerListPropertyTest
{
    BaseListProperty<String> property;

    @BeforeEach
    public void setup()
    {
        this.property = new BaseListProperty<>(new ArrayList<>(), "test");
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
    public void testCheckerAddValid()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        this.property.setElementChecker(checker);
        this.property.add("42");

        String actual = this.property.get(0);

        assertThat(actual).isEqualTo("42");
    }

    @Test
    public void testCheckerAddNotValid1()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        this.property.setElementChecker(checker);
        this.property.add("");

        String actual = this.property.get(0);

        assertThat(actual).isNull();
    }

    @Test
    public void testCheckerAddNotValid2()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        this.property.setElementChecker(checker);
        this.property.add(null);

        String actual = this.property.get(0);

        assertThat(actual).isNull();
    }

    @Test
    public void testCheckerAddNotValid3()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        this.property.setElementChecker(checker);
        this.property.add("2");

        String actual = this.property.get(0);

        assertThat(actual).isNull();
    }

    @Test
    public void testCheckerSetValid()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        this.property.add("0");

        this.property.setElementChecker(checker);
        this.property.set(0, "42");

        String actual = this.property.get(0);

        assertThat(actual).isEqualTo("42");
    }

    @Test
    public void testCheckerSetNotValid1()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        this.property.add("0");

        this.property.setElementChecker(checker);
        this.property.set(0, "");

        String actual = this.property.get(0);

        assertThat(actual).isEqualTo("0");
    }

    @Test
    public void testCheckerSetNotValid2()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        this.property.add("0");

        this.property.setElementChecker(checker);
        this.property.set(0, null);

        String actual = this.property.get(0);

        assertThat(actual).isEqualTo("0");
    }

    @Test
    public void testCheckerSetNotValid3()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        this.property.add("0");

        this.property.setElementChecker(checker);
        this.property.set(0, "2");

        String actual = this.property.get(0);

        assertThat(actual).isEqualTo("0");
    }
}
