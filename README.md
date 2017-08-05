# android-remote-mouse
An android application which works as a remote mouse to OS X machine.

## To run TCP server

* cd RemoteDesktopServer
* `gcc -o tcp tcp_server.c`
* `./tcp <port_number>`

## To run TCP client

* cd AndroidRemoteClient
* `javac TCPClient.java`
* `java TCPClient <host_address> <port_number>`
