package io.protostuff.gradle

import org.gradle.api.NamedDomainObjectContainer


/**
 * This is the Gradle extension that configures the Protostuff plugin.  All
 * configuration options will be in the {@code protostuff} block of the
 * build.gradle file.  This block consists of a list of proto modules and
 * optional compiler properties.
 *
 * @author Konstantin Shchepanovskyi
 */
class ProtostuffExtension {

    /**
     * Define the list of proto modules for code generation.
     */
    final NamedDomainObjectContainer<ProtostuffModule> modules

    /**
     * When {@code true}, the protos are cached for re-use. This matters when
     * a certain proto is also used/imported by other modules.
     */
    boolean cacheProtos

    ProtostuffExtension(NamedDomainObjectContainer<ProtostuffModule> modules) {
        this.modules = modules
    }

    def modules(Closure closure) {
        modules.configure(closure)
    }

}
