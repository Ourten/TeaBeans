package fr.ourten.teabeans.test;

import fr.ourten.teabeans.binding.BaseBinding;
import fr.ourten.teabeans.binding.BidirectionalBinding;
import fr.ourten.teabeans.value.BaseProperty;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BidirectionnalBindingTest
{
    private int count;

    @Before
    public void setup()
    {
        this.count = 0;
    }

    @Test
    public void testComplexBinding()
    {
        final BaseProperty<String> p1 = new BaseProperty<>("none", "testStringProperty");
        final BaseProperty<String> p2 = new BaseProperty<>("nothing", "testStringProperty2");

        final BaseProperty<String> p3 = new BaseProperty<>("toy", "testStringProperty3");

        final BaseBinding<String> binding = new BaseBinding<String>()
        {
            {
                super.bind(p1);
                super.bind(p2);
            }

            @Override
            public String computeValue()
            {
                BidirectionnalBindingTest.this.count++;
                return p1.getValue() + p2.getValue();
            }
        };

        p2.bindBidirectional(p3);

        assertThat(binding.getValue()).isEqualTo("nonetoy");

        p1.bind(p3);

        assertThat(binding.getValue()).isEqualTo("toytoy");
        assertThat(this.count).isEqualTo(2);
    }

    @Test
    public void testBidirectionnalUnbinding()
    {
        final BaseProperty<String> p1 = new BaseProperty<>("none", "testStringProperty");
        final BaseProperty<String> p2 = new BaseProperty<>("nothing", "testStringProperty2");

        p1.bindBidirectional(p2);

        assertThat(p1.getValue()).isEqualTo(p2.getValue());

        p2.setValue("lalala");

        assertThat(p1.getValue()).isEqualTo(p2.getValue());

        p1.unbindBidirectional(p2);

        p2.setValue("another value");

        assertThat(p1.getValue()).isNotEqualTo(p2.getValue());
    }

    @Test
    public void testBidirectionnalBindingEquals()
    {
        final BaseProperty<String> p1 = new BaseProperty<>("none", "testStringProperty");
        final BaseProperty<String> p2 = new BaseProperty<>("nothing", "testStringProperty2");

        final BidirectionalBinding<String> binding1 = new BidirectionalBinding<>(p1, p2);

        assertThat(binding1).isEqualTo(binding1);
    }

    @Test
    public void testBidirectionnalBindingNotEquals()
    {
        final BaseProperty<String> p1 = new BaseProperty<>("none", "testStringProperty");
        final BaseProperty<String> p2 = new BaseProperty<>("nothing", "testStringProperty2");
        final BaseProperty<String> p3 = new BaseProperty<>("nowhere", "testStringProperty3");

        final BidirectionalBinding<String> binding1 = new BidirectionalBinding<>(p1, p2);
        final BidirectionalBinding<String> binding3 = new BidirectionalBinding<>(p2, p3);

        assertThat(binding1.equals(binding3)).isFalse();
    }
}