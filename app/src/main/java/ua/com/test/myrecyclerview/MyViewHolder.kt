package ua.com.test.myrecyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ua.com.test.myrecyclerview.databinding.ItemRecyclerviewBinding

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemRecyclerviewBinding.bind(itemView)

    fun bind(name: Name, desc: Description) = with(binding) {
        ivFoto.setImageResource(name.imageId)
        tvName.text = name.name
        tvDescription.text = desc.desc
    }
}