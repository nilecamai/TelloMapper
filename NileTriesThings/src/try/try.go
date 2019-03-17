package main

import (
	"fmt"
	"time"

	"gobot.io/x/gobot"
	"gobot.io/x/gobot/platforms/dji/tello"
)

func main() {
	drone := tello.NewDriver("8888")

	myCoordinates := []Coordinate{} // create an empty array
	myCoordinates = append(myCoordinates, *NewCoordinate(245, 536))
	myCoordinates = append(myCoordinates, *NewCoordinate(192, 75))
	myCoordinates = append(myCoordinates, *NewCoordinate(678, 428))

	FlyCoordinates(myCoordinates)

	work := func() {
		drone.TakeOff()

		gobot.After(1*time.Second, func() {
			//drone.Forward(20)
			drone.Right(10)
		})

		gobot.After(1*time.Second, func() {
			drone.Forward(40)
		})

		gobot.After(5*time.Second, func() {
			drone.Land()
		})
	}

	robot := gobot.NewRobot("tello",
		[]gobot.Connection{},
		[]gobot.Device{drone},
		work,
	)

	fmt.Println(robot)
	//robot.Start()
}

type Coordinate struct {
	x float32
	y float32
}

func NewCoordinate(x float32, y float32) *Coordinate {
	c := new(Coordinate)
	c.x = x
	c.y = y
	return c
}

// we need a void function that takes an array of coordinates as a parameter and then makes the drone move accordingly
// basic functionality. not including angles or speeds. that will not be fun
// between each set of coordinate points we need to pythag
// we actually don't because coordinates are already in x,y
// so basically we just need to subtract oh this will be easy

func FlyCoordinates(coordinates []Coordinate) {
	for _, coordinate := range coordinates {
		fmt.Println(coordinate)
	}
}
