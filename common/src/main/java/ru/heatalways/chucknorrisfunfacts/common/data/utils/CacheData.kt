package ru.heatalways.chucknorrisfunfacts.common.data.utils


/**
 * Utility class for caching data. Use this class in repositories to prevent
 * a lot of requests to server or in other use-cases
 */
class CacheData<T>(
    /**
     * The life time of cache
     */
    private val cacheLifeTime: Long = CACHE_LIFE_TIME
) {

    /**
     * Cached data
     */
    private var value: T? = null

    /**
     * The last cache write time
     */
    private var lastUpdateTime = 0L

    /**
     * Returns true, if the cache has been expired.
     */
    val isExpired get() =
        System.currentTimeMillis() - lastUpdateTime >= cacheLifeTime || value == null

    /**
     * Returns true, if the cache has not been expired.
     */
    val isNotExpired get() = !isExpired

    /**
     * Returns the cached value
     */
    fun getValue(): T? {
        if (isExpired) this.value = null
        return this.value
    }

    /**
     * Save value to cache
     */
    fun setValue(value: T) {
        lastUpdateTime = System.currentTimeMillis()
        this.value = value
    }

    fun clear() {
        this.value = null
    }

    companion object {
        private const val CACHE_LIFE_TIME = 15000L
    }
}