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

func main() {
	str := "411\t358\n408\t398\n443\t395\n452\t355\n411\t358\n"
	coordinates := []Coordinate{}
	c := new(Coordinate)
	result := strings.Split(str, "\n")
	for i := 0; i < len(result)-1; i++ { // minus one because the split makes a split at the end
		coord := strings.Split(result[i], "\t")
		// assumption that there's only an x and a y
		if x, err := strconv.ParseFloat(coord[0], 64); err == nil {
			c.x = x
		}
		if y, err := strconv.ParseFloat(coord[1], 64); err == nil {
			c.y = y
		}
		coordinates = append(coordinates, *NewCoordinate(c.x, c.y))
	}
	drone := tello.NewDriver("8888")

	work := func() {

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
		/*
			gobot.After(time.Duration(t*TIME_DILATION)*time.Second, func() {
				drone.Land()
				fmt.Println("I landed!")
				os.Exit(0)
			})
		*/
		//drone.Halt()
		time.Sleep(time.Duration(TIME_DELAY) * time.Second)
		drone.Land()
		os.Exit(0)
	}

	robot := gobot.NewRobot("tello",
		[]gobot.Connection{},
		[]gobot.Device{drone},
		work,
	)
	robot.Start()
	robot.Stop()
}

/*
func Tokenize(str string) []Coordinate {
	cs := []Coordinate{}
	c := new(Coordinate)
	result := strings.Split(str, "\n")
	for i := 0; i < len(result)-1; i++ { // minus one because the split makes a split at the end
		coord := strings.Split(result[i], "\t")
		// assumption that there's only an x and a y
		if x, err := strconv.ParseFloat(coord[0], 64); err == nil {
			c.x = x
		}
		if y, err := strconv.ParseFloat(coord[1], 64); err == nil {
			c.y = y
		}
		cs = append(cs, *NewCoordinate(c.x, c.y))
	}
	return cs
}
*/

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
