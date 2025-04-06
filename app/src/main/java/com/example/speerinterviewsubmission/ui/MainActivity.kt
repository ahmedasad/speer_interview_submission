package com.example.speerinterviewsubmission.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.speerinterviewsubmission.R
import com.example.speerinterviewsubmission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupNav()

        setView()
    }

    private fun setView() {

//        binding.searchBarUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                if (query.isNullOrEmpty()) {
//                    Toast.makeText(this@MainActivity, "Please Type user name", Toast.LENGTH_SHORT).show()
//                } else if (query.length < 3) {
//                    Toast.makeText(this@MainActivity, "Please Type 3 letters", Toast.LENGTH_SHORT).show()
//                } else {
//                    searchUser(query)
//                }
//                return false
//            }
//
//            private fun searchUser(query: String) {
//                val bundle = Bundle()
//                bundle.putString("Query",query)
//                navController.navigate(R.id.action_fragmentSearchUser_self,bundle)
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
////                if(newText?.length == 0 && binding.txtProfileUserName.text.isNullOrEmpty()) {
////                    binding.txtStatusLabel.isVisible = true
////                    binding.txtStatusLabel.text =
////                        getString(R.string.use_search_bar)
////                    binding.imgStatusLabel.isVisible = false
////                }
//                return true
//            }
//
//        })
    }

    fun setupNav(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_main) as NavHostFragment

        val navController = navHostFragment.navController
    }
}