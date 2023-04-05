package ua.com.test.myrecyclerview

import androidx.annotation.DrawableRes

interface ListItem {
    fun areItemTheSame(that: ListItem): Boolean
    fun areContentTheSame(that: ListItem): Boolean
}

interface Ageable {
    val age: Int
}

interface PhysicalObject : ListItem, Ageable {
    var isSelected: Boolean
}

data class User(
    val name: String,
    var description: String,
    override val age: Int,
    @DrawableRes val avatar: Int
) : PhysicalObject {

    override var isSelected: Boolean = false
    override fun areItemTheSame(that: ListItem): Boolean {
        return that is User && this.name == that.name
    }

    override fun areContentTheSame(that: ListItem): Boolean {
        return that is User &&
                this.name == that.name &&
                this.description == that.description &&
                this.age == that.age &&
                this.avatar == that.avatar
    }
}

data class HeaderUser(val headerTitle: String) : ListItem {
    override fun areItemTheSame(that: ListItem): Boolean {
        return that is HeaderUser && this.headerTitle == that.headerTitle
    }

    override fun areContentTheSame(that: ListItem): Boolean {
        return that is HeaderUser && this.headerTitle == that.headerTitle
    }
}



