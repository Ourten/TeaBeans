package fr.ourten.teabeans.test;

import fr.ourten.teabeans.value.BaseProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckerPropertyTest
{
    BaseProperty<String> property;

    @BeforeEach
    public void setup()
    {
        this.property = new BaseProperty<>("test");
    }

    @Test
    public void testCheckerNull()
    {
        assertThat(this.property.getChecker()).isNull();
    }

    @Test
    public void testCheckerNotNull()
    {
        final BiFunction<String, String, String> checker = (final String oldValue, final String newValue) ->
                newValue != null && !newValue.isEmpty() ? newValue : oldValue;

        this.property.setChecker(checker);
        final BiFunction<String, String, String> actual = this.property.getChecker();

        assertThat(actual).isEqualTo(checker);
    }

    @Test
    public void testCheckerAddNull1()
    {
        this.property.setChecker(null);
    }

    @Test
    public void testCheckerValid()
    {
        final BiFunction<String, String, String> checker = (final String oldValue, final String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        this.property.setValue("0");

        this.property.setChecker(checker);
        this.property.setValue("42");

        final String actual = this.property.getValue();

        assertThat(actual).isEqualTo("42");
    }

    @Test
    public void testCheckerNotValid1()
    {
        final BiFunction<String, String, String> checker = (final String oldValue, final String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        this.property.setValue("0");

        this.property.setChecker(checker);
        this.property.setValue("");

        final String actual = this.property.getValue();

        assertThat(actual).isEqualTo("0");
    }

    @Test
    public void testCheckerNotValid2()
    {
        final BiFunction<String, String, String> checker = (final String oldValue, final String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        this.property.setValue("0");

        this.property.setChecker(checker);
        this.property.setValue(null);

        final String actual = this.property.getValue();

        assertThat(actual).isEqualTo("0");
    }

    @Test
    public void testCheckerNotValid3()
    {
        final BiFunction<String, String, String> checker = (final String oldValue, final String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        this.property.setValue("0");

        this.property.setChecker(checker);
        this.property.setValue("2");

        final String actual = this.property.getValue();

        assertThat(actual).isEqualTo("0");
    }

}