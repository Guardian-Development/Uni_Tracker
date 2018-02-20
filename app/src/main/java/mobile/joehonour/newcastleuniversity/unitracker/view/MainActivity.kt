package mobile.joehonour.newcastleuniversity.unitracker.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.viewmodels.TestViewModel
import org.koin.android.architecture.ext.viewModel

class MainActivity : AppCompatActivity() {

    private val testViewModel: TestViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        helloWorld.text = testViewModel.testMessage()
    }
}
