package th.ac.buu.se.myfirebasedemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    var mAuthListener: FirebaseAuth.AuthStateListener? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Picasso.get().load("https://firebase.google.com/images/brand-guidelines/logo-standard.png")
            .placeholder(R.mipmap.ic_launcher)
            .into(imageView)

        var btn_auth = findViewById<Button>(R.id.btn_auth)
        var btn_no_auth = findViewById<Button>(R.id.btn_no_auth)

        btn_auth.setOnClickListener {
            var intent = Intent(this.applicationContext, AuthActivity::class.java)
            startActivity(intent)
        }

        btn_no_auth.setOnClickListener {
            //sign in anonymous
            mAuth!!.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("FIREBASENAJA", "signInAnonymously:success")
                        var intent = Intent(this.applicationContext, ChatActivity::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("FIREBASENAJA", "signInAnonymously:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }

}
