package ua.com.test.myrecyclerview

import java.util.*
import java.util.concurrent.ThreadLocalRandom

class UsersService {
    private var users = mutableListOf<User>()

    init {
        users = (0..100).map {
            val index = if (it < NAMES.size) it else it % NAMES.size
            User(
                name = NAMES[index],
                description = DESCRIPTION[index],
                age = randomAge(),
                avatar = AVATARS[index]
            )

        }.toMutableList()
    }

    private fun randomAge(): Int {
        return ThreadLocalRandom.current().nextInt(20, 40)
    }

    companion object {
        private val NAMES = mutableListOf(
            "Johnny Bravo",
            "Scooby Doo",
            "Bugs Bunny",
            "Mickey Mouse",
            "Daffy Duck",
            "Bart Simpson",
            "Elmer Fudd",
            "Tweety Bird",
            "Garfield Cat",
            "Sylvester Cat",
            "Tom Cat",
            "Jerry Mouse",
            "Porky Pig",
            "Speedy Gonzales",
            "Yosemite Sam",
        )
        private val DESCRIPTION = mutableListOf(
            "Always wears sunglasses",
            "Loves to eat Scooby Snacks",
            "Has a carrot addiction",
            "Famous Disney character",
            "Can never win against Bugs",
            "Class clown of Springfield",
            "Hunting season",
            "I tawt I taw a puddy tat",
            "Hates Mondays",
            "Forever chasing Tweety",
            "Forever chasing Jerry",
            "Can't resist his favorite snack",
            "Thinks he's the fastest mouse in Mexico",
            "Short-tempered cowboy",
            "Short-tempered gunslinger"
        )
        private val AVATARS = mutableListOf(
            R.drawable.johnny_bravo,
            R.drawable.scooby_doo,
            R.drawable.bugs_bunny,
            R.drawable.mickey_mouse,
            R.drawable.daffy_duck,
            R.drawable.bart_simpson,
            R.drawable.elmer_fudd,
            R.drawable.tweety_bird,
            R.drawable.garfield_cat,
            R.drawable.sylvester_cat,
            R.drawable.tom_cat,
            R.drawable.jerry_mouse,
            R.drawable.porky_pig,
            R.drawable.speedy_gonzales,
            R.drawable.yosemite_sam
        )
    }

    private fun getUsers(): List<User> {
        return users.sortedBy { it.age }
    }

    fun getUsersWithHeaders(): List<ListItem> =
  //      getUsers()
        getUsersWithHeaders(getUsers())

    private fun getUsersWithHeaders(sortedUsers: List<User>): List<ListItem> {
        val usersWithHeaders = mutableListOf<ListItem>()
        var currentAge = 0

        sortedUsers.forEach { user ->
            if (user.age != currentAge) {
                currentAge = user.age
                val header = Header("Age: $currentAge")
                usersWithHeaders.add(header)
            }
            usersWithHeaders.add(user)
        }
        return usersWithHeaders
    }

    fun fetchData(): List<ListItem> {
        val updatedList = mutableListOf<User>()
        for ((index, item) in getUsers().withIndex()) {
            if (index % 2 == 0) {
                updatedList.add(item)
            }
        }
        return getUsersWithHeaders(updatedList)
    }

    fun addRandomUser(): User {
        val random = Random()
        return User(
            name = NAMES[random.nextInt(NAMES.size)],
            description = DESCRIPTION[random.nextInt(DESCRIPTION.size)],
            age = randomAge(),
            avatar = AVATARS[random.nextInt(AVATARS.size)]
        )
    }

}