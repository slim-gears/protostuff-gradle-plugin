protostuff-gradle-plugin
========================

Protostuff Plugin for Gradle

Usage
=====

Add to your build.properties

```gradle
apply plugin: 'io.protostuff.compiler'

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }
    dependencies {
        classpath 'io.protostuff:protostuff-gradle-plugin:1.0.1-SNAPSHOT'
    }
}

```

Then you can configure your proto modules

```gradle
protostuff {
    modules {
        foo {
            source = file('src/main/resources/foo.proto')
            outputDir = file('src/main/java')
            output = 'java_bean'
        }
    }
}
```