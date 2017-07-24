package com.bhg.posServer.utils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class MapUtils {
    
    public static <K, V> List<K> sortByValue(Map<K, V> map) {

        List<Map.Entry<K, V>> list = new ArrayList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @SuppressWarnings("unchecked")
			@Override
            public int compare(Entry<K, V> entry1, Entry<K, V> entry12) {
                Comparable<V> comparable1 = (Comparable<V>) entry1.getValue();
                return - comparable1.compareTo(entry12.getValue());
            }
            
        });
        
        List<K> result = new ArrayList<K>();
        for(Map.Entry<K, V> entry: list) {
            result.add(entry.getKey());
        }
        
        return result;
    }
    
    
    public static <T> void mergIntegerFloatMap(Map<T, Float> map, Map<T, Float> other) {
    	for(T key: other.keySet()) {
	        Float value = map.get(key);
	        if(value == null) {
	        	map.put(key, other.get(key));
	        } else {
	        	map.put(key, map.get(key) + other.get(key));
	        }
	    }
	}
    
    public static <K, V> Map<K, V> sort(Map<K, V> map) {
    	return sort(map, map.size());
    }
    
	public static <K, V> Map<K, V> sort(Map<K, V> map, int count) {
    	Map<K, V> result = new LinkedHashMap<K, V>();
    	int index = 0;
    	for(K key: MapUtils.sortByValue(map)) {
    		if(index ++ < count) {
    			result.put(key, map.get(key));
    		}
    	}
    	return result;
    }
    
    public static void main(String[] args) {
        Map<Integer, Double> map = new TreeMap<Integer, Double>();
        map.put(1, 4.1);
        map.put(2, 3.1);
        map.put(3, 5.1);
        
        System.out.println(map.keySet());
        
        System.out.println(MapUtils.sortByValue(map));
        
    }
    
    
        
}
