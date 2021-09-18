package ru.heatalways.chucknorrisfunfacts.presentation.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import java.lang.ref.WeakReference

@Parcelize
data class TrackedReference<ReferenceType: Any> private constructor(
    private var uniqueID: Int = -1
) : Serializable, Parcelable {
    constructor(reference: ReferenceType) : this() {
        uniqueID = System.identityHashCode(this)
        referenceMap[uniqueID] = reference
    }

    @Suppress("UNCHECKED_CAST")
    val get: ReferenceType?
        get() = referenceMap[uniqueID] as? ReferenceType

    fun removeStrongReference() {
        get?.let { referenceMap[uniqueID] = WeakReference(it) }
    }

    companion object {
        var referenceMap = hashMapOf<Int, Any>()
            private set
    }
}
