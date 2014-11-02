package io.protostuff.gradle

import io.protostuff.compiler.ProtoModule

/**
 * This class extends original protostuff-compiler proto modules for the plugin dsl
 *
 * @author Konstantin Shchepanovskyi
 */
class ProtostuffModule extends ProtoModule {

    final String name

    ProtostuffModule(String name) {
        this.name = name
    }
}
