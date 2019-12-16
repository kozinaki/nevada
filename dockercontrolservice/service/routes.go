package service

import (
	"strings"
	"net/http"

	"github.com/docker/docker/api/types"
	"github.com/docker/docker/client"
	"golang.org/x/net/context"
)

type Route struct {
	Name        string
	Method      string
	Pattern     string
	HandlerFunc http.HandlerFunc
}

type Routes []Route

var routes = Routes {
	Route {
		"GetRunningContainers",
		"GET",
		"/runningcontainers/names",
		func(w http.ResponseWriter, r *http.Request) {
			//ctx := context.Background()
			cli, err := client.NewClientWithOpts(client.FromEnv, client.WithAPIVersionNegotiation())
			if err != nil {
				panic(err)
			}
			containers, err := cli.ContainerList(context.Background(), types.ContainerListOptions{})
			if err != nil {
				panic(err)
			}
			w.Header().Set("Content-Type", "application/json; charset=UTF-8")
			jsonStr := "{\"result\":["
			containersStr := ""
			for _, container := range containers {
				if (containersStr == "") {
					containersStr += "\"" + strings.Replace(container.Names[0], "/", "", -1) + "\""
				} else {
					containersStr += ", \"" + strings.Replace(container.Names[0], "/", "", -1) + "\""
				}
				//fmt.Println(container.Names[0])
			}
			w.Write([]byte(jsonStr + containersStr + "]}"))
		},
	},
}
