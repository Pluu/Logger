package com.pluu.logger.component

import kotlinx.collections.immutable.toImmutableSet

internal class RestrictedComponentContainer(
    component: Component<*>,
    private val delegateContainer: ComponentContainer
) : AbstractComponentContainer() {

    private val allowedDirectInterfaces: Set<Class<*>>

    init {
        val directInterfaces = HashSet<Class<*>>()

        for (dependency in component.dependencies) {
            directInterfaces.add(dependency.getInterface())
        }
        allowedDirectInterfaces = directInterfaces.toImmutableSet()
    }

    override fun <T> get(anInterface: Class<T>): T? {
        if (!allowedDirectInterfaces.contains(anInterface)) {
            throw DependencyException(
                "Attempting to request an undeclared dependency %s.".format(anInterface)
            )
        }
        return delegateContainer.get(anInterface)
    }

    override fun <T> getProvider(anInterface: Class<T>): Provider<T>? {
        return delegateContainer.getProvider(anInterface)
    }
}