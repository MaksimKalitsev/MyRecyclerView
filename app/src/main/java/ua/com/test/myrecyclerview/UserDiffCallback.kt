package ua.com.test.myrecyclerview

import androidx.recyclerview.widget.DiffUtil

class UserDiffCallback(
    private val oldList:List<ListItem>, private val newList:List<ListItem>):DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser.areItemTheSame(newUser)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser.areContentTheSame(newUser)
    }

}