package fr.ourten.teabeans.test;

import fr.ourten.teabeans.value.BaseMapProperty;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.function.BiFunction;

public class CheckerMapPropertyTest
{
    BaseMapProperty<Integer, String> property;

    @Before
    public void setup()
    {
        this.property = new BaseMapProperty<>(new HashMap<>(), "test");
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
                newValue != null && !newValue.isEmpty() ? newValue : oldValue;

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
    public void testCheckerPutValid()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        String expected = "42";

        this.property.setElementChecker(checker);
        this.property.put(42, "42");

        String actual = this.property.get(42);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerAddNotValid1()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        String expected = null;

        this.property.setElementChecker(checker);
        this.property.put(-1, "");

        String actual = this.property.get(0);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerAddNotValid2()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        String expected = null;

        this.property.setElementChecker(checker);
        this.property.put(-1, null);

        String actual = this.property.get(0);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerAddNotValid3()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        String expected = null;

        this.property.setElementChecker(checker);
        this.property.put(-1, "2");

        String actual = this.property.get(0);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerSetValid()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        String expected = "42";
        this.property.put(0, "0");

        this.property.setElementChecker(checker);
        this.property.replace(0, "42");

        String actual = this.property.get(0);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerSetNotValid1()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        String expected = "0";
        this.property.put(0, "0");

        this.property.setElementChecker(checker);
        this.property.replace(0, "");

        String actual = this.property.get(0);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerSetNotValid2()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        String expected = "0";
        this.property.put(0, "0");

        this.property.setElementChecker(checker);
        this.property.replace(0, null);

        String actual = this.property.get(0);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckerSetNotValid3()
    {
        BiFunction<String, String, String> checker = (String oldValue, String newValue) ->
                newValue != null && !newValue.isEmpty() && newValue.length() > 1 ? newValue : oldValue;

        String expected = "0";
        this.property.put(0, "0");

        this.property.setElementChecker(checker);
        this.property.replace(0, "2");

        String actual = this.property.get(0);

        Assert.assertEquals(actual, expected);
    }
}
