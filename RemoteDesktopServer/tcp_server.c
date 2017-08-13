#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <strings.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <unistd.h>
#include <sys/types.h>
#include "mouse_keyboard_events.c"

#define BUFSIZE 1024

int main(int argc, char **argv) {
  int port_number, parentfd, childfd, optval, n;
  struct sockaddr_in serveraddr, clientaddr;
  char buf[BUFSIZE];
  char output_msg[BUFSIZE] = "Thank you for connecting with our server"; /* message buffer */
  socklen_t clientlen;

  if(argc != 2) {
    fprintf(stderr, "Please provide port number");
    exit(1);
  }
  port_number = atoi(argv[1]);

  //Creating socket
  parentfd = socket(AF_INET, SOCK_STREAM, 0);
  if (parentfd < 0) {
    fprintf(stderr, "ERROR opening socket");
    exit(1);
  }

  /* setsockopt: Handy debugging trick that lets us rerun the server immediately after we kill it otherwise we have to wait about 20 secs.
   * Eliminates "ERROR on binding: Address already in use" error.
   */
  optval = 1;
  setsockopt(parentfd, SOL_SOCKET, SO_REUSEADDR, (const void *)&optval , sizeof(int));

  // Building server socket address
  bzero((char *) &serveraddr, sizeof(serveraddr)); // places zero-valued bytes in serveraddr
  serveraddr.sin_family = AF_INET;
  serveraddr.sin_port = htons((unsigned short)port_number);
  serveraddr.sin_addr.s_addr = htonl(INADDR_ANY);

  // bind the server socket with port number
  if (bind(parentfd, (struct sockaddr *) &serveraddr, sizeof(serveraddr)) < 0)
    fprintf(stderr, "ERROR on binding");

  // server socket is ready to accept connection requests, 5 requests can be queued up
  if (listen(parentfd, 5) < 0)
    fprintf(stderr, "ERROR on listen");

  clientlen = sizeof(clientaddr);
  while(1) {
    // server socket is waiting for client requests
    childfd = accept(parentfd, (struct sockaddr *) &clientaddr, &clientlen);
    if (childfd < 0)
      fprintf(stderr, "ERROR on accept");

    printf("server established connection\n");

    // read input string from client
    bzero(buf, BUFSIZE);
    while((n = read(childfd, buf, BUFSIZE - 1)) > 0);
    if (n < 0)
      fprintf(stderr, "ERROR reading from socket");
    printf("server received: %s\n", buf);
    if(strcmp(buf, "a") == 0){
      leftMouseUp();
    } else if(strcmp(buf, "b") == 0){
      leftMouseDown();
    } else if(strcmp(buf, "c") == 0){
      rightMouseUp();
    } else if(strcmp(buf, "d") == 0){
      rightMouseDown();
    } else if(buf[0] == 'k'){
      memmove(buf, buf + 1, strlen(buf));
      printf("%s printed \n", buf);
      keyboard(buf);
    } else if(buf[0] == 'm'){
      memmove(buf, buf + 1, strlen(buf));
      char *p = strtok(buf, ";");
      float x = atof(p);
      p = strtok (NULL, ";");
      float y = atof(p);
      // while(p != NULL) {
      //   printf("%s printed \n", p);
      //   p = strtok (NULL, ";");
      // }

      mouseMove(x, y);
    }

    // write back to the client
    n = write(childfd, output_msg, strlen(output_msg));
    if (n < 0)
      fprintf(stderr, "%s\n", "ERROR writing to socket");

   close(childfd);
  }
}
