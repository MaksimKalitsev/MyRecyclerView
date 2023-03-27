package ua.com.test.myrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ua.com.test.myrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var usersService: UsersService
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MyAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var ShowingFirstList = true

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
            ShowingFirstList = !ShowingFirstList
            adapter.items = getCurrentList()
            swipeRefreshLayout.isRefreshing = false
        }
    }
    private fun getCurrentList(): List<ListItem> {
        return if (ShowingFirstList) {
            usersService.getUsersWithHeaders()
        } else {
            usersService.fetchData()
        }
    }
}