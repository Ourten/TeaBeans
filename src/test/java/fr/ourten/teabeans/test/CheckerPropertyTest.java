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
        this.property = new BaseProperty<String>("test");
    }

    @Test
    public void testCheckerNull()
    {
        BiFunction<String, String, String> expected = null;
        BiFunction<String, String, String> actual = this.property.getChecker();

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerNotNull()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
        {
            return newValue != null && !newValue.isEmpty() ? newValue : oldValue;
        };

        this.property.setChecker(checker);
        BiFunction<String, String, String> expected = checker;
        BiFunction<String, String, String> actual = this.property.getChecker();

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
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
        {
            return newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;
        };

        String expected = "42";

        this.property.setValue("0");

        this.property.setChecker(checker);
        this.property.setValue("42");

        String actual = this.property.getValue();

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerNotValid1()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
        {
            return newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;
        };

        String expected = "0";

        this.property.setValue("0");

        this.property.setChecker(checker);
        this.property.setValue("");

        String actual = this.property.getValue();

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerNotValid2()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
        {
            return newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;
        };

        String expected = "0";

        this.property.setValue("0");

        this.property.setChecker(checker);
        this.property.setValue(null);

        String actual = this.property.getValue();

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerNotValid3()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
        {
            return newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;
        };

        String expected = "0";

        this.property.setValue("0");

        this.property.setChecker(checker);
        this.property.setValue("2");

        String actual = this.property.getValue();

        Assert.assertEquals(actual, expected);
    }

}