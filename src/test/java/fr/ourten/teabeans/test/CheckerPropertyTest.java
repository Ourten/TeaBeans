package fr.ourten.teabeans.test;

import java.util.function.BiFunction;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.ourten.teabeans.value.BaseProperty;

public class CheckerPropertyTest
{
    BaseProperty<String> property;

    @Before
    public void setup()
    {
        this.property = new BaseProperty<>("test");
    }

    @Test
    public void testCheckerNull()
    {
        final BiFunction<String, String, String> expected = null;
        final BiFunction<String, String, String> actual = this.property.getChecker();

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerNotNull()
    {
        final BiFunction<String, String, String> checker = (final String oldValue, final String newValue) ->
        {
            return newValue != null && !newValue.isEmpty() ? newValue : oldValue;
        };

        this.property.setChecker(checker);
        final BiFunction<String, String, String> expected = checker;
        final BiFunction<String, String, String> actual = this.property.getChecker();

        Assert.assertEquals(actual, expected);
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
        {
            return newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;
        };

        final String expected = "42";

        this.property.setValue("0");

        this.property.setChecker(checker);
        this.property.setValue("42");

        final String actual = this.property.getValue();

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerNotValid1()
    {
        final BiFunction<String, String, String> checker = (final String oldValue, final String newValue) ->
        {
            return newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;
        };

        final String expected = "0";

        this.property.setValue("0");

        this.property.setChecker(checker);
        this.property.setValue("");

        final String actual = this.property.getValue();

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerNotValid2()
    {
        final BiFunction<String, String, String> checker = (final String oldValue, final String newValue) ->
        {
            return newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;
        };

        final String expected = "0";

        this.property.setValue("0");

        this.property.setChecker(checker);
        this.property.setValue(null);

        final String actual = this.property.getValue();

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerNotValid3()
    {
        final BiFunction<String, String, String> checker = (final String oldValue, final String newValue) ->
        {
            return newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;
        };

        final String expected = "0";

        this.property.setValue("0");

        this.property.setChecker(checker);
        this.property.setValue("2");

        final String actual = this.property.getValue();

        Assert.assertEquals(actual, expected);
    }

}