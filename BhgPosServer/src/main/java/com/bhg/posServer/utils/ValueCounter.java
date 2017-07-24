package com.bhg.posServer.utils;


import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class ValueCounter<K> implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	private Map<K, Integer> map = new TreeMap<K, Integer>();
	
	public void filter(int minValue, int maxValue) {
		Set<K> set = new HashSet<K>(map.keySet());
		for(K k: set) {
			if(map.get(k) > maxValue || map.get(k) < minValue) {
				map.remove(k);
			}
		}
	}
    
    public void put(K k) {
        Integer count = map.get(k);
        if(count == null) {
            count = 0;
            map.put(k, 1);
        } else {
            map.put(k, count + 1);
        }
    }
    
    public void putAll(Collection<K> keys) {
        for(K k: keys) {
            put(k);
        }
    }
    
    public void putSets(Collection<Set<K>> keySets) {
        for(Set<K> keys: keySets) {
            putAll(keys);
        }
    }
    
    public int get(K k) {
        Integer count = map.get(k);
        return count == null ? 0 : count;
    }
    
    public List<K> hot(int n) {
        return MapUtils.sortByValue(map).subList(0, Math.min(map.size(), n));
    }
    
    public Set<K> keySet() {
        return map.keySet();
    }
    
    public int size() {
        return map.size();
    }

	public Map<K, Integer> getMap() {
		return map;
	}
    
    public void clear() {
    	map.clear();
    }

}
