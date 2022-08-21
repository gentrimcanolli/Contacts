package com.example.contacts

import android.Manifest.permission.CALL_PHONE
import android.Manifest.permission.READ_CONTACTS
import android.R.attr
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES.M
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.System.out
import java.util.jar.Manifest
import android.R.attr.data


class MainActivity : AppCompatActivity() {

    private lateinit var contactRecyclerView: RecyclerView
    private var contactList = mutableListOf<ContactData>()
    private lateinit var contactListAdapter: ContactListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contactRecyclerView = findViewById(R.id.recycler_view_main)
        contactListAdapter = ContactListAdapter(contactList)


        if (Build.VERSION.SDK_INT >= M
            && checkSelfPermission(READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(READ_CONTACTS), 1)
        } else {
            getContacts()
        }



        contactRecyclerView.layoutManager = LinearLayoutManager(this)
        contactRecyclerView.adapter = contactListAdapter

        Toast.makeText(this, "Click the contact you want to call", Toast.LENGTH_LONG).show()

    }

    @SuppressLint("Range", "Recycle")
    fun getContacts(): MutableList<ContactData> {

        val cr = contentResolver

        val cur =
            cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
        if (cur!!.count > 0) {
            while (cur.moveToNext()) {
                val id =
                    cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NAME_RAW_CONTACT_ID))
                val name =
                    cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phoneNumber =
                    cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                contactList.add(ContactData(id, name, phoneNumber))

            }
        }

        return contactList
    }

}

