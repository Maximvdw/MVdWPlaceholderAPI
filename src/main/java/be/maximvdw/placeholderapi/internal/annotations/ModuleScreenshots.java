package be.maximvdw.placeholderapi.internal.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ModuleScreenshots
 * <p>
 * Created by maxim on 16-Jan-17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleScreenshots {
    /**
     * Screenshot links
     *
     * @return screenshot links
     */
    String[] value();
}
