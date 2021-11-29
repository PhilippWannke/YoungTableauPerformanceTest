

data class Tupel(val node : Node, val dist : Int) : Comparable<Tupel> {

    override fun compareTo(other: Tupel): Int {
        return this.dist.compareTo(other.dist)
    }

}