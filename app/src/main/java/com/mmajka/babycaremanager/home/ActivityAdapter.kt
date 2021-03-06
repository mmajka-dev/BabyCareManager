package com.mmajka.babycaremanager.home


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.data.BasicActionEntity
import kotlinx.android.synthetic.main.single_activity.view.*

class ActivityAdapter(val actions: ArrayList<BasicActionEntity>): RecyclerView.Adapter<ActivityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.single_activity, parent, false)
        return ActivityViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val bind = actions.get(position)
        val image = holder.image
        holder.bind(bind)

        when(actions.get(position).type){
            "diaper" -> {
                image.setImageResource(R.drawable.ic_diaper_rv)
            }
            "feeding" -> {
                image.setImageResource(R.drawable.ic_bottle_rv)
            }
            "bath" ->{
                image.setImageResource(R.drawable.ic_bathtub_rv)
            }
            "walk" -> {
                image.setImageResource(R.drawable.ic_pram_rv)
            }
            "sleep" -> {
                image.setImageResource(R.drawable.ic_sleep_rv)
            }
        }
    }

    override fun getItemCount(): Int {
        return actions.size
    }
}

class ActivityViewHolder(view: View): RecyclerView.ViewHolder(view){
    val image = itemView.imageView9
    val title = itemView.title
    val time = itemView.time
    val date = itemView.date

    fun bind(action: BasicActionEntity){
        title.text = action.title
        time.text = action.time
        date.text = action.date
    }
}

