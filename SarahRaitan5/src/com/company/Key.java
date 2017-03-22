package com.company;
import java.io.Serializable;

public interface Key extends Serializable {
    Object getKey();
    void setKey( Object key );
}