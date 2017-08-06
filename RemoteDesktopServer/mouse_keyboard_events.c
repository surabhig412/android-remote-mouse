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
