package fr.ourten.teabeans.test;

import fr.ourten.teabeans.value.BaseProperty;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BasePropertyTest
{
    @Test
    public void testPropertyName()
    {
        final BaseProperty<String> property = new BaseProperty<>("value", "name");
        final BaseProperty<String> property2 = new BaseProperty<>("value");

        assertThat(property.getName()).isEqualTo("name");
        assertThat(property2.getName()).isEmpty();
    }

    @Test
    public void testPropertyValue()
    {
        final BaseProperty<Float> property = new BaseProperty<>(5f, "testFloatProperty");

        assertThat(property.getValue()).isEqualTo(5f);
    }

    @Test
    public void testPropertyBinding()
    {
        final BaseProperty<Integer> property1 = new BaseProperty<>(3, "testIntegerProperty1");
        final BaseProperty<Integer> property2 = new BaseProperty<>(5, "testIntegerProperty1");

        property2.bind(property1);
        assertThat(property1.getValue()).isEqualTo(property2.getValue());

        property1.setValue(10);
        assertThat(property2.getValue()).isEqualTo(10);

        assertThat(property2.isBound()).isTrue();
    }

    @Test
    public void testBidirectionnalPropertyBinding()
    {
        final BaseProperty<Integer> property1 = new BaseProperty<>(3, "testIntegerProperty1");
        final BaseProperty<Integer> property2 = new BaseProperty<>(5, "testIntegerProperty1");

        property2.bindBidirectional(property1);

        assertThat(property1.getValue()).isEqualTo(property2.getValue());

        property1.setValue(10);
        assertThat(property2.getValue()).isEqualTo(10);

        property2.setValue(15);
        assertThat(property1.getValue()).isEqualTo(15);
    }

    @Test(expected = RuntimeException.class)
    public void testBindingError()
    {
        final BaseProperty<Integer> property1 = new BaseProperty<>(3, "testIntegerProperty1");
        final BaseProperty<Integer> property2 = new BaseProperty<>(5, "testIntegerProperty1");

        property2.bind(property1);

        property2.setValue(2);
    }
}