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
    public static final String GENERATE_SOURCE_TASK = 'generateProto'
    public static final String CLEAN_GENERATE_TASK = 'cleanProto'

    @Override
    void apply(Project project) {

        def modules = project.container(ProtostuffModule)

        project.configure(project) {
            extensions.create(PROTOSTUFF_EXTENSION, ProtostuffExtension, modules)
        }

        project.afterEvaluate {

            def genTask = project.tasks.create(GENERATE_SOURCE_TASK) {
                description 'Generate java classes from all configured proto files'
                ProtostuffExtension extension = project.protostuff
                modules.each { inputs.file it.source }
                modules.each { outputs.dir it.outputDir }

                doLast {
                    CachingProtoLoader loader = extension.cacheProtos ? new CachingProtoLoader() : null
                    extension.modules.each { m ->
                        m.setCachingProtoLoader(loader)
                        CompilerMain.compile(m)
                    }
                }
            }

            def cleanTask = project.tasks.create(CLEAN_GENERATE_TASK) {
                description "Clean generated java sources"
                doLast {
                    modules.each { it.outputDir.deleteDir() }
                }
            }

            project.tasks.getByName('compileJava').dependsOn genTask
            project.tasks.getByName('clean').dependsOn cleanTask

            modules.each {
                project.sourceSets.main.java.srcDir it.outputDir
            }
        }
    }
}
