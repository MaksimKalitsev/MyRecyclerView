package ua.com.test.myrecyclerview

import androidx.annotation.DrawableRes

data class Car(
    val model: String,
    override val age: Int,
    val color: String,
    @DrawableRes val image: Int
) : PhysicalObject {
    override fun areItemTheSame(that: ListItem): Boolean {
        return that is Car && this.age == that.age
    }

    override fun areContentTheSame(that: ListItem): Boolean {
        return that is Car &&
                this.model == that.model &&
                this.age == that.age &&
                this.color == that.color &&
                this.image == that.image
    }
}


