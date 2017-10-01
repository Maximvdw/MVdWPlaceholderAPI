package be.maximvdw.placeholderapi.internal.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ModuleAuthor
 *
 * Created by maxim on 15-Jan-17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleAuthor {
    /**
     * Module author
     * @return value
     */
    String value();
}
