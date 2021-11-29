
import java.util.*
import kotlin.math.absoluteValue
import kotlin.system.measureTimeMillis


val pathMV : String = "C:/Users/asdf3/OneDrive - bwedu/Uni/3. Semester/Programmier Projekt/MV.fmi"
val pathGermany : String = "C:/Users/asdf3/OneDrive - bwedu/Uni/3. Semester/Programmier Projekt/germany.fmi"

val GRAPH : Graph = Graph.Companion.parseGraph(pathMV)


fun main(args: Array<String>) {

    println("Amount Nodes: " + GRAPH.amountNodes)
    println("Amount Edges: " + GRAPH.amountEdges)


    /*val tupels : Array<Tupel?> = arrayOfNulls<Tupel>(GRAPH.amountNodes)
    for (i in 0 until tupels.size){
        tupels[i] = Tupel(GRAPH.nodeList[i], (10..500).random())
    }*/
    val compareDist  : Comparator<Node> = compareBy { it.dist }
    val pq = PriorityQueue<Node>(compareDist)


    // PriorityQueue
    println("\nPriorityQueue")
    val prioAdd = measureTimeMillis {
        for (i in 0 until GRAPH.amountNodes) {
            GRAPH.nodeList[i].dist = (10..500).random()
            pq.add(GRAPH.nodeList[i])
        }
    }
    println("PrioAdd: " + prioAdd)
    var prioRem : Long = 0
    var prioReAdd : Long = 0
    var prioPull : Long = 0
    val prioMix = measureTimeMillis {
        for (k in 1..1){
            for (i in 0 until 10) {
                prioRem += measureTimeMillis { pq.remove(GRAPH.nodeList[i]) }
                GRAPH.nodeList[i].dist = (10..GRAPH.nodeList[i].dist).random()
                prioReAdd += measureTimeMillis { pq.add(GRAPH.nodeList[i]) }
            }
            for (i in 0 until 10) {
                prioPull += measureTimeMillis { pq.remove() }
            }
        }
    }
    println("prioRem: " + prioRem)
    println("prioReAdd: " + prioReAdd)
    println("prioPull: " + prioPull)



    // YoungTableau
    println("\nYoungTableau")
    var yt : YoungTableau = YoungTableau(GRAPH.amountNodes)
    val ytAdd : Long = measureTimeMillis {
        for (i in 0 until /*GRAPH.amountNodes*/ 10000) {
            GRAPH.nodeList[i].dist = (100..500).random()
            yt.insert(GRAPH.nodeList[i])
        }
        /*for (i in (0 until 4)){
            print(GRAPH.nodeList[yt.table[5][2][2][4].id].pos[i].toString() + ", ")
        }
        println()*/
    }
    println("ytAdd: " + ytAdd)
    var ytRem : Long = 0
    var ytReAdd : Long = 0
    var ytPull : Long = 0
    val ytMix : Long = measureTimeMillis {
        for (k in 1..1){
            for (i in 0 until 1) {
                // TODO: update Node dist
                //ytRem += measureTimeMillis { pq.remove(GRAPH.nodeList[i]) }
                GRAPH.nodeList[i].dist = (10/*..GRAPH.nodeList[i].dist).random()*/)
                //ytReAdd += measureTimeMillis { pq.add(GRAPH.nodeList[i]) }
                yt.manipulate(GRAPH.nodeList[i].pos, 10)
                for (j in (0 until 4)){
                    print(GRAPH.nodeList[i].pos[j].toString() + ", ")
                }
                println("\n${GRAPH.nodeList[i].dist}")
                println(yt.table[1][1][1][1].dist)
            }
            for (i in 0 until 100) {
                ytPull += measureTimeMillis { print(yt.pull().dist.toString() + ", ") }
            }
        }
    }
    println("ytRem: " + ytRem)
    println("ytReAdd: " + ytReAdd)
    println("ytPull: " + ytPull)



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

