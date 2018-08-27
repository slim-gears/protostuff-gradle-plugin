package io.protostuff.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

class ProtostuffCompilerPluginTest extends GroovyTestCase {
    Project project

    @Before
    void setUp() {
        project = ProjectBuilder.builder()
                .withName('testProj')
                .withProjectDir(new File('C:\\tmp'))
                .build()
    }

    @Test
    void testPlugin() {
        project.apply plugin: 'java'
        project.apply plugin: 'io.protostuff.compiler'
        project.protostuff {
            modules {
                testModule {
                    source = project.file('src/proto')
                    outputDir = project.file('build/generated/source/proto')
                    output = 'java_bean'
                }
            }
        }
        project.evaluate()

        assertTrue(project.tasks.getByName(ProtostuffCompilerPlugin.GENERATE_SOURCE_TASK) != null)
        assertTrue(project.tasks.getByName(ProtostuffCompilerPlugin.CLEAN_GENERATE_TASK) != null)

        assertTrue(project.tasks
                .getByName(ProtostuffCompilerPlugin.GENERATE_SOURCE_TASK)
                .outputs
                .files
                .contains(project.file('build/generated/source/proto')))

        assertTrue(project.tasks
                .getByName(ProtostuffCompilerPlugin.GENERATE_SOURCE_TASK)
                .inputs
                .files
                .contains(project.file('src/proto')))
    }
}
