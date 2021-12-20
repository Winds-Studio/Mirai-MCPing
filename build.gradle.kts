plugins {
    val kotlinVersion = "1.5.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.8.3"
}

group = "tax.cute.mcpingplugin"
version = "1.1-Beta"

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}

dependencies {
    implementation("com.alibaba:fastjson:1.2.79")
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // https://mvnrepository.com/artifact/org.yaml/snakeyaml
    implementation("org.yaml:snakeyaml:1.29")


}
