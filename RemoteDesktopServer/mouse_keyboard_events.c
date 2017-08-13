#include <ApplicationServices/ApplicationServices.h>
#include <unistd.h>

void leftMouseUp() {
  CGEventRef click = CGEventCreateMouseEvent(
          NULL, kCGEventLeftMouseUp,
          CGPointMake(750, 750),
          kCGMouseButtonLeft
      );
  CGEventPost(kCGHIDEventTap, click);
  CFRelease(click);
}

void leftMouseDown() {
  CGEventRef click = CGEventCreateMouseEvent(
          NULL, kCGEventLeftMouseDown,
          CGPointMake(350, 350),
          kCGMouseButtonLeft
      );
  CGEventPost(kCGHIDEventTap, click);
  CFRelease(click);
}

void rightMouseUp() {
  CGEventRef click = CGEventCreateMouseEvent(
          NULL, kCGEventRightMouseUp,
          CGPointMake(950, 950),
          kCGMouseButtonRight
      );
  CGEventPost(kCGHIDEventTap, click);
  CFRelease(click);
}

void rightMouseDown() {
  CGEventRef click = CGEventCreateMouseEvent(
          NULL, kCGEventRightMouseDown,
          CGPointMake(150, 150),
          kCGMouseButtonRight
      );
  CGEventPost(kCGHIDEventTap, click);
  CFRelease(click);
}

void keyboard(char* buf) {
  printf("creating keyboard event: %s", buf);
  CGEventRef event1 = CGEventCreateKeyboardEvent (NULL, (CGKeyCode)atoi(buf), true);
  CGEventRef event2 = CGEventCreateKeyboardEvent (NULL, (CGKeyCode)atoi(buf), false);
  CGEventPost(kCGHIDEventTap, event1);
  CGEventPost(kCGHIDEventTap, event2);
  CFRelease(event1);
  CFRelease(event2);
}

void mouseMove(float x, float y) {
  CGEventRef move1 = CGEventCreateMouseEvent(
         NULL, kCGEventMouseMoved,
         CGPointMake(x, y),
         kCGMouseButtonLeft // ignored
  );
  CGEventPost(kCGHIDEventTap, move1);
  CFRelease(move1);
}
