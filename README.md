protostuff-gradle-plugin
========================

Forked from: https://github.com/ramonwirsch/protostuff-gradle-plugin

Protostuff Plugin for Gradle

Usage
=====

Add to your build.properties

```groovy
buildscript {
    repositories {
        mavenCentral()    
    }
    dependencies {
        classpath 'com.slimgears.protostuff:protostuff-gradle-plugin:1.1.2'
    }
}

apply plugin: 'com.slimgears.protostuff'
```

Then you can configure your proto modules

```groovy
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
