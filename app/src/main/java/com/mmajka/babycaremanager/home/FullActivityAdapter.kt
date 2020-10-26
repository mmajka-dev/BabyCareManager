package com.mmajka.babycaremanager.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.data.BasicActionEntity
import kotlinx.android.synthetic.main.single_activity.view.*
import kotlinx.android.synthetic.main.single_activity_full.view.*

class FullActivityAdapter(val actions: ArrayList<BasicActionEntity>): RecyclerView.Adapter<FullActivityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullActivityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.single_activity_full, parent, false)
        return FullActivityViewHolder(view)
    }

    override fun onBindViewHolder(holder: FullActivityViewHolder, position: Int) {
        val bind = actions.get(position)
        val image = holder.image
        val duration = holder.duration
        val durationTxt = holder.durationTxt
        val card = holder.card
        holder.bind(bind)
        when(actions.get(position).title){
            "Diaper" -> {
                image.setImageResource(R.drawable.ic_diaper_rv)

                card.setCardBackgroundColor(Color.parseColor("#F2C06C"))
            }
            "Feeding" -> {
                image.setImageResource(R.drawable.ic_bottle_rv)
                duration.visibility = View.VISIBLE
                durationTxt.visibility = View.VISIBLE
                card.setCardBackgroundColor(Color.parseColor("#FA8997"))
            }
            "Bath" ->{
                image.setImageResource(R.drawable.ic_bathtub_rv)
                card.setCardBackgroundColor(Color.parseColor("#62BCD4"))
            }
            "Walk" -> {
                image.setImageResource(R.drawable.ic_pram_rv)
                card.setCardBackgroundColor(Color.parseColor("#6DB67F"))
            }
            "Sleep" -> {
                image.setImageResource(R.drawable.ic_sleep_rv)
                card.setCardBackgroundColor(Color.parseColor("#CAA4CC"))
            }
        }
    }

    override fun getItemCount(): Int {
        return actions.size
    }
}

class FullActivityViewHolder(view: View): RecyclerView.ViewHolder(view){
    val card = itemView.card
    val image = itemView.imageView11
    val title = itemView.full_title
    val time = itemView.full_time
    val date = itemView.full_date
    val duration = itemView.duration
    val durationTxt = itemView.duration_txt
    val comment = itemView.full_comment

    fun bind(action: BasicActionEntity){
        title.text = action.title
        time.text = action.time
        date.text = action.date
        comment.text = action.info
        duration.text = action.duration
    }
}