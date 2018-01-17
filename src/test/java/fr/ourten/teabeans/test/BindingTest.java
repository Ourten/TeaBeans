package fr.ourten.teabeans.test;

import fr.ourten.teabeans.binding.BaseBinding;
import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.BaseProperty;
import fr.ourten.teabeans.value.Observable;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BindingTest
{
    private int count;

    @Before
    public void setup()
    {
        this.count = 0;
    }

    @Test
    public void testBaseBinding()
    {
        final BaseProperty<String> p1 = new BaseProperty<>("none", "testStringProperty");
        final BaseProperty<String> p2 = new BaseProperty<>("nothing", "testStringProperty2");

        final BaseBinding<String> binding = new BaseBinding<String>()
        {
            {
                super.bind(p1);
                super.bind(p2);
            }

            @Override
            public String computeValue()
            {
                BindingTest.this.count++;
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
        assertThat(this.count).isEqualTo(3);
    }

    @Test
    public void testChainedBinding()
    {
        final BaseProperty<String> p1 = new BaseProperty<>("none", "testStringProperty");
        final BaseProperty<String> p2 = new BaseProperty<>("nothing", "testStringProperty2");

        final BaseProperty<String> p3 = new BaseProperty<>("", "testStringProperty3");

        p3.bind(new BaseBinding<String>()
        {
            {
                super.bind(p1);
                super.bind(p2);
            }

            @Override
            public String computeValue()
            {
                BindingTest.this.count++;
                return p1.getValue() + p2.getValue();
            }
        });

        assertThat(p3.getValue()).isEqualTo("nonenothing");
        assertThat(this.count).isEqualTo(1);
    }

    @Test
    public void testBindingUnbinding()
    {
        final BaseProperty<String> p1 = new BaseProperty<>("none", "testStringProperty");
        final BaseProperty<String> p2 = new BaseProperty<>("nothing", "testStringProperty2");

        final BaseBinding<String> binding = new BaseBinding<String>()
        {
            {
                super.bind(p1);
                super.bind(p2);
            }

            @Override
            public String computeValue()
            {
                BindingTest.this.count++;
                return p1.getValue() + p2.getValue();
            }
        };

        assertThat(binding.getDependencies().toArray()).containsExactly(new Observable[] { p1, p2 });

        binding.unbind(p1, p2);

        assertThat(binding.getDependencies().toArray()).containsExactly(new Observable[0]);
    }

    @Test
    public void testUnidirectionnalUnbinding()
    {
        final BaseProperty<String> p1 = new BaseProperty<>("none", "testStringProperty");
        final BaseProperty<String> p2 = new BaseProperty<>("nothing", "testStringProperty2");

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
        final BaseProperty<String> p1 = new BaseProperty<>("none", "testStringProperty");
        final BaseProperty<String> p2 = new BaseProperty<>("nothing", "testStringProperty2");

        final BaseBinding<String> binding = new BaseBinding<String>()
        {
            {
                super.bind(p1);
                super.bind(p2);
            }

            @Override
            public String computeValue()
            {
                BindingTest.this.count++;
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
        final BaseProperty<String> p1 = new BaseProperty<>("none", "testStringProperty");
        final BaseProperty<String> p2 = new BaseProperty<>("nothing", "testStringProperty2");

        final BaseBinding<String> binding = new BaseBinding<String>()
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

        final ValueChangeListener<String> listener = (observable, oldValue, newValue) -> BindingTest.this.count++;
        final ValueInvalidationListener listener2 = observable -> BindingTest.this.count++;

        binding.addListener(listener);
        binding.addListener(listener2);
        binding.getValue();

        assertThat(this.count).isEqualTo(1);

        p1.setValue("none");

        assertThat(this.count).isEqualTo(2);
        binding.removeListener(listener);
        p1.setValue("test");

        assertThat(this.count).isEqualTo(3);

        binding.removeListener(listener2);
        p1.setValue("testagain");

        assertThat(this.count).isEqualTo(3);
    }

    @Test
    public void testNullBinding()
    {
        final BaseProperty<String> p1 = new BaseProperty<>("nonnull", "nullProperty");

        final BaseBinding<String> binding = new BaseBinding<String>()
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

        binding.addListener((observable, oldValue, newValue) -> BindingTest.this.count++);

        assertThat(binding.getValue()).isNotNull();
        assertThat(this.count).isEqualTo(1);

        p1.setValue(null);

        assertThat(binding.getValue()).isNull();
        assertThat(this.count).isEqualTo(2);

        binding.invalidate();

        assertThat(binding.getValue()).isNull();
        assertThat(this.count).isEqualTo(2);

        p1.setValue(null);

        assertThat(this.count).isEqualTo(2);

        p1.setValue("nonnull");

        assertThat(binding.getValue()).isNotNull();
        assertThat(this.count).isEqualTo(3);

        binding.invalidate();

        assertThat(binding.getValue()).isNotNull();
        assertThat(this.count).isEqualTo(3);
    }
}