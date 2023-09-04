/*
 * SPDX-FileCopyrightText: 2023 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.glimpse.models

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import org.lineageos.glimpse.ext.readParcelable
import org.lineageos.glimpse.ext.readSerializable
import kotlin.reflect.safeCast

data class MediaUri(
    val externalContentUri: Uri,
    val mediaType: MediaType,
    val mimeType: String,
) : Comparable<MediaUri>, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Uri::class)!!,
        parcel.readSerializable(MediaType::class)!!,
        parcel.readString()!!
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeParcelable(externalContentUri, flags)
        dest.writeSerializable(mediaType)
        dest.writeString(mimeType)
    }

    override fun describeContents() = 0

    override fun hashCode(): Int {
        var result = externalContentUri.hashCode()
        result = 31 * result + mediaType.hashCode()
        result = 31 * result + mimeType.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        val obj = MediaUri::class.safeCast(other) ?: return false
        return compareTo(obj) == 0
    }

    override fun compareTo(other: MediaUri) = compareValuesBy(
        this, other,
        { it.externalContentUri },
        { it.mediaType },
        { it.mimeType },
    )

    companion object CREATOR : Creator<MediaUri> {
        override fun createFromParcel(parcel: Parcel) = MediaUri(parcel)

        override fun newArray(size: Int) = arrayOfNulls<MediaUri>(size)
    }
}