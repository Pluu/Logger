package com.pluu.logger.component

internal class ComponentRuntime(
    registrars: List<Provider<ComponentRegistrar>>,
    additionalComponents: List<Component<*>>
) : AbstractComponentContainer() {
    private val components = HashMap<Component<*>, Provider<*>>()
    private val lazyInstanceMap = HashMap<Class<*>, Provider<*>>()

    init {
        val componentsToAdd = ArrayList<Component<*>>()
        componentsToAdd.addAll(additionalComponents)
        for (providerRegistrar in registrars) {
            componentsToAdd.add(processRegister(providerRegistrar.get()))
        }

        for (component in componentsToAdd) {
            components[component] = Lazy {
                component.factory.create(
                    RestrictedComponentContainer(component, this)
                )
            }
        }

        processInstanceComponents(componentsToAdd)
        processDependencies()
    }

    @Suppress("UNCHECKED_CAST")
    private fun processRegister(register: ComponentRegistrar): Component<*> {
        val component = register.getComponent() as Component<Any>
        return if (component.name != null) {
            val newComponent = component.withFactory { container ->
                component.factory.create(container)
            }
            newComponent
        } else {
            component
        }
    }

    private fun processInstanceComponents(componentsToProcess: List<Component<*>>) {
        for (component in componentsToProcess) {
            val provider = components[component]!!
            for (anInterface in component.providedInterfaces) {
                lazyInstanceMap[anInterface] = provider
            }
        }
    }

    private fun processDependencies() {
        for (component in components.keys) {
            for (dependency in component.dependencies) {
                if (!lazyInstanceMap.containsKey(dependency.getInterface())) {
                    throw MissingDependencyException(
                        "Unsatisfied dependency for component %s: %s".format(
                            component, dependency.getInterface()
                        )
                    )
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    @Synchronized
    override fun <T> getProvider(anInterface: Class<T>): Provider<T>? {
        return lazyInstanceMap[anInterface] as? Provider<T>
    }

    class Builder {
        private val lazyRegistrars = mutableListOf<Provider<ComponentRegistrar>>()
        private val additionalComponents = mutableListOf<Component<*>>()

        fun addLazyComponentRegistrars(
            registers: List<Provider<ComponentRegistrar>>
        ): Builder = apply {
            lazyRegistrars.addAll(registers)
        }

        fun addComponent(
            component: Component<*>
        ): Builder = apply {
            additionalComponents.add(component)
        }

        fun build(): ComponentRuntime {
            return ComponentRuntime(
                lazyRegistrars,
                additionalComponents
            )
        }
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }
}