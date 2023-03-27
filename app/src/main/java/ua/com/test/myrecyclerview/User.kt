package ua.com.test.myrecyclerview

import androidx.annotation.DrawableRes

interface ListItem {
    fun areItemTheSame(that: ListItem): Boolean
    fun areContentTheSame(that: ListItem): Boolean
}
data class User(
    val name: String,
    val description: String,
    val age: Int,
    @DrawableRes val avatar: Int
) : ListItem {
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

data class Header(val headerTitle: String) : ListItem {
    override fun areItemTheSame(that: ListItem): Boolean {
        return that is Header && this.headerTitle == that.headerTitle
    }

    override fun areContentTheSame(that: ListItem): Boolean {
        return that is Header && this.headerTitle == that.headerTitle
    }
}



