package com.sumilux.ssi.client;

import java.util.Map;

public class Parameter implements Map.Entry<String, String> {

    public Parameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private final String key;

    private String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String setValue(String value) {
        try {
            return this.value;
        } finally {
            this.value = value;
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Parameter that = (Parameter) obj;
        if (key == null) {
            if (that.key != null)
                return false;
        } else if (!key.equals(that.key))
            return false;
        if (value == null) {
            if (that.value != null)
                return false;
        } else if (!value.equals(that.value))
            return false;
        return true;
    }
}