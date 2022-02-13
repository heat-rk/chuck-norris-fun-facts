package ru.heatalways.chucknorrisfunfacts.core.di

import dagger.Component
import ru.heatalways.chucknorrisfunfacts.core.di.modules.*
import javax.inject.Singleton

@Component(
    dependencies = [CoreDependencies::class],
    modules = [
        DatabaseModule::class,
        DispatcherModule::class,
        GsonModule::class,
        HttpClientModule::class,
        RetrofitModule::class
    ]
)
@Singleton
internal interface CoreComponent: CoreApi {
    companion object {
        fun initAndGet(dependencies: CoreDependencies): CoreComponent =
            DaggerCoreComponent.builder()
                .coreDependencies(dependencies)
                .build()
    }
}