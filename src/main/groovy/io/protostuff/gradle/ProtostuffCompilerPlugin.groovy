package io.protostuff.gradle

import io.protostuff.compiler.CachingProtoLoader
import io.protostuff.compiler.CompilerMain
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Protostuff Compiler Plugin for Gradle
 *
 * @author Konstantin Shchepanovskyi
 */
class ProtostuffCompilerPlugin implements Plugin<Project> {

    public static final String PROTOSTUFF_EXTENSION = 'protostuff'
    public static final String GENERATE_SOURCE_TASK = 'generateSource'

    @Override
    void apply(Project project) {

        def modules = project.container(ProtostuffModule)

        project.configure(project) {
            extensions.create(PROTOSTUFF_EXTENSION, ProtostuffExtension, modules)
        }

        project.task(GENERATE_SOURCE_TASK) << {
            ProtostuffExtension extension = project.protostuff;
            CachingProtoLoader loader = extension.cacheProtos ? new CachingProtoLoader() : null;
            extension.modules.each { m ->
                m.setCachingProtoLoader(loader);
                CompilerMain.compile(m);
            }
        }
    }
}
