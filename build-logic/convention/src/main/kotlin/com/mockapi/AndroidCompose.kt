package com.mockapi

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(
  commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
  pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

  commonExtension.apply {
    buildFeatures {
      compose = true
    }
  }

  extensions.configure<ComposeCompilerGradlePluginExtension> {
    featureFlags.add(ComposeFeatureFlag.StrongSkipping)
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
  }
}
