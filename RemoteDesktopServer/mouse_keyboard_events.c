#include <ApplicationServices/ApplicationServices.h>
#include <unistd.h>

int main() {
    // Move to 300x300
    CGEventRef move1 = CGEventCreateMouseEvent(
        NULL, kCGEventMouseMoved,
        CGPointMake(300, 300),
        kCGMouseButtonLeft // ignored
    );
    // Move to 150x150
    CGEventRef move2 = CGEventCreateMouseEvent(
        NULL, kCGEventMouseMoved,
        CGPointMake(150, 150),
        kCGMouseButtonLeft // ignored
    );
    // Left button down at 350x350
    CGEventRef click1_down = CGEventCreateMouseEvent(
        NULL, kCGEventLeftMouseDown,
        CGPointMake(350, 350),
        kCGMouseButtonLeft
    );
    // Left button up at 750x750
    CGEventRef click1_up = CGEventCreateMouseEvent(
        NULL, kCGEventLeftMouseUp,
        CGPointMake(750, 750),
        kCGMouseButtonLeft
    );
    // Keyboard key Z pressed
    CGEventRef event1, event2, event3;
    event1 = CGEventCreateKeyboardEvent (NULL, (CGKeyCode)6, true);
    CGEventSetFlags(event1, kCGEventFlagMaskShift);
    event2 = CGEventCreateKeyboardEvent (NULL, (CGKeyCode)6, false);
    CGEventSetFlags(event1, kCGEventFlagMaskShift);
    event3 = CGEventCreateKeyboardEvent(NULL, (CGKeyCode)56, false);

    // Now, execute these events with an interval to make them noticeable
    CGEventPost(kCGHIDEventTap, move1);
    CFRelease(move1);
    sleep(2);
    CGEventPost(kCGHIDEventTap, move2);
    CFRelease(move2);
    sleep(2);
    CGEventPost(kCGHIDEventTap, click1_down);
    CFRelease(click1_down);
    sleep(2);
    CGEventPost(kCGHIDEventTap, click1_up);
    CFRelease(click1_up);
    sleep(2);
    CGEventPost(kCGHIDEventTap, event1);
    CGEventPost(kCGHIDEventTap, event2);
    CGEventPost(kCGHIDEventTap, event3);

    // Release the events
    CFRelease(event1);
    CFRelease(event2);
    CFRelease(event3);

    return 0;
}
