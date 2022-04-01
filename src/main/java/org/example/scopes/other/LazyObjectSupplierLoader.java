package org.example.scopes.other;

import java.util.Objects;

public abstract class LazyObjectSupplierLoader<T> {
    private boolean initialised;
    private T object;

    /**
     * Метод надлежит переопределить в классе и отметить @Nullable или @NonNull
     * делается это для того, чтобы навигация по коду срабатывала в нужный класс и было очевиднее
     */
    protected T get() {
        if (!initialised) {
            object = load();
            initialised = true;
        }
        return object;
    }

    protected abstract T load();

    @Override
    public String toString() {
        return "lazy(" + (initialised ? Objects.toString(object) : "undef") + ")";
    }
}