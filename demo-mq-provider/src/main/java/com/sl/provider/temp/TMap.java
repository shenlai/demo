package com.sl.provider.temp;

public class TMap {

    private static final int num = 16;
    private MapKeyValue[] table;

    public TMap() {
        table = new MapKeyValue[num];
    }

    public void put(String key, String value) {
        int index = tempHash(key);
        if (table[index] == null) {
            MapKeyValue keyValue = new MapKeyValue();
            keyValue.setKey(key);
            keyValue.setValue(value);
            table[index] = keyValue;
        } else {
            MapKeyValue keyValue = table[index];
            if (!keyValue.getValue().equals(value)) {
                keyValue.setValue(value);
            }
        }
    }

    public MapKeyValue get(String key) {
        int index = tempHash(key);
        return table[index];
    }

    private int tempHash(String key) {
        if (key == null) {
            return 0;
        }
        int index = key.hashCode() % num;
        if (index < 0) {
            return 0 - index;
        }
        return index;
    }


    private class MapKeyValue {
        private String key;
        private String value;
        private MapKeyValue next;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
