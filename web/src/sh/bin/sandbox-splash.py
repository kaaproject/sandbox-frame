import curses
import sh
import subprocess

MAIN_SCREEN = 1
DETAILS_SCREEN = 2
screen_mode = None

screen = None
HINT_WIDTH = 3
CHANGE_IP_WIDTH = 5
platform = open("/virt_env").read().strip()

class DHCPMisconfiguration(Exception):
    pass


def make_greet_window():
    H, W = screen.getmaxyx()
    greet_win = screen.subwin(H / 2 - HINT_WIDTH, W, 0, 0)
    greet_win.box()
    greet_win.addstr(1, 2, "Kaa Sandbox")
    greet_win.addstr(2, 2, "http://kaaproject.org")


def make_ip_window():
    H, W = screen.getmaxyx()
    ip_win = screen.subwin(H / 2, W, H / 2 - HINT_WIDTH, 0)
    ip_win.box()
    try:
        import socket
        sandbox_port = "9080"
        ip = socket.gethostbyname(socket.gethostname())

        if ip == "127.0.0.1":
            raise DHCPMisconfiguration()
    except sh.ErrorReturnCode:
        ip_win.addstr(1, 2, "===================================")
        ip_win.addstr(2, 2, "Connectivity issues detected!")
        ip_win.addstr(3, 2, "===================================")
        ip_win.addstr(4, 2, "Check VM setup instructions")
        ip_win.addstr(6, 2, "For details, see VM setup instructions")
    except DHCPMisconfiguration:
        ip_win.addstr(1, 2, "===================================")
        ip_win.addstr(2, 2, "Connectivity issues detected!")
        ip_win.addstr(3, 2, "===================================")
        ip_win.addstr(4, 2, "Check connection of network interface")
        ip_win.addstr(7, 2, "For details, see VM setup instructions")
    else:
        ip_win.addstr(1, 2, "To initiate your Kaa Sandbox session,")
        ip_win.addstr(2, 2, "please open a browser and enter this address ")
        ip_win.addstr(3, 2, "in the browser's address field: ")
        if platform == "vbox" and ip == "10.0.2.15":
        	ip_win.addstr(4, 2, "http://%s:%s/sandbox" % ("127.0.0.1",sandbox_port))
        	ip_win.addstr(6, 2, "You can access SSH by $ ssh kaa@%s -p 2222" % "127.0.0.1")
        else:
        	ip_win.addstr(4, 2, "http://%s:%s/sandbox" % (ip,sandbox_port))
        	ip_win.addstr(6, 2, "You can access SSH by $ ssh kaa@%s" % ip)
        ip_win.addstr(8, 2, "Type 'd' for additional details.")


def make_hint_window():
    global screen_mode
    H, W = screen.getmaxyx()
    hint_win = screen.subwin(HINT_WIDTH, W, H - HINT_WIDTH, 0)
    hint_win.box()
    if screen_mode == MAIN_SCREEN:
        if platform == "vmware":
            hint_win.addstr(
                1, 1, "Log in to this virtual machine: Linux/Windows <Alt+F5>, Mac OS X <Ctrl+Alt+F5>")
        else:
            hint_win.addstr(
                1, 1, "Log in to this virtual machine: Linux/Windows <Alt+F5>, Mac OS X <Fn+Alt+F5>")
    elif screen_mode == DETAILS_SCREEN:
        hint_win.addstr(
            1, 1, "Type 'r' to return to main screen.")

def make_web_details_window():
    H, W = screen.getmaxyx()
    det_win = screen.subwin(H - CHANGE_IP_WIDTH - HINT_WIDTH, W, 0, 0)
    det_win.box()
    det_win.addstr(
        1, 2, "Administrative web accounts details:")
    det_win.addstr(
        3, 2, "To login as 'Kaa admin' use:")
    det_win.addstr(
        4, 2, "username: kaa")
    det_win.addstr(
        5, 2, "password: kaa123")
    det_win.addstr(
        7, 2, "To login as 'Tenant admin' use:")
    det_win.addstr(
        8, 2, "username: admin")
    det_win.addstr(
        9, 2, "password: admin123")
    det_win.addstr(
        11, 2, "To login as 'Tenant developer' use:")
    det_win.addstr(
        12, 2, "username: devuser")
    det_win.addstr(
        13, 2, "password: devuser123")

def make_change_ip_window():
    H, W = screen.getmaxyx()
    det_win = screen.subwin(CHANGE_IP_WIDTH, W, H - CHANGE_IP_WIDTH - HINT_WIDTH, 0)
    det_win.box()
    det_win.addstr(
        1, 2, "You can change kaa services host/ip by executing script:")
    det_win.addstr(
        3, 2, "sudo /usr/lib/kaa-sandbox/bin/change_kaa_host.sh <new host/ip>")


def init_main_screen():
    global screen_mode
    screen_mode = MAIN_SCREEN
    curses.noecho()

    make_greet_window()
    make_ip_window()
    make_hint_window()

def init_details_screen():
    global screen_mode
    screen_mode = DETAILS_SCREEN
    curses.noecho()
    make_web_details_window()
    make_change_ip_window()
    make_hint_window()

def show_netinfo():
    commands = [
        "route -n",
        "getent ahosts",
        "ip addr",
        "cat /etc/resolv.conf",
        "cat /etc/hosts",
    ]

    f = file("/tmp/netinfo", "w")
    for cmd in commands:
        f.write("====  %s ==== \n" % cmd)
        f.write(subprocess.Popen(cmd, shell=True,
                stdout=subprocess.PIPE).communicate()[0])
        f.write("\n")
    f.close()
    subprocess.call("less /tmp/netinfo", shell=True)


def main():
    global screen
    global screen_mode
    screen = curses.initscr()
    init_main_screen()

    screen.refresh()

    curses.curs_set(0)

    import sys
    if len(sys.argv) > 1 and sys.argv[1] == "-s":
        screen.getch()
    else:
        while True:
            try:
                c = screen.getch()
                if c == ord('n'):
                    curses.endwin()
                    show_netinfo()
                    screen = curses.initscr()
                    init_main_screen()
                elif c == ord('d'):
                    curses.endwin()
                    screen = curses.initscr()
                    screen.clear()
                    init_details_screen()
                elif c == ord('r'):
                    curses.endwin()
                    screen = curses.initscr()
                    screen.clear()
                    init_main_screen()
                screen.refresh()
            except KeyboardInterrupt:
                pass

    curses.endwin()


if __name__ == '__main__':
    main()
