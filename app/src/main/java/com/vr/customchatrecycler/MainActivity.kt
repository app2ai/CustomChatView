package com.vr.customchatrecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    var listAll = mutableListOf<Any>()
    companion object{
        var chatList = mutableListOf(
            Chat("Test box call me, I am not Stupid.","28-08-2020"),
            Chat("Test box call me, I am not Clever.","28-08-2020"),
            Chat("Hallo, how are you?","28-08-2020"),
            Chat("How was mother and Sister","30-09-2020"),
            Chat("All are good, and they are doing well","30-09-2020"),
            Chat("And what about you, How is Master Google?","30-09-2020"),
            Chat("He also fine","01-09-2020"),
            Chat("Thanks", "01-09-2020")
        )
        const val dateView = 0
        const val chatView = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rec.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            layoutDirection = View.LAYOUT_DIRECTION_INHERIT
            /*val ss = mutableSetOf<String>()
            chatList.forEach {date->
                    ss.add(date.dateOfMessage)
            }
            println("Date set is: $ss")*/
            listAll = getAllList()
            println("Date set is: $listAll")
            adapter = ChatAdapter(listAll)
        }
    }

    private fun getAllList(): MutableList<Any> {
        //listAll.addAll(chatList)
        var i = 0
        var _0th = chatList[0]
        listAll.add(_0th.dateOfMessage)
        i++
        listAll.add(_0th)
        while (i< chatList.size){
            /*if (_0th.equals(listAll[i])){

            }else{
                _0th = listAll[i] as Chat
                listAll.add(i, _0th.dateOfMessage)
            }*/
            if (_0th.equals(chatList[i])){
                listAll.add(chatList[i])
            }else{
                _0th = chatList[i]
                listAll.add(_0th.dateOfMessage)
                listAll.add(_0th)
            }
            i++
        }
        return listAll
    }

    fun messageSend(v: View){
        if (!textMessage.text.isNullOrEmpty()){

            val last = listAll.last() as Chat
            val latest = Chat(textMessage.text.trim().toString(), "Today")
            if (last.equals(latest)){
                listAll.add(latest)
            }else{
                listAll.add(latest.dateOfMessage)
                listAll.add(latest)
            }
            textMessage.text.clear()
            rec.adapter?.notifyDataSetChanged()
//            rec.adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
//                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
//                    rec.smoothScrollToPosition(((rec.adapter?.itemCount)?:1-1)?:0)
//                }
//            })
        }
    }

    class ChatAdapter(var list: List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            if (viewType == chatView){
                val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_text_row, parent, false)
                return ChatHolder(v)
            }else{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.date_text_row, parent, false)
                return DateHolder(v)
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewType = getItemViewType(position)
            if (viewType == chatView){
                val chatText = list[position] as Chat
                holder as ChatHolder
                holder.tvChatText.text = chatText.textMsg
                holder.chatTextLayout.layoutDirection = when{
                    position%2 == 0 -> View.LAYOUT_DIRECTION_RTL
                    else -> View.LAYOUT_DIRECTION_LTR
                }
            }else{
                holder as DateHolder
                holder.tvDate.text = list[position] as String
            }
        }

        class ChatHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val tvChatText: TextView
            val chatTextLayout : LinearLayout
            init {
                tvChatText = itemView.findViewById<TextView>(R.id.chat_text)
                chatTextLayout = itemView.findViewById<LinearLayout>(R.id.chat_text_lay)
            }
        }

        class DateHolder(itemView: View):RecyclerView.ViewHolder(itemView){
            val tvDate : TextView
            init {
                tvDate = itemView.findViewById(R.id.date_text)
            }
        }

        override fun getItemViewType(position: Int): Int {
            return when{
                list[position] is Chat -> chatView
                else -> dateView
            }
        }
    }
}
