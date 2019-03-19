package gotomobile

import (
	"fmt"
)

func SayHello() {
	fmt.Println("Hello!")
}

func SayHelloWithName(name string) {
	fmt.Println("Hello! " + name)
}

// this returns an error if name is not x
func ThisReturnsAnError(name string) error {
	if name != "x" {
		return fmt.Errorf("name is not x")
	}
	return nil
}

func Returns2Arguments(firstName, LastName string) (string, error) {
	if LastName != "x" {
		return "", fmt.Errorf("eerror")
	}
	return LastName + LastName, nil
}

// This will not work. Second result value must be of type error
//func Returns2Arguments(firstName, LastName string) (string, string) {
// return LastName, firstName
//}
