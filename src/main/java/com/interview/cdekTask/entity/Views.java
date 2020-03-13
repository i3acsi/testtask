package com.interview.cdekTask.entity;

public final class Views {
    public interface CourierView {}
    public interface OperatorView extends CourierView {}
    public interface FullView extends OperatorView {}
}
