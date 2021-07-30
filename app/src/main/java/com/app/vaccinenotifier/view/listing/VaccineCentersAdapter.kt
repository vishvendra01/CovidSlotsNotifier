package com.app.vaccinenotifier.view.listing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.vaccinenotifier.R
import com.app.vaccinenotifier.data.model.VaccineCenter

class VaccineCentersAdapter : RecyclerView.Adapter<VaccineCenterHolder>() {
    var items: MutableList<VaccineCenter> = mutableListOf()
        set(value) {
            items.clear()
            items.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaccineCenterHolder {
        return VaccineCenterHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_vaccine_center, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VaccineCenterHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = item.name
        holder.addressTextView.text = item.address
        if (item.sessions.isNotEmpty()) {
            item.sessions[0].run {
                holder.doseOneTextView.text = "Dose 1 : ${this.availableCapacityDose1}"
                holder.doseTwoTextView.text = "Dose 2 : ${this.availableCapacityDose2}"
                holder.vaccineNameTextView.text = this.vaccine
                if (this.allowAllAge) {
                    holder.ageTextView.setText("18 & Above")
                } else {
                    if(this.minAgeLimit == 18 && this.maxAgeLimit == 44) {
                        holder.ageTextView.setText("18-44 Only")
                    }else if(this.minAgeLimit == 45){
                        holder.ageTextView.setText("45 & Above")
                    }
                }
            }
        }
        holder.feeTypeTextView.text = item.feeType
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class VaccineCenterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
    val addressTextView = itemView.findViewById<TextView>(R.id.addressTextView)
    val doseOneTextView = itemView.findViewById<TextView>(R.id.doseOneTextView)
    val doseTwoTextView = itemView.findViewById<TextView>(R.id.doseTwoTextView)
    val vaccineNameTextView = itemView.findViewById<TextView>(R.id.vaccineNameTextView)
    val feeTypeTextView = itemView.findViewById<TextView>(R.id.feeTypeTextView)
    val ageTextView = itemView.findViewById<TextView>(R.id.ageTextView)
}