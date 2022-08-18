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
    private var contactList = ArrayList<ContactData>()
    private lateinit var contactData: ContactData
    private lateinit var contactListAdapter: ContactListAdapter

    private lateinit var phoneNumberTvMainActivity: TextView
    private lateinit var phoneNumberTv: TextView
    private lateinit var linearlActivity: LinearLayout
    private lateinit var btnCall: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearlActivity = findViewById(R.id.linear_layout_main)
        phoneNumberTvMainActivity = findViewById(R.id.phoneNumber_textview_main)
//        btnCall = findViewById(R.id.btn_call)
//
////        var intent = intent
////        var gNum = intent.getStringExtra("phoneNumber")
//
////        btnCall.setOnClickListener {
////            if (gNum != null) {
////                makePhoneCall(gNum)
////            }
////        }

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

        Toast.makeText(this, "Click the contact you want to call",Toast.LENGTH_LONG).show()

    }

    @SuppressLint("Range", "Recycle")
    fun getContacts() {

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
                contactList.sortWith { lhs, rhs ->

                    if (lhs.id < rhs.id) -1 else if (lhs.name < rhs.name) 1 else 0
                }


            }
        }
    }

    private fun makePhoneCall(phoneNumber : String) {

        if (Build.VERSION.SDK_INT >= M
            && checkSelfPermission(CALL_PHONE) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(CALL_PHONE), 1)
        } else {
            val num = "tel: $phoneNumber"
            intent = Intent(Intent.ACTION_DIAL, Uri.parse(num))
            startActivity(intent)

        }
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if (requestCode == 1) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getContacts()
////                makePhoneCall(phoneNumberTv.text.toString())
//            }
//        }
//    }
}