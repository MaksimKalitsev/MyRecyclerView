package ua.com.test.myrecyclerview


import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ua.com.test.myrecyclerview.databinding.ItemCarsBinding
import ua.com.test.myrecyclerview.databinding.ItemHeaderBinding
import ua.com.test.myrecyclerview.databinding.ItemRecyclerviewBinding

class MyAdapter(private val callback: Callback) : RecyclerView.Adapter<MyAdapter.GeneralHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER_USER = 0
        private const val VIEW_TYPE_USER = 1
        private const val VIEW_TYPE_CAR = 2
    }

    private val _itemClickedLiveData = MutableLiveData("")
    val itemClickedLiveData: LiveData<String> = _itemClickedLiveData


    interface Callback {
        fun showToastInActivity(position: Int, itemType: String)
    }

    private val holderCallback = object : GeneralHolder.Callback {
        override fun doSomething() {

        }

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
                val color = if (user.isSelected) Color.GREEN else Color.BLACK
                tvDescription.setTextColor(color)
            }
        }

        override fun onItemClicked(item: ListItem): Boolean {
            val user = item as User
            user.isSelected = !user.isSelected
            return true
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
                val color = if (car.isSelected) Color.GREEN else Color.BLACK
                tvColorCar.setTextColor(color)
            }
        }

        override fun onItemClicked(item: ListItem): Boolean {
            val car = item as Car
            car.isSelected = !car.isSelected
            return true
        }
    }

    abstract class GeneralHolder(view: View) : RecyclerView.ViewHolder(view) {

        interface Callback {
            fun doSomething()
        }

        abstract fun bind(item: ListItem)

        /**
         * @return true if notify item changed required
         */
        open fun onItemClicked(item: ListItem): Boolean = false
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

        holder.itemView.setOnClickListener {
//            callback.showToastInActivity(holder.adapterPosition, item.javaClass.simpleName)
            _itemClickedLiveData.value = "${holder.adapterPosition} - ${item.javaClass.simpleName}"
            val hasToBeNotified = holder.onItemClicked(item)
            if (hasToBeNotified) {
                val itemClickedPosition = holder.adapterPosition
                notifyItemChanged(itemClickedPosition)
            }

        }
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