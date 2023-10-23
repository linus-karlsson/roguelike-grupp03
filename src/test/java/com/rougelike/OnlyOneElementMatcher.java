package com.rougelike;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class OnlyOneElementMatcher<T> extends TypeSafeMatcher<List<T>> {
    private T value;

    public OnlyOneElementMatcher(T value) {
        this.value = value;
    }

    @Override
    protected boolean matchesSafely(List<T> array) {
        for (T element : array) {
            if (!value.equals(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("A list containing only ").appendValue(value);
    }

    public static <T> OnlyOneElementMatcher<T> containsOnly(T value) {
        return new OnlyOneElementMatcher<T>(value);
    }
}
