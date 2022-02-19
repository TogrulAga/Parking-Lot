package parking

data class Car(val registrationNumber: String = "", val color: String = "")

class ParkingSpot {
    private var taken: Boolean = false
    private var car: Car = Car("", "")

    fun park(car: Car = Car()) {
        taken = true
        this.car = car
    }

    fun leave(): Boolean {
        return if (taken) {
            taken = false
            this.car = Car()
            true
        } else {
            false
        }
    }

    fun isTaken(): Boolean {
        return taken
    }

    fun info(): String {
        return "${car.registrationNumber} ${car.color}"
    }

    fun getCarColor(): String {
        return car.color.lowercase()
    }

    fun getRegNumber(): String {
        return car.registrationNumber
    }
}

class ParkingLot {
    private var spots: MutableList<ParkingSpot> = MutableList(0) { ParkingSpot() }
    private var created = false

    fun park(registrationNumber: String, color: String) {
        if (!isCreated()) return

        if (isFull()) return

        for ((spotNumber, spot) in spots.withIndex()) {
            if (!spot.isTaken()) {
                spot.park(Car(registrationNumber, color))
                println("$color car parked in spot ${spotNumber + 1}.")
                return
            }
        }
    }

    fun leave(spotNumber: Int) {
        if (!isCreated()) return

        if (spots[spotNumber - 1].leave()) {
            println("Spot $spotNumber is free.")
        } else {
            println("There is no car in spot $spotNumber.")
        }
    }

    fun create(size: Int) {
        spots = MutableList(size) { ParkingSpot() }
        created = true
        println("Created a parking lot with $size spots.")
    }

    fun status() {
        if (!isCreated()) return

        if (isEmpty()) {
            println("Parking lot is empty.")
            return
        }

        for ((spotNumber, spot) in spots.withIndex()) {
            if (!spot.isTaken()) continue
            println("${spotNumber + 1} ${spot.info()}")
        }
    }

    fun regByColor(color: String) {
        if (!isCreated()) return

        val regNumbers = MutableList(0) { "" }
        for (spot in spots) {
            if (!spot.isTaken()) continue
            if (spot.getCarColor() == color.lowercase()) regNumbers.add(spot.getRegNumber())
        }

        if (regNumbers.size == 0) {
            println("No cars with color $color were found.")
        } else {
            println(regNumbers.joinToString(", "))
        }
    }

    fun spotByColor(color: String) {
        if (!isCreated()) return

        val regNumbers = MutableList(0) { 0 }
        for ((spotNumber, spot) in spots.withIndex()) {
            if (!spot.isTaken()) continue
            if (spot.getCarColor() == color.lowercase()) regNumbers.add(spotNumber + 1)
        }

        if (regNumbers.size == 0) {
            println("No cars with color $color were found.")
        } else {
            println(regNumbers.joinToString(", "))
        }
    }

    fun spotByReg(reg: String) {
        if (!isCreated()) return

        val spot = spots.filter { it.getRegNumber() == reg }
        if (spot.isEmpty()) {
            println("No cars with registration number $reg were found.")
            return
        } else {
            val spotNumber = spots.indexOf(spot[0])
            println(spotNumber + 1)
        }
    }

    private fun isEmpty(): Boolean {
        return spots.all { !it.isTaken() }
    }

    private fun isFull(): Boolean {
        return if (spots.all { it.isTaken()}) {
            println("Sorry, the parking lot is full.")
            true
        } else false
    }

    private fun isCreated(): Boolean {
        if (!created) println("Sorry, a parking lot has not been created.")
        return created
    }
}

object Program {
    fun execute() {
        val parkingLot = ParkingLot()
        while (true) {
            val command = readln().split(" ")

            if (command[0] == "create") {
                parkingLot.create(command[1].toInt())
            } else if (command[0] == "park") {
                parkingLot.park(command[1], command[2])
            } else if (command[0] == "leave") {
                parkingLot.leave(command[1].toInt())
            } else if (command[0] == "status") {
                parkingLot.status()
            } else if (command[0] == "reg_by_color") {
                parkingLot.regByColor(command[1])
            } else if (command[0] == "spot_by_color") {
                parkingLot.spotByColor(command[1])
            } else if (command[0] == "spot_by_reg") {
                parkingLot.spotByReg(command[1])
            } else if (command[0] == "exit") {
                return
            }
        }
    }

}

fun main() {
    Program.execute()
}