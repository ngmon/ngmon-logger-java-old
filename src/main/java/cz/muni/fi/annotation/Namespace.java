package cz.muni.fi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface Namespace {
    
    String value();
    
}