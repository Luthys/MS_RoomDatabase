package dev.hugozammit.roomdatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FriendListAdapter( friendList: List<Friend> ) :
    RecyclerView.Adapter<FriendListAdapter.ViewHolder>() {

    private var dataset: MutableList<Friend> = friendList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.adapter_friend_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = dataset[position]
        holder.name.setText( friend.firstName )
        holder.rating.setText( friend.rating.toString() )

        holder.bRemove.setOnClickListener { eventListener?.onFriendDelete(dataset[position]) }

        holder.bSave.setOnClickListener {
            val editFriend = Friend(
                uid = dataset[position].uid,
                firstName = holder.name.text.toString(),
                rating = holder.rating.text.toString().toInt()
            )
            eventListener?.onFriendSave(editFriend)
        }
    }

    fun updateData( friendList: List<Friend> ) {
        dataset.clear()
        dataset.addAll(friendList)
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataset.size

    private var eventListener: EventListener? = null
    fun setEventListener(eventListener: EventListener) {
        this.eventListener = eventListener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<EditText>(R.id.name)!!
        val rating = view.findViewById<EditText>(R.id.rating)!!
        val bRemove = view.findViewById<Button>(R.id.bRemove)!!
        val bSave = view.findViewById<Button>(R.id.bSave)!!
    }

    interface EventListener {
        fun onFriendSave(friend: Friend)
        fun onFriendDelete(friend: Friend)
    }
}