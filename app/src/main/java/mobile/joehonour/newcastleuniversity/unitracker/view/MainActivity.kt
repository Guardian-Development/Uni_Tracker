package mobile.joehonour.newcastleuniversity.unitracker.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import mobile.joehonour.newcastleuniversity.unitracker.R

class MainActivity : AppCompatActivity() {

    //this is only here as main activity will be removed once actual app content is created
    private val firebaseCurrentUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        helloWorld.text = firebaseCurrentUser.displayName
    }
}
