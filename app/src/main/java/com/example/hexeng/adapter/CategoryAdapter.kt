package com.example.hexeng.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hexeng.R
import com.example.hexeng.activity.VocabListActivity
import com.example.hexeng.model.Category
import kotlinx.android.synthetic.main.recycleview_row.view.*


class CategoryAdapter(): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var categoryList: List<Category> = emptyList()

    fun update(categoryList: List<Category>) {
        this.categoryList = categoryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//      Return rendered view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_row,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//      Set up the content in the placeholder buttons to be the vocabulary catogories
        holder?.itemView?.setOnClickListener { v ->
            val extras = Bundle()
//          Assign the content on the designatied position
            val catID:String = this.categoryList[position].id.toString()
            val catName:String = this.categoryList[position].name.toString()
            extras.putString("catID",catID)
            extras.putString("catName",catName)
//          Set up mapping to start VocabListActivity, pass category ID, name to the next activity.
            v.context.startActivity(Intent(v.context,VocabListActivity::class.java).putExtras(extras))
        }

//      Configure the position of each category
        val category = categoryList[position]
        if (holder != null) {
            holder.itemView.item.text = category.name
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
    }

}


