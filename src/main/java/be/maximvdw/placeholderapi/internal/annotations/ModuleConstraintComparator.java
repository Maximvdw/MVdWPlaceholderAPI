package be.maximvdw.placeholderapi.internal.annotations;

import java.util.Comparator;

/**
 * ModuleConstraintComparator
 *
 * Created by maxim on 25-Jan-17.
 */
public class ModuleConstraintComparator implements Comparator<ModuleConstraint>{
    @Override
    public int compare(ModuleConstraint o1, ModuleConstraint o2) {
        int priority1 = o1.type().getPriority();
        int priority2 = o2.type().getPriority();
        return priority1 > priority2 ? 1 : priority1 < priority2 ? -1 : 0;
    }
}
