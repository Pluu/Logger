package com.pluu.logger.component

import kotlinx.collections.immutable.toImmutableSet

internal class Component<T : Any> private constructor(
    val name: String?,
    providedInterfaces: Set<Class<T>>,
    dependencies: Set<Dependency>,
    val factory: ComponentFactory<T>
) {
    val providedInterfaces: Set<Class<T>> = providedInterfaces.toImmutableSet()
    val dependencies: Set<Dependency> = dependencies.toImmutableSet()

    fun withFactory(factory: ComponentFactory<T>): Component<T> {
        return Component(
            name, providedInterfaces, dependencies, factory
        )
    }

    /** FirebaseComponent builder. */
    internal class Builder<T : Any> constructor(
        anInterface: Class<T>,
        vararg additionalInterfaces: Class<T>
    ) {
        private var name: String? = null
        private val providedInterfaces = mutableSetOf<Class<T>>()
        private val dependencies = mutableSetOf<Dependency>()
        private var factory: ComponentFactory<T>? = null

        init {
            providedInterfaces.add(anInterface)
            providedInterfaces.addAll(additionalInterfaces)
        }

        /** Set a name for the [Component] being built. */
        fun name(name: String): Builder<T> = apply {
            this.name = name
        }

        fun add(dependency: Dependency): Builder<T> = apply {
            validateInterface(dependency.getInterface())
            dependencies.add(dependency)
        }

        private fun validateInterface(anInterface: Class<*>) {
            check(!providedInterfaces.contains(anInterface)) {
                "Components are not allowed to depend on interfaces they themselves provide."
            }
        }

        /** Set the factory that will be used to initialize the [Component]. */
        fun factory(value: ComponentFactory<T>?): Builder<T> = apply {
            factory = value
        }

        /** Return the built [Component] definition. */
        fun build(): Component<T> {
            return Component(
                name,
                HashSet(providedInterfaces),
                HashSet(dependencies),
                requireNotNull(factory)
            )
        }
    }

    companion object {
        /** Returns a Component<T> builder. */
        fun <T : Any> builder(anInterface: Class<T>): Builder<T> {
            return Builder(anInterface)
        }

        /** Returns a Component<T> builder. */
        fun <T : Any> builder(
            anInterface: Class<T>, vararg additionalInterfaces: Class<T>
        ): Builder<T> {
            return Builder(anInterface, *additionalInterfaces)
        }

        /** Wraps a value in a [Component] with no dependencies. */
        fun <T : Any> of(
            value: T, anInterface: Class<T>, vararg additionalInterfaces: Class<T>
        ): Component<T> {
            return builder(
                anInterface,
                *additionalInterfaces
            ).factory { value }
                .build()
        }
    }
}