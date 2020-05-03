package com.example.hexeng.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hexeng.R
import com.example.hexeng.activity.DefinitionActivity
import com.example.hexeng.model.Vocab
import kotlinx.android.synthetic.main.recycleview_row.view.*

class VocabAdapter(): RecyclerView.Adapter<VocabAdapter.ViewHolder>() {

    private var vocabList: List<Vocab> = emptyList()
    private var catID = ""

    fun update(categoryList: List<Vocab>) {
        this.vocabList = categoryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//      Return rendered view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_row,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//      Set up on-clicked attribute
        holder?.itemView?.setOnClickListener { v ->
            val extras = Bundle()
//          Assign the vocabularies to the designatied position
            val word:String = this.vocabList[position].word.toString()
            val meaning:String = this.vocabList[position].meaning.toString()
            extras.putString("word",word)
            extras.putString("meaning",meaning)

//          Start DefinitionActivity, and pass the word and meaning to the next activity
            v.context.startActivity(Intent(v.context,DefinitionActivity::class.java).putExtras(extras))
        }

//      Configure the content position
        val vocab = vocabList[position]
        if (holder != null) {
            holder.itemView.item.text = vocab.word
        }
    }

    override fun getItemCount(): Int {
        return vocabList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
    }
}

