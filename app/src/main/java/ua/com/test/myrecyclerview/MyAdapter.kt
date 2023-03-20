package ua.com.test.myrecyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ua.com.test.myrecyclerview.databinding.ItemHeaderBinding
import ua.com.test.myrecyclerview.databinding.ItemRecyclerviewBinding

class MyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_USER = 1
    }
    private var headerPositions: List<Int> = emptyList()
    var items: List<Any> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            headerPositions = findHeaderPositions(newValue)
            notifyDataSetChanged()
        }
    var users: List<User> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            items=newValue.plus(headerPositions)
            notifyDataSetChanged()
        }

    class MyViewHolder(val binding: ItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
    class HeaderViewHolder(val binding: ItemHeaderBinding):
            RecyclerView.ViewHolder(binding.root)


//    abstract class KalitsevHolder(view: View): RecyclerView.ViewHolder(view) {
//        abstract fun bind(item: ListItem)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding = ItemHeaderBinding.inflate(inflater, parent, false)
                HeaderViewHolder(binding)
            }
            else -> {
                val binding = ItemRecyclerviewBinding.inflate(inflater, parent, false)
                MyViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is HeaderViewHolder -> {
                val header = item as Header
                with(holder.binding) {
                    tvHeader.text = header.headerTitle
                }
            }
            is MyViewHolder -> {
                val user = item as User
                with(holder.binding) {
                    tvName.text = user.name
                    tvDescription.text = user.description
                    tvAge.text = tvAge.context.getString(R.string.age, user.age)
                    ivFoto.load(user.avatar)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] is Header) {
            VIEW_TYPE_HEADER
        } else {
            VIEW_TYPE_USER
        }
    }

    override fun getItemCount(): Int {
        return users.size+items.size
    }
    private fun findHeaderPositions(items: List<Any>): List<Int> {
        val headerPositions = mutableListOf<Int>()
        var currentPosition = -1
        for ((index, item) in items.withIndex()) {
            if (item is String) {
                currentPosition = index
                headerPositions.add(currentPosition)
            }
        }
        return headerPositions
    }

}