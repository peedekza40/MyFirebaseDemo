package th.ac.buu.se.myfirebasedemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import android.R.attr.password
import android.util.Log
import android.R.attr.password


class AuthActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    var mAuthListener: FirebaseAuth.AuthStateListener? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Auth Activity"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        var btn_login = findViewById<Button>(R.id.btn_login)
        var btn_regis = findViewById<Button>(R.id.btn_regis)
        var input_email = findViewById<EditText>(R.id.input_email)
        var input_pass = findViewById<EditText>(R.id.input_pass)



    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener(mAuthListener!!)
    }

}
