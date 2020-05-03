package com.example.hexeng.activity


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hexeng.R
import com.example.hexeng.adapter.CategoryAdapter
import com.example.hexeng.model.Category
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.category_activity.*


class CategoryActivity : AppCompatActivity() {

    private var mAdapter = CategoryAdapter()
    private var firestoreDB : FirebaseFirestore? = null
    private var firestoreListener: ListenerRegistration? = null
    private var listCategory = ArrayList<Category>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_activity)

        item_list.layoutManager = LinearLayoutManager(this)
        item_list.adapter = mAdapter

//      Retrieve an instance of Firebase DB
        firestoreDB = FirebaseFirestore.getInstance()

//      Add the categories retrieve from Firebase DB snapshot and render new view
        firestoreListener = firestoreDB!!.collection("Category")
                            .addSnapshotListener(EventListener{snapshots, e ->
                                if(e != null) {
                                    Log.w("thisis", "error",e)
                                }

//                              Create recycle view of Categories
                                if (snapshots != null) {
                                    for(cat in snapshots.documents) {
                                        val categoryInfo = cat.data
                                        val id = cat.id
                                        if (categoryInfo != null) {
                                            this.addCategory(id, categoryInfo)
                                        }
                                    }
//                                  Update recycle view
                                    mAdapter.update(listCategory)
                                }
                            })
    }

    override fun onDestroy() {
        super.onDestroy()

        firestoreListener!!.remove()
    }

    private fun addCategory(id:String,categoryInfo:MutableMap<String,Any>) {
//      Add category object retrieved from Firebase DB to a list of categories.
        listCategory.add(
            Category(
                id,
                categoryInfo["Name"] as String
            )
        )
    }
}