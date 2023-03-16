package ua.com.test.myrecyclerview

import java.util.concurrent.ThreadLocalRandom

class UsersService {
    private var users = mutableListOf<Users>()

    init {
        users = (0..100).map {
            val index = if (it < NAMES.size) it else it % NAMES.size
            Users(
                name = NAMES[index],
                description = DESCRIPTION[index],
                age = randomAge()
            )

        }.toMutableList()
    }

    private fun randomAge(): String {
        val random = ThreadLocalRandom.current().nextInt(20, 40)
        return "Age: $random"
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

    }

    fun getUsers(): List<Users> {
        return users
    }
}