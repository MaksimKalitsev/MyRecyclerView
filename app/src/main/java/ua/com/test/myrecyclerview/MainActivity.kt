package ua.com.test.myrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ua.com.test.myrecyclerview.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var usersService: UsersService
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MyAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var showingFirstList = true
    private lateinit var fab: FloatingActionButton
    private val random = java.util.Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MyAdapter()
        usersService = UsersService()
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        adapter.items = usersService.getUsersWithHeaders()

        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            showingFirstList = !showingFirstList
            adapter.items = getCurrentList()
            swipeRefreshLayout.isRefreshing = false
        }
        fab = binding.fab
        fab.setOnClickListener {
            val randomUser = usersService.addRandomUser()
            val currentItems = adapter.items.toMutableList()
            val randomPosition = random.nextInt(currentItems.size + 1)
            currentItems.add(randomPosition, randomUser)
            adapter.items = currentItems.toList()
            adapter.notifyItemInserted(randomPosition)
        }

    }

    private fun getCurrentList(): List<ListItem> {
        return if (showingFirstList) {
            usersService.getUsersWithHeaders()
        } else {
            usersService.fetchData()
        }
    }
}