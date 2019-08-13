package cn.gyyx.bts.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * Created by lishile on 2016/3/3.
 */
public class Values {

    private HashMap<String, Object> values;

    public Values() {
        values = new HashMap<String, Object>(8);
    }

    public Values(int size) {
        values = new HashMap<String, Object>(size, 1.0f);
    }

    public Values(Values from) {
        values = new HashMap<String, Object>(from.values);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Values)) {
            return false;
        }
        return values.equals(((Values) object).values);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

    public void put(String key, String value) {
        values.put(key, value);
    }

    public void putAll(Values other) {
        values.putAll(other.values);
    }

    public void put(String key, Byte value) {
        values.put(key, value);
    }

    public void put(String key, Short value) {
        values.put(key, value);
    }

    public void put(String key, Integer value) {
        values.put(key, value);
    }

    public void put(String key, Long value) {
        values.put(key, value);
    }

    public void put(String key, Float value) {
        values.put(key, value);
    }

    public void put(String key, Double value) {
        values.put(key, value);
    }

    public void put(String key, Boolean value) {
        values.put(key, value);
    }

    public void put(String key, byte[] value) {
        values.put(key, value);
    }

    public void putNull(String key) {
        values.put(key, null);
    }

    public int size() {
        return values.size();
    }

    public void remove(String key) {
        values.remove(key);
    }

    public void clear() {
        values.clear();
    }

    public boolean containsKey(String key) {
        return values.containsKey(key);
    }

    public Object get(String key) {
        return values.get(key);
    }

    public String getAsString(String key) {
        Object value = values.get(key);
        return value != null ? value.toString() : null;
    }

    public Long getAsLong(String key) {
        Object value = values.get(key);
        if(value==null) {
        	return null;
        }
        if(value instanceof Number) {
        	return ((Number) value).longValue();
        }
        if(value instanceof CharSequence) {
        	return Long.valueOf(value.toString());
        }
        return null;
    }

    public Integer getAsInteger(String key) {
    	 Object value = values.get(key);
         if(value==null) {
         	return null;
         }
         if(value instanceof Number) {
         	return ((Number) value).intValue();
         }
         if(value instanceof CharSequence) {
         	return Integer.valueOf(value.toString());
         }
         return null;
    }

    public Short getAsShort(String key) {
    	 Object value = values.get(key);
         if(value==null) {
         	return null;
         }
         if(value instanceof Number) {
         	return ((Number) value).shortValue();
         }
         if(value instanceof CharSequence) {
         	return Short.valueOf(value.toString());
         }
         return null;
    }

    public Byte getAsByte(String key) {
    	 Object value = values.get(key);
         if(value==null) {
         	return null;
         }
         if(value instanceof Number) {
         	return ((Number) value).byteValue();
         }
         if(value instanceof CharSequence) {
         	return Byte.valueOf(value.toString());
         }
         return null;
    }

    public Double getAsDouble(String key) {
    	 Object value = values.get(key);
         if(value==null) {
         	return null;
         }
         if(value instanceof Number) {
         	return ((Number) value).doubleValue();
         }
         if(value instanceof CharSequence) {
         	return Double.valueOf(value.toString());
         }
         return null;
    }

    public Float getAsFloat(String key) {
    	 Object value = values.get(key);
         if(value==null) {
         	return null;
         }
         if(value instanceof Number) {
         	return ((Number) value).floatValue();
         }
         if(value instanceof CharSequence) {
         	return Float.valueOf(value.toString());
         }
         return null;
    }

    public Boolean getAsBoolean(String key) {
        Object value = values.get(key);
        if(value instanceof Boolean) {
        	return (Boolean)value;
        }
        if(value instanceof CharSequence) {
        	return Boolean.valueOf(value.toString());
        }
        return null;
    }

    public byte[] getAsByteArray(String key) {
        Object value = values.get(key);
        if (value instanceof byte[]) {
            return (byte[]) value;
        } else {
            return null;
        }
    }

    public Set<Map.Entry<String, Object>> valueSet() {
        return values.entrySet();
    }

    public Set<String> keySet() {
        return values.keySet();
    }

    /**
     * Unsupported, here until we get proper bulk insert APIs. {@hide}
     */
    @Deprecated
    public void putStringList(String key, List<String> value) {
        values.put(key, value);
    }

    /**
     * Unsupported, here until we get proper bulk insert APIs. {@hide}
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    public List<String> getStringList(String key) {
        return (List<String>) values.get(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String name : values.keySet()) {
            String value = getAsString(name);
            if (sb.length() > 0)
                sb.append(" ");
            sb.append(name + "=" + value);
        }
        return sb.toString();
    }



}
