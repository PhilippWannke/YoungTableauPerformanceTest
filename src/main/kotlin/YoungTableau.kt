import java.util.Comparator
import kotlin.math.pow

/**
 * multidimensional YoungTableau.
 * x1[n] <= x1[n+1] for each axis.
 * => smallest element at table[1][1]..[1]
 * => biggest element at table[size-2]..[size-2]
 *
 * Has a safety edge to avoid comparisons, be careful with indices.
 *
 * Node with pos = -3 => edge node dist = -1
 * Node with pos = -2 => edge node dist = Int.MAX_VALUE
 * Node with pos = -1 => node not in the table
 */
class YoungTableau(val nodeAmount : Int) {

    val dim : Int = 4
    // calculate size
    val size : Int = nodeAmount.toDouble().pow(1.0 / dim ).toInt() + 3 // Aufrunden + Rand
    // initialize YoungTableau
    val table : Array<Array<Array<Array<Node>>>> = Array<Array<Array<Array<Node>>>>(size)
    { Array<Array<Array<Node>>>(size)
    { Array<Array<Node>>(size)
    { Array<Node>(size)
    { Node(0, 0.0, 0.0, -1, Array<Int>(dim) { -3 } ) } } } }

    init {
        println("size is: $size")
        for (x1 in (1 until size)) {
            for (x2 in (1 until size)) {
                for (x3 in (1 until size)) {
                    for (x4 in (1 until size)) {
                        //table.index(arrayOf(x1, x2, x3, x4)) = Node(0, 0.0, 0.0, Int.MAX_VALUE, Array<Int>(dim) { -2 } )
                    }
                }
            }
        }
    }

    /**
     * Insert a node into the table. Automatically restores the table properties.
     */
    fun insert(node : Node) {
        if (isFull()) {
            println("ChaosNode ist node id ${node.id}")
            throw Exception("YoungTableau ist voll? Bruder du hast mies verkackt. Probiers doch mal mit einem größeren YoungTableau.")
        }
        insertAt(node, Array<Int>(dim) { size-2 })
    }

    /**
     * Insert a node into the table at the given position. Entry has to be empty. Automatically restores the table properties.
     */
    fun insertAt(node : Node, atPosition : Array<Int>) {

        if (table[atPosition[0]][atPosition[1]][atPosition[2]][atPosition[3]].dist != Int.MAX_VALUE) {
            throw Exception("Junge mach doch erst mal Platz befor du eine neue Node reinballerst")
        }

        val compareSecond  : Comparator<Pair<Int, Int>> = compareBy { it.second }
        table[atPosition[0]][atPosition[1]][atPosition[2]][atPosition[3]] = node
        //GRAPH.nodeList[node.id].pos = Array(dim) { size - 2 }
        var currentPos : Array<Int> = atPosition

        print("currentpos: ")
        for(i in (0..3)){
            print(currentPos[i].toString() + ", ")
        }
        println("")


        //println("Currently inserting node with distance ${node.dist}")
        for(iteration in (0..dim*size)) {

            // calculate diffList
            val diffList : Array<Int> = Array(dim) { 0 }
            val currentDist = node.dist
            //println("Nachbarnchecken:")
            //println("aktuelle Position " + pos[0] + ", " + pos[1] + ", " + pos[2] + ", " + pos[3] )
            for (i in (0 until dim)) {
                currentPos[i]--
                diffList[i] = table[currentPos[0]][currentPos[1]][currentPos[2]][currentPos[3]].dist - currentDist
                //println("Das ist der $i te Nachbar bei " + pos[0] + ", " + pos[1] + ", " + pos[2] + ", " + pos[3] + " mit diffList " + table[pos[0]][pos[1]][pos[2]][pos[3]].dist.toString() )
                currentPos[i]++
            }

            // find index with biggest entry in diffList
            var biggestDiffIndice = diffList.mapIndexed() { i, j -> Pair<Int, Int>(i, j)}.maxOfWith( compareSecond ) { p -> p }.first

            // tausche mit biggest        // schneller tausch durch tausch tausch
            if (diffList[biggestDiffIndice] > 0) {
                currentPos[biggestDiffIndice]--
                var temp = table[currentPos[0]][currentPos[1]][currentPos[2]][currentPos[3]]
                table[currentPos[0]][currentPos[1]][currentPos[2]][currentPos[3]] = node
                currentPos[biggestDiffIndice]++
                table[currentPos[0]][currentPos[1]][currentPos[2]][currentPos[3]] = temp
                // update position of switched neigbor node
                table[currentPos[0]][currentPos[1]][currentPos[2]][currentPos[3]].pos[biggestDiffIndice]++

                currentPos[biggestDiffIndice]--
            }
            else {
                break
            }
        }
        GRAPH.nodeList[node.id].pos = currentPos
        //table[node.pos[0]][node.pos[1]][node.pos[2]][node.pos[3]].pos = currentPos
    }

