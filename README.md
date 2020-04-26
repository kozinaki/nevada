Enable TCP port 2375 for external connection to Docker
------------------------------------------------------

See this [issue](https://github.com/moby/moby/issues/25471).  
Docker documentation [Troubleshoot the daemon](https://docs.docker.com/config/daemon/#troubleshoot-conflicts-between-the-daemonjson-and-startup-scripts).       
[Source](https://gist.github.com/styblope/dc55e0ad2a9848f2cc3307d4819d819f).

1. Create `daemon.json` file in `/etc/docker`:

        {"hosts": ["tcp://0.0.0.0:2375", "unix:///var/run/docker.sock"]}

2. Add `/etc/systemd/system/docker.service.d/override.conf`

        [Service]
        ExecStart=
        ExecStart=/usr/bin/dockerd


3. Reload the systemd daemon:

        systemctl daemon-reload

4. Restart docker:

        systemctl restart docker.service
