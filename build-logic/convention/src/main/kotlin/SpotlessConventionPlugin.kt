import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class SpotlessConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.diffplug.spotless")

            extensions.configure<SpotlessExtension> {
                kotlin {
                    target("**/*.kt")
                    targetExclude(
                        "**/build/**/*.kt",
                        "**/test/**/*.kt",
                        "**/androidTest/**/*.kt"
                    )
                    ktlint()
                        .userData(mapOf("android" to "true"))
                    licenseHeaderFile(rootProject.file("$rootDir/spotless/copyright.kt"))
                }
            }
        }
    }
}
