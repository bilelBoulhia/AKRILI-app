package com.example.akrili.Data



import android.os.Parcel
import android.os.Parcelable

data class imageslist(val image0: String, val image1: String, val image2: String)
    // : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readString() ?: "",
//        parcel.readString() ?: "",
//        parcel.readString() ?: ""
//    )
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(image0)
//        parcel.writeString(image1)
//        parcel.writeString(image2)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<imageslist> {
//        override fun createFromParcel(parcel: Parcel): imageslist {
//            return imageslist(parcel)
//        }
//
//        override fun newArray(size: Int): Array<imageslist?> {
//            return arrayOfNulls(size)
//        }
//    }
//}


