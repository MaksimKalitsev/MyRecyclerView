package ua.com.test.myrecyclerview


fun getSortedUsersAndCarsWithHeaders(sortedObjects: List<PhysicalObject>): List<ListItem> {
    val usersAndCarsWithHeaders = mutableListOf<ListItem>()

    var currentAge = 0
    sortedObjects.forEach { physicalObject ->
        if (physicalObject.age != currentAge) {
            currentAge = physicalObject.age
            val header = HeaderUser("Age: $currentAge")
            usersAndCarsWithHeaders.add(header)
        }
        usersAndCarsWithHeaders.add(physicalObject)
    }

    return usersAndCarsWithHeaders
}

fun fetchData(sortedObjects: List<PhysicalObject>): List<PhysicalObject> {
    val updatedList = mutableListOf<PhysicalObject>()
    for ((index, item) in sortedObjects.withIndex()) {
        if (index % 2 == 0) {
            updatedList.add(item)
        }
    }
    return updatedList
}

fun List<PhysicalObject>.sortByAge() = sortedBy { it.age }