package main

import (
	"bufio"
	"fmt"
	"io"
	"log"
	"os"
	"strconv"
	"strings"
)

type interval struct {
	from int
	to   int
}

type FileScanner struct {
	io.Closer
	*bufio.Scanner
}

func main() {
	fmt.Println("Task 1: " + strconv.Itoa(task_1("input.txt")))
	fmt.Println("Task 2: " + strconv.Itoa(task_2("input.txt")))
}

func read_file(filename string) *FileScanner {
	f, err := os.Open(filename)

	if err != nil {
		log.Fatal(err)
	}
	scanner := bufio.NewScanner(f)
	return &FileScanner{f, scanner}
}

func get_interval(text_interval string) (output interval) {
	values := strings.Split(text_interval, "-")
	valueFrom, err1 := strconv.Atoi(values[0])
	if err1 != nil {
		log.Fatal(err1)
	}
	output.from = valueFrom
	valueTo, err2 := strconv.Atoi(values[1])
	if err2 != nil {
		output.to = valueFrom
	} else {
		output.to = valueTo
	}
	return
}

func is_fully_overlapping(intA interval, intB interval) bool {
	return is_subset(intA, intB) || is_subset(intB, intA)
}

func is_subset(superset interval, subset interval) bool {
	return superset.from <= subset.from && superset.to >= subset.to
}

func task_1(filename string) (output int) {
	scanner := read_file(filename)
	output = 0
	defer scanner.Close()
	for scanner.Scan() {
		intervals := strings.Split(scanner.Text(), ",")
		intervalA := get_interval(intervals[0])
		intervalB := get_interval(intervals[1])
		if is_fully_overlapping(intervalA, intervalB) {
			output++
		}
	}
	return
}

func is_overlapping(intA interval, intB interval) bool {
	return intA.to >= intB.from && intA.to <= intB.to
}

func task_2(filename string) (output int) {
	scanner := read_file(filename)
	output = 0
	defer scanner.Close()
	for scanner.Scan() {
		intervals := strings.Split(scanner.Text(), ",")
		intervalA := get_interval(intervals[0])
		intervalB := get_interval(intervals[1])
		if is_overlapping(intervalA, intervalB) || is_overlapping(intervalB, intervalA) {
			output++
		}
	}
	return
}
