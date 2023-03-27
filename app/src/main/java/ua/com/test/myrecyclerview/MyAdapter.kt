package ua.com.test.myrecyclerview


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ua.com.test.myrecyclerview.databinding.ItemHeaderBinding
import ua.com.test.myrecyclerview.databinding.ItemRecyclerviewBinding

class MyAdapter : RecyclerView.Adapter<MyAdapter.GeneralHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_USER = 1
    }

    var items: List<ListItem> = emptyList()

        set(newValue) {
            val diffCallback = UserDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    class MyViewHolder(private val binding: ItemRecyclerviewBinding) :
        GeneralHolder(binding.root) {

        override fun bind(item: ListItem) {
            val user = item as User
            with(binding) {
                tvName.text = user.name
                tvDescription.text = user.description
                tvAge.text = tvAge.context.getString(R.string.age, user.age)
                ivFoto.load(user.avatar)
            }
        }
    }

    class HeaderViewHolder(private val binding: ItemHeaderBinding) : GeneralHolder(binding.root) {

        override fun bind(item: ListItem) {
            val header = item as Header
            with(binding) {
                tvHeader.text = header.headerTitle
            }
        }
    }

    abstract class GeneralHolder(view: View) : RecyclerView.ViewHolder(view) {

        abstract fun bind(item: ListItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralHolder {
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

    override fun onBindViewHolder(holder: GeneralHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] is Header) {
            VIEW_TYPE_HEADER
        } else {
            VIEW_TYPE_USER
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}