    /**
     * Pull the node with the smallest dist out of the Table. Automatically restores the table properties.
     * @return returns the node with the smallest dist.
     */
    fun pull() : Node {

        if (isEmpty()) {
            // Can be handled, doesn't have to
        }

        var valueToReturn = table[1][1][1][1]
        GRAPH.nodeList[table[1][1][1][1].id].pos = Array<Int>(4) { -1 }
        var pos : Array<Int> = Array(dim) { 1 }
        table[1][1][1][1] = Node(0, 0.0, 0.0, Int.MAX_VALUE, Array<Int>(dim) { -2 } )

        for (iteration in (0..dim*size)) {

            // calculate diffList
            val diffList : Array<Int> = Array(dim) { 0 }
            val currentDist = Int.MAX_VALUE
            for (i in (0 until dim)) {
                pos[i]++
                diffList[i] = currentDist - table[pos[0]][pos[1]][pos[2]][pos[3]].dist
                //println(diffList[i].toString() + ", ")
                pos[i]--
            }

            // find index with smallest entry in diffList
            val compareSecond  : Comparator<Pair<Int, Int>> = compareBy { it.second }
            var smallestDiffIndice = diffList.mapIndexed() { i, j -> Pair<Int, Int>(i, j)}.maxOfWith( compareSecond ) { p -> p }.first

            // tausche mit smallest         // schneller tausch durch tausch tausch
            if (diffList[smallestDiffIndice] != 0) {
                pos[smallestDiffIndice]++
                var temp = table[pos[0]][pos[1]][pos[2]][pos[3]]
                table[pos[0]][pos[1]][pos[2]][pos[3]] = Node(0, 0.0, 0.0, Int.MAX_VALUE, Array<Int>(dim) { -2 })
                pos[smallestDiffIndice]--
                table[pos[0]][pos[1]][pos[2]][pos[3]] = temp

                pos[smallestDiffIndice]++
            }
            else {
                break
            }
        }

        return valueToReturn
    }

    /**
     * Change the dist attribute of the node at the given position
     */
    fun manipulate(position : Array<Int>, newDist : Int) {

        if (table[position[0]][position[1]][position[2]][position[3]].dist == Int.MAX_VALUE) {
            throw Exception("Dega ich hab dir gesagt hör auf mit dem Kiffkraut! Du versuchst eine Stelle zu manipulieren wo keine node vorhanden ist.")
        }

        var manipulatedNodeId : Int = table[position[0]][position[1]][position[2]][position[3]].id
        table[position[0]][position[1]][position[2]][position[3]] = Node(0, 0.0, 0.0, Int.MAX_VALUE, Array<Int>(4) { -1 })

        insertAt(GRAPH.nodeList[manipulatedNodeId], position)
    }


    // funktioniert nur im 2-dimensionalen
    fun printYT() {
        /*for (i in (0 until size)){
            print("[ ")
            for (j in (0 until size)){
                print(table[i][j].dist.toString() + ", ")
            }
            println(" ]")
        }
        print("\n\n")*/
    }

    fun isFull() : Boolean {
        if (table[size-2][size-2][size-2][size-2].dist != Int.MAX_VALUE) {
            println("ist eigentlich voll lol")
            println("sollte fassen: ${(size-2).toDouble().pow(dim)}")
            println(table[5][1][1][1].dist)
            println(table[1][5][1][1].dist)
            println(table[1][1][20][1].dist)
            println(table[1][1][1][20].dist)
        }
        return table[size-2][size-2][size-2][size-2].dist != Int.MAX_VALUE
    }

    fun isEmpty() : Boolean {
        return table[1][1][1][1].dist == Int.MAX_VALUE
    }



}