package fr.ourten.teabeans.test;

import fr.ourten.teabeans.property.Property;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PropertyTest
{
    @Test
    public void testPropertyValue()
    {
        Property<Float> property = new Property<>(5f);

        assertThat(property.getValue()).isEqualTo(5f);
    }

    @Test
    public void testPropertyBinding()
    {
        Property<Integer> property1 = new Property<>(3);
        Property<Integer> property2 = new Property<>(5);

        property2.bindProperty(property1);
        assertThat(property1.getValue()).isEqualTo(property2.getValue());

        property1.setValue(10);
        assertThat(property2.getValue()).isEqualTo(10);

        assertThat(property2.isBound()).isTrue();
    }

    @Test
    public void testBidirectionnalPropertyBinding()
    {
        Property<Integer> property1 = new Property<>(3);
        Property<Integer> property2 = new Property<>(5);

        property2.bindBidirectional(property1);

        assertThat(property1.getValue()).isEqualTo(property2.getValue());

        property1.setValue(10);
        assertThat(property2.getValue()).isEqualTo(10);

        property2.setValue(15);
        assertThat(property1.getValue()).isEqualTo(15);
    }

    @Test
    public void testBindingError()
    {
        Property<Integer> property1 = new Property<>(3);
        Property<Integer> property2 = new Property<>(5);

        property2.bindProperty(property1);

        assertThrows(RuntimeException.class, () -> property2.setValue(2));
    }
}