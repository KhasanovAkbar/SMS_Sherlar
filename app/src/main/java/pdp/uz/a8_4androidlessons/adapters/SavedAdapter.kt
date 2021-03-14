package pdp.uz.a8_4androidlessons.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_view.view.*
import pdp.uz.a8_4androidlessons.R
import pdp.uz.a8_4androidlessons.models.Info

class SavedAdapter(
    var listInfo: ArrayList<Info>?,
    var onMyItemClickListener: SavedAdapter.OnMyItemClickListener
) : RecyclerView.Adapter<SavedAdapter.Vh>() {

    inner class Vh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(data: Info, position: Int) {
            itemView.poem_title.text = data.title
            itemView.poem_info.text = data.poem
            itemView.image_saved.visibility = View.VISIBLE

            itemView.setOnClickListener {
                onMyItemClickListener.onMyItemClick(data, position)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(listInfo!![position], position)
    }

    override fun getItemCount(): Int = listInfo!!.size

    interface OnMyItemClickListener {
        fun onMyItemClick(data: Info, position: Int)
    }
}