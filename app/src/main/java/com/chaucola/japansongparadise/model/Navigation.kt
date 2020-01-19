package com.chaucola.japansongparadise.model

import android.os.Parcel
import android.os.Parcelable

class Navigation private constructor(private val tabId: Int,
                                     private val categoryId: Int,
                                     private val actionId: Int,
                                     private val isDeepLinking: Boolean) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(tabId)
        parcel.writeInt(categoryId)
        parcel.writeInt(actionId)
        parcel.writeByte(if (isDeepLinking) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Navigation> {
        override fun createFromParcel(parcel: Parcel): Navigation {
            return Navigation(parcel)
        }

        override fun newArray(size: Int): Array<Navigation?> {
            return arrayOfNulls(size)
        }
    }

    class NavigationBuilder(private var isDeepLinking: Boolean = false) {
        private var tabId: Int = 0
        private var categoryId: Int = 0
        private var actionId: Int = 0

        init {
            tabId = -1
            categoryId = -1
            actionId = -1
        }

        fun setTabId(tabId: Int): NavigationBuilder {
            this.tabId = tabId
            return this
        }

        fun setCategoryId(categoryId: Int): NavigationBuilder {
            this.categoryId = categoryId
            return this
        }

        fun setActionId(actionId: Int): NavigationBuilder {
            this.actionId = actionId
            return this
        }

        fun build(): Navigation {
            return Navigation(tabId, categoryId, actionId, isDeepLinking)
        }
    }

    fun getTabId(): Int {
        return tabId
    }

    fun getCategoryId(): Int {
        return categoryId
    }

    fun getActionId(): Int {
        return actionId
    }
}