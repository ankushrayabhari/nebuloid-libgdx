package com.ankushrayabhari.nebuloid.core.entities;

/**
 * Created by ankushrayabhari on 8/6/17.
 */

import java.util.Comparator;

public class EntityComparator implements Comparator<Entity> {
    @Override
    public int compare(Entity entity1, Entity entity2) {
        return entity1.getzIndex()-entity2.getzIndex();
    }
}