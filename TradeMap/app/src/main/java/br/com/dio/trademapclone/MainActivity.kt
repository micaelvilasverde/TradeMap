package br.com.dio.trademapclone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import br.com.dio.trademapclone.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by inject()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupNavController()
        // mainViewModel.consumirAcoes()
    }

    private fun setupNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.loginFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.toolbar.setNavigationOnClickListener {
                val popped = navController.popBackStack()
                if (!popped) {
                    finish()
                }
            }
            when (destination.id) {
                R.id.loginFragment -> {
                    esconderActionBar()
                }
                else -> {
                    mostrarActionBar()
                }
            }
        }
    }

    fun esconderActionBar() {
        supportActionBar?.hide()
    }

    fun mostrarActionBar() {
        supportActionBar?.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController.navigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.pararCosumirAcoes()
    }

}