package ua.com.test.myrecyclerview

import java.util.concurrent.ThreadLocalRandom

class CarsService {
    private var cars = mutableListOf<Car>()
    init {
        cars = (0..7).map {
            val index = if (it < MODELS.size) it else it % MODELS.size
            Car(
                model = MODELS[index],
                color = COLORS[index],
                age = randomMileage(),
                image = IMAGES[index]
            )

        }.toMutableList()
    }
    private fun randomMileage(): Int {
        return ThreadLocalRandom.current().nextInt(20, 40)
    }

    companion object{
        private val MODELS = mutableListOf(
            "Audi A6",
            "Audi RS",
            "BMW 320",
            "Dodge SRT",
            "Ferrari",
            "Lamborghini",
            "Mercedes GL",
            "BMW 8 series"
        )
        private val COLORS = mutableListOf(
            "Black",
            "Black",
            "Grey",
            "Black",
            "Red",
            "Orange",
            "Grey",
            "Red"
        )
        private val IMAGES = mutableListOf(
            R.drawable.audi_a6,
            R.drawable.audi_rs,
            R.drawable.bmw_3,
            R.drawable.dodge,
            R.drawable.ferrari,
            R.drawable.lamborghini,
            R.drawable.mercedes_gl,
            R.drawable.red_bmw

        )
    }
     fun getCars():List<Car>{
        return cars.sortedBy { it.age }
    }
}
