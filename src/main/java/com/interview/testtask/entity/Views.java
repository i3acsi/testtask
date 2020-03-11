package com.interview.testtask.entity;

public final class Views {
    public interface SimpleView{}
    public interface CommonView extends SimpleView{}
    public interface FullView extends CommonView{}
}
