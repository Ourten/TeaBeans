package fr.ourten.teabeans.binding;

import fr.ourten.teabeans.property.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BidirectionalBindingTest
{
    private int count;

    @BeforeEach
    public void setup()
    {
        count = 0;
    }

    @Test
    public void testComplexBinding()
    {
        Property<String> p1 = new Property<>("none");
        Property<String> p2 = new Property<>("nothing");

        Property<String> p3 = new Property<>("toy");

        Binding<String> binding = new Binding<String>()
        {
            {
                super.bind(p1);
                super.bind(p2);
            }

            @Override
            public String computeValue()
            {
                count++;
                return p1.getValue() + p2.getValue();
            }
        };

        p2.bindBidirectional(p3);

        assertThat(binding.getValue()).isEqualTo("nonetoy");

        p1.bindProperty(p3);

        assertThat(binding.getValue()).isEqualTo("toytoy");
        assertThat(count).isEqualTo(2);
    }

    @Test
    public void testBidirectionalUnbinding()
    {
        Property<String> p1 = new Property<>("none");
        Property<String> p2 = new Property<>("nothing");

        p1.bindBidirectional(p2);

        assertThat(p1.getValue()).isEqualTo(p2.getValue());

        p2.setValue("lalala");

        assertThat(p1.getValue()).isEqualTo(p2.getValue());

        p1.unbindBidirectional(p2);

        p2.setValue("another value");

        assertThat(p1.getValue()).isNotEqualTo(p2.getValue());
    }

    @Test
    public void testBidirectionalBindingEquals()
    {
        Property<String> p1 = new Property<>("none");
        Property<String> p2 = new Property<>("nothing");

        BidirectionalBinding<String> binding1 = new BidirectionalBinding<>(p1, p2);

        assertThat(binding1).isEqualTo(binding1);
    }

    @Test
    public void testBidirectionalBindingNotEquals()
    {
        Property<String> p1 = new Property<>("none");
        Property<String> p2 = new Property<>("nothing");
        Property<String> p3 = new Property<>("nowhere");

        BidirectionalBinding<String> binding1 = new BidirectionalBinding<>(p1, p2);
        BidirectionalBinding<String> binding3 = new BidirectionalBinding<>(p2, p3);

        assertThat(binding1.equals(binding3)).isFalse();
    }

    @Test
    public void equals_givenIdenticalAndInvertedBinding_thenShouldMakeEqualityCheck()
    {
        Property<String> p1 = new Property<>("none");
        Property<String> p2 = new Property<>("nothing");
        Property<String> p3 = new Property<>("nothing again");

        BidirectionalBinding<String> binding1 = new BidirectionalBinding<>(p1, p2);

        BidirectionalBinding<String> sameBinding = new BidirectionalBinding<>(p1, p2);
        BidirectionalBinding<String> invertedBinding = new BidirectionalBinding<>(p2, p1);

        BidirectionalBinding<String> differingBinding = new BidirectionalBinding<>(p2, p3);

        assertThat(binding1).isEqualTo(sameBinding);
        assertThat(sameBinding).isEqualTo(invertedBinding);

        assertThat(sameBinding).isNotEqualTo(differingBinding);
    }
}