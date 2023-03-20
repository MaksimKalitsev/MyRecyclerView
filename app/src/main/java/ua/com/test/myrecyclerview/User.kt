package ua.com.test.myrecyclerview

import androidx.annotation.DrawableRes

interface ListItem
data class User(val name:String, val description:String, val age:Int, @DrawableRes val avatar:Int):ListItem
data class Header(val headerTitle: String):ListItem



