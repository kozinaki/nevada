package main

import (
	"fmt"
	"github.com/kozinaki/nevada/dockercontrolservice/service"
)

var appName = "Docker Control Service"

func main() {
	fmt.Printf("Starting %v\n", appName)
	service.StartWebServer("8080")
}
