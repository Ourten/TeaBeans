package fr.ourten.teabeans.property;

import fr.ourten.teabeans.binding.WeakObservableListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.listener.recorder.ValueChangeRecorder;
import fr.ourten.teabeans.listener.recorder.ValueInvalidationRecorder;
import fr.ourten.teabeans.test.GCUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

        assertThrows(UnsupportedOperationException.class, () -> property2.setValue(2));
    }

    @Test
    void bindProperty_givenAlreadyBoundWithSame_thenShouldNotRebind()
    {
        Property<Integer> property = new Property<>(0);
        Property<Integer> secondProperty = new Property<>(10);

        ValueInvalidationRecorder invalidationRecorder = new ValueInvalidationRecorder(property);
        ValueChangeRecorder<Integer> valueChangeRecorder = new ValueChangeRecorder<>(property);

        property.bindProperty(secondProperty);

        assertThat(invalidationRecorder.getCount()).isEqualTo(1);
        assertThat(valueChangeRecorder.getCount()).isEqualTo(1);

        property.bindProperty(secondProperty);

        assertThat(invalidationRecorder.getCount()).isEqualTo(1);
        assertThat(valueChangeRecorder.getCount()).isEqualTo(1);
    }

    @Test
    void mute_givenMutedProperty_thenShouldNotPropagateChanges()
    {
        Property<Integer> property = new Property<>(0);
        property.mute();

        ValueInvalidationRecorder invalidationRecorder = new ValueInvalidationRecorder(property);
        ValueChangeRecorder<Integer> valueChangeRecorder = new ValueChangeRecorder<>(property);

        property.setValue(0);
        property.setValue(5);

        Property<Integer> secondProperty = new Property<>(10);
        property.bindProperty(secondProperty);

        assertThat(invalidationRecorder.getCount()).isEqualTo(0);
        assertThat(valueChangeRecorder.getCount()).isEqualTo(0);
    }

    @Test
    void unmute_givenMutedPropertyThenUnmuted_thenShouldPropagateChanges()
    {
        Property<Integer> property = new Property<>(0);
        property.mute();

        ValueInvalidationRecorder invalidationRecorder = new ValueInvalidationRecorder(property);
        ValueChangeRecorder<Integer> valueChangeRecorder = new ValueChangeRecorder<>(property);

        property.setValue(3);
        property.setValue(5);

        property.unmute();

        assertThat(invalidationRecorder.getCount()).isEqualTo(1);
        assertThat(valueChangeRecorder.getCount()).isEqualTo(1);
        assertThat(valueChangeRecorder.getOldValues()).containsExactly(0);
        assertThat(valueChangeRecorder.getNewValues()).containsExactly(5);
    }

    @Test
    void muteWhile_givenRunnable_thenShouldMuteOnlyDuringExecution()
    {
        Property<Integer> property = new Property<>(0);

        ValueInvalidationRecorder invalidationRecorder = new ValueInvalidationRecorder(property);
        ValueChangeRecorder<Integer> valueChangeRecorder = new ValueChangeRecorder<>(property);

        property.muteWhile(() ->
        {
            property.setValue(3);
            property.setValue(6);
            property.setValue(5);
        });

        assertThat(invalidationRecorder.getCount()).isEqualTo(1);
        assertThat(valueChangeRecorder.getCount()).isEqualTo(1);
        assertThat(valueChangeRecorder.getOldValues()).containsExactly(0);
        assertThat(valueChangeRecorder.getNewValues()).containsExactly(5);
    }

    @Test
    void addListener_givenBoundPropertyThenAddingInvalidationListener_thenShouldStartObserving()
    {
        Property<Integer> property = new Property<>(0);
        Property<Integer> secondProperty = spy(new Property<>(10));
        property.bindProperty(secondProperty);

        verify(secondProperty, never()).addListener(any(ValueInvalidationListener.class));

        new ValueInvalidationRecorder(property);

        verify(secondProperty, atMostOnce()).addListener(any(ValueInvalidationListener.class));
    }

    @Test
    void addListener_givenBoundPropertyThenAddingChangeListener_thenShouldStartObserving()
    {
        Property<Integer> property = new Property<>(0);
        Property<Integer> secondProperty = spy(new Property<>(10));
        property.bindProperty(secondProperty);

        verify(secondProperty, never()).addListener(any(ValueInvalidationListener.class));

        new ValueChangeRecorder<>(property);

        verify(secondProperty, atMostOnce()).addListener(any(ValueInvalidationListener.class));
    }

    @Test
    void garbageCollection_givenBindAndFreeingBound_thenShouldRemoveListener()
    {
        Property<Integer> p1 = new Property<>(0);
        Property<Integer> p2 = spy(new Property<>(10));

        p1.bindProperty(p2);
        p1.startObserving();

        p1 = null;
        GCUtils.fullFinalization();

        p2.invalidate();
        verify(p2).removeListener(any(WeakObservableListener.class));
    }
}