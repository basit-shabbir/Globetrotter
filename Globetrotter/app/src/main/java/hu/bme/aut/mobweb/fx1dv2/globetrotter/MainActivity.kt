package hu.bme.aut.mobweb.fx1dv2.globetrotter


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.mobweb.fx1dv2.globetrotter.databinding.ActivityMainBinding




class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    companion object {
        const val KEY_NAME = "KEY_NAME"
        const val KEY_PASSWORD = "KEY_PASSWORD"
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCncel.setOnClickListener() {
            binding.tbUsername.text.clear()
            binding.tbpwd.text.clear()
        }

        binding.btnLogin.setOnClickListener() {
            if (binding.tbUsername.getText().toString().trim().equals("")) {
                binding.tbUsername.setError("Cannot be empty")
            } else {
                var IntentDetails = Intent()
                IntentDetails.setClass(
                    this,
                    CountryListActivity::class.java
                )

                IntentDetails.putExtra(KEY_NAME, binding.tbUsername.text.toString())
                IntentDetails.putExtra(KEY_PASSWORD, binding.tbpwd.text.toString())


                startActivity(IntentDetails)
            }


        }
    }}

