package ru.heatalways.chucknorrisfunfacts.di.modules

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.heatalways.chucknorrisfunfacts.data.utils.Exclude
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object GsonModule {
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .addSerializationExclusionStrategy(JsonExclusionStrategy())
        .addDeserializationExclusionStrategy(JsonExclusionStrategy())
        .serializeNulls()
        .enableComplexMapKeySerialization()
        .create()

    class JsonExclusionStrategy: ExclusionStrategy {
        override fun shouldSkipField(f: FieldAttributes?): Boolean {
            return f?.getAnnotation(Exclude::class.java) != null
        }

        override fun shouldSkipClass(clazz: Class<*>?): Boolean {
            return false
        }
    }
}