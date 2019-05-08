package th.ac.buu.se.myfirebasedemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import java.lang.reflect.Type
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_chat.*




class ChatActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    var mAuthListener: FirebaseAuth.AuthStateListener? = null;
    var mDatabase: FirebaseDatabase? = FirebaseDatabase.getInstance()
    var mFirebaseAdapter:FirebaseRecyclerAdapter<ChatData, MessageViewHolder>? =  null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Chat Activity"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        var btn_send = findViewById<Button>(R.id.btn_send)
        var btn_logout = findViewById<Button>(R.id.btn_logout)

        //connect database
        var mReference = mDatabase!!.getReference()
        //push data
        btn_send.setOnClickListener {
            var input_text = findViewById<EditText>(R.id.input_text)
            var messageText = input_text.text.toString()
            if(messageText != "") {
                mReference.child("chat").push().setValue(
                    ChatData(messageText,mAuth!!.currentUser!!.email.toString())
                    //ChatData(messageText,mAuth!!.currentUser!!.email.toString())
                )
            }else {
                Toast.makeText(this,"No Message ?",Toast.LENGTH_LONG)
            }
            input_text.setText("")
        }
        //recycle view
        var list_chat = findViewById<RecyclerView>(R.id.list_chat)
        list_chat.layoutManager = LinearLayoutManager(this)

        var query: Query = mReference.child("chat")
        var option = FirebaseRecyclerOptions.Builder<ChatData>()
            .setQuery(query, ChatData::class.java!!)
            .build()

        mFirebaseAdapter = object: FirebaseRecyclerAdapter<ChatData, MessageViewHolder>(option){
            protected override fun onBindViewHolder(
                viewHolder: MessageViewHolder,
                position: Int,
                chatData: ChatData) {
                Log.d("FIREBASE", chatData.messageText)
                if(chatData.messageUser.equals(mAuth!!.currentUser!!.email.toString())) {
                    viewHolder.row.setGravity(Gravity.END)
                }else{
                    viewHolder.row.setGravity(Gravity.START)
                }
                viewHolder.messageTextView.setText(chatData.messageText)
                viewHolder.messengerTextView.setText(chatData.messageUser)
            }

            override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MessageViewHolder {
                val inflater = LayoutInflater.from(viewGroup.context)
                return MessageViewHolder(inflater.inflate(R.layout.item_message, viewGroup, false))
            }
        }

        list_chat.adapter = mFirebaseAdapter

        //logout
        btn_logout.setOnClickListener {
            mAuth!!.signOut()
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                //Log.d("FIREBASENAJA",user.uid)
                Log.d("FIREBASENAJA",mAuth!!.currentUser!!.email.toString())
            } else {
                finish()
            }
        }
        mAuth!!.addAuthStateListener(mAuthListener!!)
        if (mFirebaseAdapter != null) {
            mFirebaseAdapter!!.startListening()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mFirebaseAdapter != null) {
            mFirebaseAdapter!!.stopListening()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}

class MessageViewHolder internal constructor(v: View) : RecyclerView.ViewHolder(v) {
    internal var row: LinearLayout
    internal var messageTextView: TextView
    internal var messengerTextView: TextView

    init {
        row = itemView.findViewById(R.id.row)
        messageTextView = itemView.findViewById(R.id.show_message)
        messengerTextView = itemView.findViewById(R.id.show_name)
    }
}

