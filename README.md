# TeaBeans [![Build Status](https://ci.ferenyr.info/app/rest/builds/buildType:(id:Teabeans_Build)/statusIcon)](https://ci.ferenyr.info/viewType.html?buildTypeId=Teabeans_Build&guest=1) [![codecov](https://codecov.io/gh/Ourten/TeaBeans/branch/master/graph/badge.svg)](https://codecov.io/gh/Ourten/TeaBeans)

TeaBeans is a java beans and properties library. It allows to observe object changes and compute lazy expressions.

# Depending on TeaBeans

Add the following to your gradle build file :

```gradle
repositories {
    maven {
      url 'https://maven.ferenyr.info/artifactory/libs-release
    }
}

dependencies {
   compile 'fr.ourten:teabeans:<version number>'
}
```

# Examples

### Property declaration

A property can be declared using this syntax :

```java
Property<Double> property=new Property<>(5.5D);

        Double value=property.getValue(); // 5.5D

        property.setValue(6.5D);
```

### Property listeners

A property emit a couple of events when its value is set.

* **ValueInvalidationListener**, is fired every time the value is set, even if the new it the same as the previous one.

* **ValueChangeListener**, is fired every time the value change, only when the new value does not equals the previous.
  This listener provide the previous and new value.

* **ListValueChangeListener**, a special listener only used for ListProperty. He is fired every time a value is removed,
  added or replaced in a ListProperty. This listener provide the previous value (null in case addition) and the new
  value (null in a removal).

* **MapValueChangeListener**, a special listener only used for MapProperty. He is fired every time a value is removed,
  added or replaced in a MapProperty. This listener provide the changed key, the previous value (null in an addition)
  and the new value (null in a removal).

It is worth noting that when a listener is not needed anymore on a property it should be removed with the according
method.

### Property binding

A property can be bound to another and have its value automatically updated.

```java
Property<Double> original=new Property<>(5D);
        Property<Double> copy=new Property<>(0D);

        copy.bind(original);

        copy.getValue() // 5D
```

When a property is bound it can not have its value manually set and an exception will be thrown on attempt.

A second type of binding is the *BidirectionnalBinding*.

When a property A and a B are bidirectionnaly bound any changes made to A *or* B will update the value of the other. A
property bound by this type of binding remains modifiable by hand.

```java
Property<Double> firstBound=new Property<>(5D);
        Property<Double> secondBound=new Property<>(6D);

        secondBound.bindBidirectionnal(firstBound);

        secondBound.getValue(); // 5D

        secondBound.setValue(10D);

        firstBound.getValue() // 10D
```

When a *BidirectionnalBinding* is no longer used it should also be removed.

### Expression

*Expressions* are a subType of *Bindings*.

They help creating bindings with multiple dependencies and a specific supplied result. The result of the expression is
computed upon request and is only refreshed when one dependency has changed.

The BaseExpression class contains several helpers methods to build complex expressions.

```java 
Property<Integer> integerProperty = new Property<>(10");

Expression<Integer> multiplyExpression = Expression.transform(integerProperty, result -> result * 10);

multiplyExpression.getValue() // 100

integerProperty.setValue(5);

multiplyExpression.getValue() // 50
```

### ListProperty

A *ListProperty* is a special subType of *BaseProperty*. It store an observable list of values.

The *List* used for storing the values can be supplied in constructor. The *getValue()* of the ListProperty will return
an Immutable List.

While it is strongly discouraged the internal list can be directly modified with the *getModifiableValue()*.

Changing any value without using methods of the *ListProperty* will not propagate events nor trigger bindings refresh.

### SetProperty

A *SetProperty* is very similar to a *ListProperty* but its internal collection is a Set.

### MapProperty

A *MapProperty* is a special subType of *BaseProperty*. It store an observable map of values. It is backed by an
internal *Map* which can be supplied in constructor.
