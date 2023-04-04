package ua.com.test.myrecyclerview


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ua.com.test.myrecyclerview.databinding.ItemCarsBinding
import ua.com.test.myrecyclerview.databinding.ItemHeaderBinding
import ua.com.test.myrecyclerview.databinding.ItemRecyclerviewBinding

class MyAdapter : RecyclerView.Adapter<MyAdapter.GeneralHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER_USER = 0
        private const val VIEW_TYPE_USER = 1
        private const val VIEW_TYPE_CAR = 2
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
        override fun onItemClicked(item: ListItem) {
            val user = item as User
            binding.tvDescription.text = user.description.uppercase()
            binding.tvDescription.setTextColor(Color.RED)
        }
    }

    class HeaderUserViewHolder(private val binding: ItemHeaderBinding) :
        GeneralHolder(binding.root) {

        override fun bind(item: ListItem) {
            val header = item as HeaderUser
            with(binding) {
                tvHeader.text = header.headerTitle
            }
        }
    }

    class CarViewHolder(private val binding: ItemCarsBinding) : GeneralHolder(binding.root) {

        override fun bind(item: ListItem) {
            val car = item as Car
            with(binding) {
                tvBrandModel.text = car.model
                tvCarMileage.text = tvCarMileage.context.getString(R.string.car_mileage, car.age)
                tvColorCar.text = tvColorCar.context.getString(R.string.car_color, car.color)
                ivCars.load(car.image)
            }
        }
        override fun onItemClicked(item: ListItem) {
            val car = item as Car
            binding.tvColorCar.text = car.color.uppercase()
            binding.tvColorCar.setTextColor(Color.GREEN)
        }
    }

    abstract class GeneralHolder(view: View) : RecyclerView.ViewHolder(view) {

        abstract fun bind(item: ListItem)
        open fun onItemClicked(item: ListItem) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER_USER -> {
                val binding = ItemHeaderBinding.inflate(inflater, parent, false)
                HeaderUserViewHolder(binding)
            }
            VIEW_TYPE_USER -> {
                val binding = ItemRecyclerviewBinding.inflate(inflater, parent, false)
                MyViewHolder(binding)
            }
            else -> {
                val binding = ItemCarsBinding.inflate(inflater, parent, false)
                CarViewHolder(binding)
            }
        }
    }


    override fun onBindViewHolder(holder: GeneralHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        holder.itemView.setOnClickListener(View.OnClickListener {
            holder.onItemClicked(item)
        })
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is HeaderUser -> VIEW_TYPE_HEADER_USER
            is User -> VIEW_TYPE_USER
            else -> VIEW_TYPE_CAR
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}