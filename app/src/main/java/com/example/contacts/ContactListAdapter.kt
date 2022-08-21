package com.example.contacts

import android.Manifest.permission.CALL_PHONE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES.M
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.startActivity
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class ContactListAdapter(private var contactList: MutableList<ContactData>) :
    RecyclerView.Adapter<ContactListAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var idTv: TextView
        var nameTv: TextView
        var phoneNumberTv: TextView

        init {
            idTv = view.findViewById(R.id.id_textview)
            nameTv = view.findViewById(R.id.name_textview)
            phoneNumberTv = view.findViewById(R.id.phoneNumber_textview)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.contacts_list_items, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.idTv.text = contactList[position].id
        holder.nameTv.text = contactList[position].name
        holder.phoneNumberTv.text = contactList[position].phoneNumber

        holder.itemView.setOnClickListener {
            val num = contactList[position]

            val gNumber: String = num.phoneNumber
            val uriString = "tel: $gNumber"
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse(uriString))
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = contactList.size


}
