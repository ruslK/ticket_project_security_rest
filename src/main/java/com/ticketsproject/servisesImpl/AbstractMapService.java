package com.ticketsproject.servisesImpl;


import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class AbstractMapService <T, ID> {
    protected Map<ID, T> map = new HashMap<>();

    T save(ID id, T object) {
        map.put(id, object);
        return object;
    };

    T findById(ID id) {
        return map.get(id);
    }

    List<T> findAll() {
        return new ArrayList<>(map.values());
    }

    void deleteById(ID id) {
        map.remove(id);
    }

    void delete(T object) {
        map.entrySet().removeIf(entry -> entry.getValue().equals(object));
    }


}
