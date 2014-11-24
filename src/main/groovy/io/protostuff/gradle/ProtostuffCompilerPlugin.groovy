package io.protostuff.gradle

import io.protostuff.compiler.CachingProtoLoader
import io.protostuff.compiler.CompilerMain
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet

/**
 * Protostuff Compiler Plugin for Gradle
 *
 * @author Konstantin Shchepanovskyi
 */
class ProtostuffCompilerPlugin implements Plugin<Project> {

    public static final String PROTOSTUFF_EXTENSION = 'protostuff'
    public static final String GENERATE_SOURCE_TASK = 'generateProto'
    public static final String CLEAN_GENERATE_TASK = 'cleanProto'

    @Override
    void apply(Project project) {

        def modules = project.container(ProtostuffModule)

        project.configure(project) {
            extensions.create(PROTOSTUFF_EXTENSION, ProtostuffExtension, modules)
        }

        project.afterEvaluate {
            def sources = []
            def outs = []

            project.protostuff.modules.each { m ->
                sources.add(m.source)
                outs.add(m.outputDir)
            }

            project.tasks.create(GENERATE_SOURCE_TASK) {
                description 'Generate java classes from all configured proto files'
                ProtostuffExtension extension = project.protostuff;
                inputs.file sources
                outputs.dir outs

                doLast {
                    CachingProtoLoader loader = extension.cacheProtos ? new CachingProtoLoader() : null;
                    extension.modules.each { m ->
                        m.setCachingProtoLoader(loader);
                        CompilerMain.compile(m);
                    }
                }

            }

            def genTask = project.tasks.getByName(GENERATE_SOURCE_TASK)
            project.tasks.getByName('compileJava').dependsOn genTask

            outs.each {
                project.sourceSets.main.java.srcDir it
            }
        }
    }
}
