# android-remote-mouse
An android application which works as a remote mouse to OS X machine.

## To run TCP server

* cd RemoteDesktopServer
* `gcc -o tcp tcp_server.c -Wall -framework ApplicationServices`
* `./tcp <port_number>`

## To run android TCP client

* cd AndroidRemoteClient
* Run the app
