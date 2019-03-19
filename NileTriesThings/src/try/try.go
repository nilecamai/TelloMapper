package main

import (
	"fmt"
	"math"
	"os"
	"strconv"
	"strings"
	"time"

	"gobot.io/x/gobot"
	"gobot.io/x/gobot/platforms/dji/tello"
)

const VELOCITY_CONSTANT float64 = 100
const TIME_DILATION float64 = 0.01
const MAX_ARRAY_SIZE int = 5
const TIME_DELAY int = 2

func fly(coordinates []Coordinate) {
	// func fly(coordinates []Coordinate)
	drone := tello.NewDriver("8888")

	// make an array of velocities to store each of the thingies in
	velocitiesx := [MAX_ARRAY_SIZE]float64{}
	velocitiesy := [MAX_ARRAY_SIZE]float64{}
	times := [MAX_ARRAY_SIZE]float64{}

	work := func() {

		// process string data
		//

		drone.TakeOff()

		time.Sleep(time.Duration(TIME_DELAY) * time.Second)

		var dx, dy, s, vx, vy, t float64
		t = 0

		for i := 0; i < len(coordinates)-1; i++ {
			fmt.Println("first loop ran")

			dx = coordinates[i+1].x - coordinates[i].x
			dy = (coordinates[i+1].y - coordinates[i].y) * -1
			s = math.Sqrt(math.Pow(dx, 2) + math.Pow(dy, 2))
			vx = VELOCITY_CONSTANT * dx / s
			vy = VELOCITY_CONSTANT * dy / s

			velocitiesx[i] = vx
			velocitiesy[i] = vy
			times[i] = t

			if vx >= 0 {
				drone.Right(int(vx))
			} else {
				vx *= -1
				drone.Left(int(vx))
			}
			if vy >= 0 {
				drone.Backward(int(vy))
			} else {
				vy *= -1
				drone.Forward(int(vy))
			}
			fmt.Println(vx)
			fmt.Println(vy)

			//fmt.Println("step " + fmt.Sprintf("", i) + "\tdx: " + fmt.Sprintf("%f", dx) + "\tdy: " + fmt.Sprintf("%f", dy) + "\tvx: " + fmt.Sprintf("%f", vx) + "\tvy: " + fmt.Sprintf("%f", vy) + "\tt: " + fmt.Sprintf("%f", t))

			t = s * TIME_DILATION

			fmt.Println(t)
			time.Sleep(time.Duration(t) * time.Second)

		}

		gobot.After(time.Duration(t*TIME_DILATION)*time.Second, func() {
			drone.Land()
			fmt.Println("I landed!")
			os.Exit(0)
		})
	}

	robot := gobot.NewRobot("tello",
		[]gobot.Connection{},
		[]gobot.Device{drone},
		work,
	)

	//fmt.Println(robot)
	robot.Start()
}

func flyTest() {
	drone := tello.NewDriver("8888")

	work := func() {
		drone.TakeOff()

		gobot.After(0*time.Second, func() {
			drone.Left(20)
			drone.Forward(22)
		})

		gobot.After(13*time.Second, func() {
			drone.Right(29)
			drone.Backward(8)
		})

		gobot.After(33*time.Second, func() {
			drone.Left(27)
			drone.Backward(13)
		})

		gobot.After(33*time.Second, func() {
			drone.Land()
			os.Exit(0)
		})

	}

	robot := gobot.NewRobot("tello",
		[]gobot.Connection{},
		[]gobot.Device{drone},
		work,
	)

	robot.Start()
}

func main() {

	myCoordinates := []Coordinate{} // create an empty array
	myCoordinates = append(myCoordinates, *NewCoordinate(474, 646))
	myCoordinates = append(myCoordinates, *NewCoordinate(210, 347))
	myCoordinates = append(myCoordinates, *NewCoordinate(776, 500))
	myCoordinates = append(myCoordinates, *NewCoordinate(474, 646))
	data := "474\t646\n210\t347\n776\t500\n474\t646\n"

	//fly(myCoordinates)
	//flyTest()
	SplitDemo(data)
	fly(myCoordinates)
}

func SplitDemo(str string) []Coordinate {
	cs := []Coordinate{}
	c := new(Coordinate)
	result := strings.Split(str, "\n")
	for i := 0; i < len(result)-1; i++ { // minus one because the split makes a split at the end i think.
		coord := strings.Split(result[i], "\t")
		// assumption that there's only an x and a y
		if x, err := strconv.ParseFloat(coord[0], 64); err == nil {
			c.x = x
		}
		if y, err := strconv.ParseFloat(coord[1], 64); err == nil {
			c.y = y
		}
		//fmt.Println("" + string(i) + ": " + fmt.Sprintf("%f", c.x) + " " + fmt.Sprintf("%f", c.y))
		cs = append(cs, *NewCoordinate(c.x, c.y))
	}
	return cs
}

type Coordinate struct {
	x float64
	y float64
}

func NewCoordinate(x float64, y float64) *Coordinate {
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
