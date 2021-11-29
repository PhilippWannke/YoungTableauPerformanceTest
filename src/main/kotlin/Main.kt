
import java.util.*
import kotlin.system.measureTimeMillis


const val pathMV : String = "C:/Users/asdf3/OneDrive - bwedu/Uni/3. Semester/Programmier Projekt/MV.fmi"
const val pathGermany : String = "C:/Users/asdf3/OneDrive - bwedu/Uni/3. Semester/Programmier Projekt/germany.fmi"
const val iterations : Int = 100000

val GRAPH : Graph = Graph.parseGraph(pathMV)


fun main(args: Array<String>) {

    println("Amount Nodes: " + GRAPH.amountNodes)
    println("Amount Edges: " + GRAPH.amountEdges)

    val compareDist  : Comparator<Node> = compareBy { it.dist }
    val pq = PriorityQueue<Node>(GRAPH.amountNodes, compareDist)


    // PriorityQueue
    println("\nPriorityQueue")
    val prioAdd = measureTimeMillis {
        for (i in 0 until iterations) {
            GRAPH.nodeList[i].dist = (10..500).random()
            pq.add(GRAPH.nodeList[i])
        }
    }
    println("PrioAdd: $prioAdd")
    var prioRem : Long = 0
    var prioReAdd : Long = 0
    var prioPull : Long = 0
    val prioMix = measureTimeMillis {
        for (k in 1..1){
            for (i in 0 until iterations) {
                prioRem += measureTimeMillis { pq.remove(GRAPH.nodeList[i]) }
                GRAPH.nodeList[i].dist = (0..GRAPH.nodeList[i].dist).random()
                prioReAdd += measureTimeMillis { pq.add(GRAPH.nodeList[i]) }
            }
            for (i in 0 until iterations) {
                prioPull += measureTimeMillis { pq.remove() }
            }
        }
    }
    println("prioRem: $prioRem")
    println("prioReAdd: $prioReAdd")
    println("prioMix: $prioMix")
    println("prioPull: $prioPull")
    println("TOTAL: ${prioAdd + prioMix + prioPull}")



    // YoungTableau
    println("\nYoungTableau")
    val yt : YoungTableau = YoungTableau(GRAPH.amountNodes)
    val ytAdd : Long = measureTimeMillis {
        for (i in 0 until iterations) {
            GRAPH.nodeList[i].dist = (100..500).random()
            yt.insert(GRAPH.nodeList[i])
        }
    }
    println("ytAdd: $ytAdd")
    var ytPull : Long = 0
    val ytMix : Long = measureTimeMillis {
        for (k in 1..1) {
            for (i in 0 until iterations) {
                GRAPH.nodeList[i].dist = (1..GRAPH.nodeList[i].dist).random()
                yt.manipulate(GRAPH.nodeList[i].pos, GRAPH.nodeList[i].dist)
            }
            for (i in 0 until iterations) {
                ytPull += measureTimeMillis { yt.pull().dist.toString() }
            }
        }
    }
    println("ytMix: $ytMix")
    println("ytPull: $ytPull")
    println("TOTAL: ${ytAdd + ytMix + ytPull}")



    // Test
    /*println("\n")
    val yt : YoungTableau = YoungTableau(8)
    yt.insert(Node(1, 0.0, 0.0, 4, Array<Int>(4) {-1} ))
    yt.insert(Node(1, 0.0, 0.0, 1, Array<Int>(4) {-1} ))
    yt.insert(Node(1, 0.0, 0.0, 6, Array<Int>(4) {-1} ))
    yt.insert(Node(1, 0.0, 0.0, 4, Array<Int>(4) {-1} ))
    yt.insert(Node(1, 0.0, 0.0, 7, Array<Int>(4) {-1} ))
    yt.insert(Node(1, 0.0, 0.0, 2, Array<Int>(4) {-1} ))
    yt.insert(Node(1, 0.0, 0.0, 3, Array<Int>(4) {-1} ))
    yt.insert(Node(1, 0.0, 0.0, 1, Array<Int>(4) {-1} ))
    //for (i in (0 until 4)) { print() }
    print(yt.table[1][1][1][1].id.toString() + " " + println(yt.table[1][1][1][1].pos[1]) + " " + println(yt.table[1][1][1][1].pos[2]) + " " + println(yt.table[1][1][1][1].pos[3]) )
    print(yt.pull().dist.toString() + ", ")
    print(yt.pull().dist.toString() + ", ")
    print(yt.pull().dist.toString() + ", ")
    print(yt.pull().dist.toString() + ", ")
    print(yt.pull().dist.toString() + ", ")
    print(yt.pull().dist.toString() + ", ")
    print(yt.pull().dist.toString() + ", ")
    print(yt.pull().dist.toString() + ", ")
    print(yt.pull().dist.toString() + "\n\n") // Int.MAX_VALUE
    yt.printYT()*/


}

