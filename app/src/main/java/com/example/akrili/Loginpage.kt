package com.example.akrili

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class loginpage : AppCompatActivity() {


    companion object {
        private const val RC_SIGN_IN = 9001
    }

    private lateinit var auth: FirebaseAuth


 private  lateinit var email : EditText
   private lateinit var pass: EditText




    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginpage)

/***********************************************/
        // Initialize Facebook Login button
        /**********************************************************************************/
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {

            val intent = Intent(this, homepage::class.java)
            startActivity(intent)
            finish()
        }


        val normalsignin = findViewById<AppCompatButton>(R.id.loginbtn)
        normalsignin.setOnClickListener {
            email = findViewById(R.id.emailtext)
            pass = findViewById(R.id.passtext)

            val emailuserinput = email.text
            val passinput = pass.text
            try {
                val credential = EmailAuthProvider.getCredential(emailuserinput.toString(),passinput.toString())
                normalsignIn(credential)
            }catch (e: Exception) {Toast.makeText(this,"Please fill the neccassery data",Toast.LENGTH_LONG).show()}




        }


        val GsignInButton = findViewById<Button>(R.id.signInButton)
        GsignInButton.setOnClickListener {
            GsignIn()
        }
        val registerbtn = findViewById<AppCompatButton>(R.id.registerbtn)
        registerbtn.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
            finish()

        }
    }

    private fun normalsignIn(credential: AuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener {task->

            if(task.isSuccessful){
               if(!auth.currentUser!!.isEmailVerified){Toast.makeText(this,"email is not verified , please check your email before procceding ",Toast.LENGTH_LONG).show()
               } else if (auth.currentUser!!.isEmailVerified) {startActivity(Intent(this, homepage::class.java))
                   finish()}





            }else  Toast.makeText(this,"Wrong creditianls",Toast.LENGTH_LONG).show()
        }


    }



    private fun GsignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_clientId))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, homepage::class.java))
                        finish()
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}


