package fr.ourten.teabeans.test;

import java.util.function.BiFunction;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import fr.ourten.teabeans.value.BaseListProperty;

public class CheckerListPropertyTest
{
    BaseListProperty<String> property;

    @Before
    public void setup()
    {
        this.property = new BaseListProperty<String>(Lists.newArrayList(), "test");
    }

    @Test
    public void testCheckerNull()
    {
        BiFunction<String, String, String> expected = null;
        BiFunction<String, String, String> actual = this.property.getElementChecker();

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerNotNull()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
        {
            return newValue != null && !newValue.isEmpty() ? newValue : oldValue;
        };

        this.property.setElementChecker(checker);
        BiFunction<String, String, String> expected = checker;
        BiFunction<String, String, String> actual = this.property.getElementChecker();

        Assert.assertEquals(actual, expected);
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
        {
            return newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;
        };

        String expected = "42";

        this.property.setElementChecker(checker);
        this.property.add("42");

        String actual = this.property.get(0);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerAddNotValid1()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
        {
            return newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;
        };

        String expected = null;

        this.property.setElementChecker(checker);
        this.property.add("");

        String actual = this.property.get(0);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerAddNotValid2()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
        {
            return newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;
        };

        String expected = null;

        this.property.setElementChecker(checker);
        this.property.add(null);

        String actual = this.property.get(0);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerAddNotValid3()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
        {
            return newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;
        };

        String expected = null;

        this.property.setElementChecker(checker);
        this.property.add("2");

        String actual = this.property.get(0);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerSetValid()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
        {
            return newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;
        };

        String expected = "42";
        this.property.add("0");

        this.property.setElementChecker(checker);
        this.property.set(0, "42");

        String actual = this.property.get(0);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerSetNotValid1()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
        {
            return newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;
        };

        String expected = "0";
        this.property.add("0");

        this.property.setElementChecker(checker);
        this.property.set(0, "");

        String actual = this.property.get(0);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerSetNotValid2()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
        {
            return newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;
        };

        String expected = "0";
        this.property.add("0");

        this.property.setElementChecker(checker);
        this.property.set(0, null);

        String actual = this.property.get(0);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerSetNotValid3()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
        {
            return newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;
        };

        String expected = "0";
        this.property.add("0");

        this.property.setElementChecker(checker);
        this.property.set(0, "2");

        String actual = this.property.get(0);

        Assert.assertEquals(actual, expected);
    }
}
