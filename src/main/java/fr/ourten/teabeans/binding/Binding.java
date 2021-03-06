package fr.ourten.teabeans.binding;

public abstract class Binding<T> extends BindingBase<T>
{
    protected T value;

    @Override
    public T getValue()
    {
        if (!isValid())
        {
            T computed = computeValue();

            if (!isMuted())
            {
                if (computed == null && value != null)
                {
                    fireChangeArglessListeners();
                    fireChangeListeners(value, null);
                }
                else if (computed != null && !computed.equals(value))
                {
                    fireChangeArglessListeners();
                    fireChangeListeners(value, computed);
                }
            }
            value = computed;
            setValid(true);
        }
        return value;
    }

    protected abstract T computeValue();
}