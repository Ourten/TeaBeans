package fr.ourten.teabeans.test;

import fr.ourten.teabeans.binding.Binding;
import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.Observable;
import fr.ourten.teabeans.value.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BindingTest
{
    private int count;

    @BeforeEach
    public void setup()
    {
        count = 0;
    }

    @Test
    public void testBaseBinding()
    {
        Property<String> p1 = new Property<>("none");
        Property<String> p2 = new Property<>("nothing");

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

        // Only here to check lazy evaluation
        assertThat(binding.getValue()).isNotNull();

        assertThat(binding.getValue()).isEqualTo("nonenothing");

        p1.setValue("lala");
        assertThat(binding.getValue()).isEqualTo("lalanothing");

        p2.setValue("toto");
        assertThat(binding.getValue()).isEqualTo("lalatoto");
        assertThat(count).isEqualTo(3);
    }

    @Test
    public void testChainedBinding()
    {
        Property<String> p1 = new Property<>("none");
        Property<String> p2 = new Property<>("nothing");

        Property<String> p3 = new Property<>("");

        p3.bind(new Binding<String>()
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
        });

        assertThat(p3.getValue()).isEqualTo("nonenothing");
        assertThat(count).isEqualTo(1);
    }

    @Test
    public void testBindingUnbinding()
    {
        Property<String> p1 = new Property<>("none");
        Property<String> p2 = new Property<>("nothing");

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

        assertThat(binding.getDependencies().toArray()).containsExactly(new Observable[]{p1, p2});

        binding.unbind(p1, p2);

        assertThat(binding.getDependencies().toArray()).containsExactly(new Observable[0]);
    }

    @Test
    public void testUnidirectionnalUnbinding()
    {
        Property<String> p1 = new Property<>("none");
        Property<String> p2 = new Property<>("nothing");

        p1.bind(p2);

        assertThat(p1.getValue()).isEqualTo(p2.getValue());

        p2.setValue("lalala");

        assertThat(p1.getValue()).isEqualTo(p2.getValue());

        p1.unbind();

        p2.setValue("another value");

        assertThat(p1.getValue()).isNotEqualTo(p2.getValue());
    }

    @Test
    public void testInvalidationBinding()
    {
        Property<String> p1 = new Property<>("none");
        Property<String> p2 = new Property<>("nothing");

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

        assertThat(binding.isValid()).isFalse();
        assertThat(binding.getValue()).isEqualTo("nonenothing");
        assertThat(binding.isValid()).isTrue();

        p1.setValue("another");

        assertThat(binding.isValid()).isFalse();
    }

    @Test
    public void testBindingListener()
    {
        Property<String> p1 = new Property<>("none");
        Property<String> p2 = new Property<>("nothing");

        Binding<String> binding = new Binding<String>()
        {
            {
                super.bind(p1);
                super.bind(p2);
            }

            @Override
            public String computeValue()
            {
                return p1.getValue() + p2.getValue();
            }
        };

        ValueChangeListener<String> listener = (observable, oldValue, newValue) -> count++;
        ValueInvalidationListener listener2 = observable -> count++;

        binding.addListener(listener);
        binding.addListener(listener2);
        binding.getValue();

        assertThat(count).isEqualTo(1);

        p1.setValue("none");

        assertThat(count).isEqualTo(2);
        binding.removeListener(listener);
        p1.setValue("test");

        assertThat(count).isEqualTo(3);

        binding.removeListener(listener2);
        p1.setValue("testagain");

        assertThat(count).isEqualTo(3);
    }

    @Test
    public void testNullBinding()
    {
        Property<String> p1 = new Property<>("nonnull");

        Binding<String> binding = new Binding<String>()
        {
            {
                super.bind(p1);
            }

            @Override
            public String computeValue()
            {
                return p1.getValue();
            }
        };

        binding.addListener((observable, oldValue, newValue) -> count++);

        assertThat(binding.getValue()).isNotNull();
        assertThat(count).isEqualTo(1);

        p1.setValue(null);

        assertThat(binding.getValue()).isNull();
        assertThat(count).isEqualTo(2);

        binding.invalidate();

        assertThat(binding.getValue()).isNull();
        assertThat(count).isEqualTo(2);

        p1.setValue(null);

        assertThat(count).isEqualTo(2);

        p1.setValue("nonnull");

        assertThat(binding.getValue()).isNotNull();
        assertThat(count).isEqualTo(3);

        binding.invalidate();

        assertThat(binding.getValue()).isNotNull();
        assertThat(count).isEqualTo(3);
    }
}