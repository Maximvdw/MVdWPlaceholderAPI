package be.maximvdw.placeholderapi.internal.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Module Constraint
 * <p>
 * Created by maxim on 15-Jan-17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleConstraint {
    /**
     * Constraint type of the module
     */
    ContraintType type();

    /**
     * Constraint value
     *
     * @return constraint value
     */
    String value();

    /**
     * Module constraint type
     */
    enum ContraintType {
        /**
         * Make sure this plugin is loaded
         */
        PLUGIN,
        /**
         * Make sure the plugin CONTAINS this version string
         */
        PLUGIN_VERSION,
        /**
         * Make sure the plugin version is lower
         */
        PLUGIN_VERSION_IS_LOWER,
        /**
         * Make sure the plugin version is higher
         */
        PLUGIN_VERSION_IS_HIGHER,
        /**
         * Make sure the plugin has this class for main (written like in plugin.yml)
         */
        PLUGIN_MAIN,
        /**
         * Plugin author
         */
        PLUGIN_AUTHOR,
        /**
         * QAPlugin version higher
         */
        QAPLUGIN_VERSION
    }
}
