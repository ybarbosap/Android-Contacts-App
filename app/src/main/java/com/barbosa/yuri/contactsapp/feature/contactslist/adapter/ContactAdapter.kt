package com.barbosa.yuri.contactsapp.feature.contactslist.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barbosa.yuri.contactsapp.R
import com.barbosa.yuri.contactsapp.feature.contactslist.models.ContactVO
import kotlinx.android.synthetic.main.item_contact.view.*

val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

class ContactAdapter(
    val onClick: (Int) -> Unit,
    val data: List<ContactVO>
): RecyclerView.Adapter<ContactAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(contactVO: ContactVO, position: Int){
            with(itemView){
                tvNome.text = contactVO.name
                tvTelefone.text = contactVO.phone
                this.setOnClickListener {
                    onClick(contactVO.id)
                }
                if(position == 0) {
                    this.setPadding(16.dp, 16.dp, 16.dp, 8.dp)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return VH(layout)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int = data.size
}