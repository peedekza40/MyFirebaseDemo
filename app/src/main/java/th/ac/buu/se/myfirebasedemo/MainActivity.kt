package th.ac.buu.se.myfirebasedemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn_auth = findViewById<Button>(R.id.btn_auth)
        var btn_no_auth = findViewById<Button>(R.id.btn_no_auth)

        btn_auth.setOnClickListener {
            var intent = Intent(this.applicationContext, AuthActivity::class.java)
            startActivity(intent)
        }

        btn_no_auth.setOnClickListener {
            var intent = Intent(this.applicationContext, ChatActivity::class.java)
            startActivity(intent)
        }

    }

}
