package com.github.operador231.core.ui.annotations

@RequiresOptIn(
    message = "This UI component is part of the experimental AniFlux design system. " +
            "It may change visually or structurally.",
    level = RequiresOptIn.Level.WARNING
)
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.CLASS
)
public annotation class ExperimentalAniFluxUi
