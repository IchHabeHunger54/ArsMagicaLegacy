import com.github.minecraftschurlimods.helperplugin.api
import com.github.minecraftschurlimods.helperplugin.localGradleProperty
import com.github.minecraftschurlimods.helperplugin.moddependencies.ModDependency
import com.github.minecraftschurlimods.helperplugin.sourceSets
import com.github.minecraftschurlimods.helperplugin.version

plugins {
    idea
    id("net.neoforged.gradle.userdev")
    id("com.github.minecraftschurlimods.helperplugin")
}

helper.withApiSourceSet()
helper.withDataGenSourceSet()
helper.withTestSourceSet()

helper.accessTransformers.add("META-INF/accesstransformer.cfg")
helper.enumExtensions.set("META-INF/enumextensions.json")

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        name = "NeoForge Maven for PR #2993" // https://github.com/neoforged/NeoForge/pull/2993
        url = uri("https://prmaven.neoforged.net/NeoForge/pr2993")
        content {
            includeModule("net.neoforged", "neoforge")
            includeModule("net.neoforged", "testframework")
        }
    }
    maven {
        name = "Geckolib"
        url = uri("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
        content {
            includeGroupAndSubgroups("com.geckolib")
        }
    }
    maven {
        name = "Curios"
        url = uri("https://maven.theillusivec4.top/")
        content {
            includeGroup("top.theillusivec4.curios")
        }
    }
    maven {
        // Jade
        name = "Modrinth"
        url = uri("https://api.modrinth.com/maven")
        content {
            includeGroup("maven.modrinth")
        }
    }
    maven {
        // JEI, Patchouli
        name = "blamejared Maven"
        url = uri("https://maven.blamejared.com/")
        content {
            includeGroup("mezz.jei")
            includeGroup("vazkii.patchouli")
        }
    }
    maven {
        // JEI fallback
        name = "Modmaven"
        url = uri("https://modmaven.dev")
        content {
            includeGroup("mezz.jei")
        }
    }
    maven {
        // EasyDatagenLib
        name = "Minecraftschurli Maven"
        url = uri("https://maven.minecraftschurli.at/maven-public")
        content {
            includeGroup("com.github.minecraftschurlimods")
            includeGroup("at.minecraftschurli")
        }
    }
}

val curios = helper.dependencies.curios()
val jade = helper.dependencies.jade()
val jei = helper.dependencies.jei()
val geckolib = helper.dependencies.required("geckolib") {
    ordering = ModDependency.Ordering.BEFORE
    side = ModDependency.Side.BOTH
}
/* TODO patchouli
val patchouli = helper.dependencies.required("patchouli") {
    ordering = ModDependency.Ordering.BEFORE
    side = ModDependency.Side.BOTH
}
*/

dependencies {
    implementation(helper.neoforge())
    testImplementation(helper.testframework())

    // curios for integration
    val curiosApiDep = curios.version.map { "top.theillusivec4.curios:curios-neoforge:${it}:api" }
    val curiosDep = curios.version.map { "top.theillusivec4.curios:curios-neoforge:${it}" }
    compileOnly(curiosApiDep)
    "dataCompileOnly"(curiosApiDep)
    "dataRuntimeOnly"(curiosDep)

    // jade for integration
    val jadeDep = jade.version.map { "maven.modrinth:jade:${it}-neoforge" }
    compileOnly(jadeDep)

    /* TODO jei
    // jei for integration
    val jeiApiDep = helper.minecraftVersion.zip(jei.version) { mc, version -> "mezz.jei:jei-${mc}-common-api:${version}" }
    val jeiDep = helper.minecraftVersion.zip(jei.version) { mc, version -> "mezz.jei:jei-${mc}-neoforge:${version}" }
    compileOnly(jeiApiDep)
    */

    // geckolib for animations
    val geckolibDep = helper.minecraftVersion.zip(geckolib.version) { mc, version -> "com.geckolib:geckolib-neoforge-${/*mc*/ "26.1-snapshot-11"}:${version}" }
    implementation(geckolibDep)
    testRuntimeOnly(geckolibDep)
    "dataRuntimeOnly"(geckolibDep)
    "interfaceInjection"(geckolibDep)

    /* TODO patchouli
    // patchouli for the guide book (arcane compendium)
    val patchouliApiDep = patchouli.version.map { "vazkii.patchouli:Patchouli:${it}:api" }
    val patchouliDep = patchouli.version.map { "vazkii.patchouli:Patchouli:${it}" }
    compileOnly(patchouliApiDep)
    runtimeOnly(patchouliDep)
    testRuntimeOnly(patchouliDep)
    "dataRuntimeOnly"(patchouliDep)
    */

    if (!helper.runningInCI.getOrElse(false)) {
        runtimeOnly(curiosDep)
        // TODO jade runtimeOnly(jadeDep)
        // TODO jei runtimeOnly(jeiDep)
    }

    val easyDatagenLibVersion = project.localGradleProperty("dependency.easydatagenlib.version")
    val easyDatagenLibApiDep = easyDatagenLibVersion.map { "at.minecraftschurli.mods:easydatagenlib:${it}:api" }
    val easyDatagenLibDep = easyDatagenLibVersion.map { "at.minecraftschurli.mods:easydatagenlib:${it}" }
    "apiCompileOnly"(easyDatagenLibApiDep)
    "dataImplementation"(easyDatagenLibDep)
    "accessTransformer"(easyDatagenLibDep)

    testImplementation("org.junit.jupiter:junit-jupiter:${project.properties["junit_version"]}")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    sourceSets.forEach {
        it.compileOnlyConfigurationName("org.jetbrains:annotations:23.0.0")
        /*it.annotationProcessorConfigurationName("systems.manifold:manifold-preprocessor:2026.1.6")*/
    }
}

helper.withCommonRuns()
helper.withGameTestRuns()
helper.withDataGenRuns()

minecraft.accessTransformers.file("src/main/resources/META-INF/accesstransformer.cfg")

tasks.jar {
    exclude("at/minecraftschurli/arsmagicalegacy/api/data")
}

tasks.withType<JavaCompile>().matching { !it.name.startsWith("neo") }.configureEach {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(arrayOf("-Xlint:-removal", "-Xmaxerrs", "9999"))
    //options.compilerArgs.add("-Xplugin:Manifold")
}

tasks.withType<JavaCompile>().matching { it.name == "neoFormRecompile" }.configureEach {
    options.compilerArgs.add("-Xlint:-removal")
}

tasks.javadoc {
    classpath = sourceSets.api.get().compileClasspath
    source = sourceSets.api.get().allJava
}

helper.publication.pom {
    organization {
        name = "Minecraftschurli Mods"
        url = "https://github.com/MinecraftschurliMods"
    }
    developers {
        developer {
            id = "minecraftschurli"
            name = "Minecraftschurli"
            email = "minecraftschurli@gmail.com"
            url = "https://github.com/Minecraftschurli"
            organization = "Minecraftschurli Mods"
            organizationUrl = "https://github.com/MinecraftschurliMods"
            timezone = "Europe/Vienna"
        }
        developer {
            id = "ichhabehunger54"
            name = "IchHabeHunger54"
            url = "https://github.com/IchHabeHunger54"
            organization = "Minecraftschurli Mods"
            organizationUrl = "https://github.com/MinecraftschurliMods"
            timezone = "Europe/Vienna"
        }
    }
}
