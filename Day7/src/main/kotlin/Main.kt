import java.io.File

fun task1(inputList: List<String>): Long {
    return getFolders(inputList).filter { it.getSize() < 100000L }.sumOf { it.getSize() }
}

fun task2(inputList: List<String>): Long {
    val folders = getFolders(inputList)
    return smallestDeletable(folders, folders[0].getSize()).getSize()
}

fun getFolders(inputList: List<String>): List<Directory> {
    val directories = mutableListOf<Directory>()
    var current: Directory? = null
    fun processLine(line: String) {
        val args = line.split(" ")
        if(isFile(args)) {
            current?.contents?.add(getFile(args))
        } else if(isChangingDirs(args)) {
            current = if(isReturning(args)) {
                current?.parent
            } else {
                getFolder(args[2], directories, current)
            }
        }
    }
    for (line in inputList) {
        processLine(line)
    }
    return directories
}

fun smallestDeletable(directories: List<Directory>, spaceOccupied: Long): Directory = directories.sortedBy {
    it.getSize() }.first { spaceOccupied - it.getSize() < 40000000L }

fun isFile(args: List<String>): Boolean = args[0].toLongOrNull() != null

fun isReturning(args: List<String>): Boolean = args[2] == ".."

fun isChangingDirs(args: List<String>): Boolean = args[1] == "cd"

fun getFile(args: List<String>): DataFile = DataFile(args[1], args[0].toLong())

fun getFolder(name: String, directories: MutableList<Directory>, parent: Directory?): Directory {
    val newDirectory = Directory(name, mutableListOf(), parent, mutableListOf())
    directories.add(newDirectory)
    parent?.children?.add(newDirectory)
    return newDirectory
}

data class DataFile(val name: String, val size: Long)

data class Directory(
    val name: String,
    val contents: MutableList<DataFile>,
    val parent: Directory?,
    val children: MutableList<Directory>) {
    fun getSize(): Long = contents.sumOf { it.size } + children.sumOf { it.getSize() }
}

fun main(args: Array<String>) {
    println(task1(readFile("D:\\AdventOfCode\\2022\\Day7\\src\\main\\resources\\input.txt")))
    println(task2(readFile("D:\\AdventOfCode\\2022\\Day7\\src\\main\\resources\\input.txt")))
}

fun readFile(fileName: String): List<String> = File(fileName).bufferedReader().readLines()
