
import java.util.LinkedList

data class Node(val id : Int, val lat : Double, val lon : Double, var dist : Int, var pos : Array<Int>) : Comparable<Node>{

    var outgoingEdges = LinkedList<Edge>()

    override fun compareTo(other : Node) : Int {
        if(other.id > this.id)
            return -1
        if(other.id < this.id)
            return 1
        return 0
    }

    fun addEdge(edge : Edge){
        outgoingEdges.add(edge)
    }

    fun addEdge(successor : Int,cost : Int, type : Int, maxSpeed : Int){
        addEdge(Edge(this.id, successor,cost, type, maxSpeed))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Node

        if (id != other.id) return false
        if (lat != other.lat) return false
        if (lon != other.lon) return false
        if (dist != other.dist) return false
        if (!pos.contentEquals(other.pos)) return false
        if (outgoingEdges != other.outgoingEdges) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + lat.hashCode()
        result = 31 * result + lon.hashCode()
        result = 31 * result + dist
        result = 31 * result + pos.contentHashCode()
        result = 31 * result + outgoingEdges.hashCode()
        return result
    }
}