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
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var fabDel: FloatingActionButton
    private lateinit var carsService: CarsService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MyAdapter()
        usersService = UsersService()
        carsService = CarsService()
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        adapter.items = getCurrentList()

        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            showingFirstList = !showingFirstList
            adapter.items = getCurrentList()
            swipeRefreshLayout.isRefreshing = false
        }
        fabAdd = binding.fabAddUser
        fabAdd.setOnClickListener {
            val randomUser = usersService.addRandomUser()
            val currentItems = adapter.items.toMutableList()
            val randomPosition = Random.nextInt(currentItems.size)
            currentItems.add(randomPosition, randomUser)
            adapter.items = currentItems.toList()
        }
        fabDel = binding.fabDeleteUser
        fabDel.setOnClickListener {
            val randomIndex = Random.nextInt(adapter.items.size)
            val currentItems = adapter.items.toMutableList()
            currentItems.removeAt(randomIndex)
            adapter.items = currentItems.toList()
        }
    }

    private fun getCurrentList(): List<ListItem> {
        return if (showingFirstList) {
            val usersList = usersService.getUsers()
            val carsList = carsService.getCars()
            getSortedUsersAndCarsWithHeaders(usersList + carsList)
        } else {
            fetchData(usersService.getUsers()+carsService.getCars())
        }
    }
}