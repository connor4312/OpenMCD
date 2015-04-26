Protocol
========

The Multicraft daemon protocol is a directional which operates by sending ISO-8859-1 strings over a TCP connection. Herein we will refer to the packets the panel sends as *server packets*, and packets the daemon sends as *daemon packets*.

As far as I am aware, the daemon protocol has not changed substantially since the inception of Multicraft, and no backwards incompatible changes have been made. The foundational information covered here should be valid for all versions.

Example exchange which retrieves version info from the daemon::

    Server: "auth\n"
    Daemon: " token :4275310260\n>OK - token sent\n"
    Server: "codeword: AAYHWgEDWgEOUVZUVVoMBFADAAxSAApQAAFdUAACAgAAClYDBQIAVA==\n"
    Daemon: ">OK - client authorized - welcome\n"
    Server: "version\n"
    Daemon: " info : :version :1.8.2 :remote :1.8.2 :time :1425097329.33 :\n>OK\n"

Server Packets
--------------

Server packets are single strings which end with a line feed (LF) character ``\n``. Unfortunately, as far as I have been able to discern so far, each "action" the server sends has a rather unique signature which requires specialized parsing.

Examples of some server packets::

    Get the daemon version:
    "version\n"

    Confirm a password:
    "codeword: AAYHWgEDWgEOUVZUVVoMBFADAAxSAApQAAFdUAACAgAAClYDBQIAVA==\n"

    Run a jar update:
    "updatejar start ::spudbukkit\n"

    List players of a server:
    "server 2:get players\n"

Daemon Packets
--------------

Strangely, the daemon implements a custom serialization method. It's rather difficult to read for humans, but fairly trivial to generate. A packet is made of zero or more "data" lines following by a "status" line, each delimited by LF characters.

Data lines consist of a space as the first character of the line, followed by one or more key/value pairs delimited by a space followed by a colon. For example, the line " info : :version :1.8.2 :\n" might more commonly be expressed as the JSON object ``{ info: "", version: "1.8.2" }``. In pseudo-code, and encoder might look like the following:

.. code-block:: python

    def escape(str):
        return str.replace(" :", " \\:")

    def encode(data):
        output = " "
        for key, value in data:
            output += escape(key)   + " :"
            output += escape(value) + " :"

        return output + "\n"

.. note::

    For the sake of our sanity, daemon packet information will be presented as JSON throughout this documentation.

Each daemon packet ends with a "status", which is a greater than sign, followed by ``OK`` or ``ERROR``. This can optionally be followed by a space, hyphen, and a space, after which some custom error message may be sent. Finally, the line should end with a line feed.

Examples of some daemon packets::

    Sending an auth toke to the server:
    " token :4275310260\n>OK - token sent\n"

    Responding to a version request:
    " info : :version :1.8.2 :remote :1.8.2 :time :1425097329.33 :\n>OK"

    If the daemon password hash is wrong:
    ">ERROR - incorrect authorization\n"

    Getting jar statuses:
    " jar :minecraft_server.jar :name :Default Minecraft Server :\n" +
    " jar :craftbukkit.jar :name :Mod: Craftbukkit :\n" +
    ">OK - List sent\n"


Authentication
--------------

Authentication is required in order to run any command except ``auth``. Authenticated state is stored on the connection, and within the panel a single connection may be reused across multiple requests (using PHP's ``pfsockopen``).

Rather than using something like TLS to ensure data integrity, authentication is established using the following mechanism:

* Send an ``auth`` packet to the server.
* Receive a reply from the the server indicating a token to use for hashing.
* Do a little cryptographic juggling, and send ``token`` packet back.

The auth is a simple, unadorned string packet that looks like ``auth\n``. Following the sending of this packet, one of three things may happen:

* The daemon might close the connection if the connecting IP is not allowed to connect to it.
* The daemon might say ``>OK - already authed\n`` if authentication has already been established on the connection.
* The daemon can send a token packet, in the form ``token :4275310260\n>OK - token sent\n``

.. note::

    The daemon **always** requires a "password" authentication. If you have not set up password authentication, you should simply use the string ``none`` for the password.

Assuming you've got a token, you now need to do a little work to get the "codeword" to send back. In pseudo-code again:

.. code-block:: python

    password = get_daemon_password()
    codeword = base64_encode(sha1(token + sha1(sha1(password))) ^ sha1(password))

The PHP implementation of this can be found in ``protected/components/McBridge.php`` within the panel, and my Java version is `here <https://github.com/connor4312/OpenMCD/blob/master/src/main/java/mcd/auth/ShaTokenExchange.java#L30>`_.

The resulting codeword should be taken and sent back in a packet like ``codeword: AAYHWgEDWgEOUVZUVVoMBFADAAxSAApQAAFdUAACAgAAClYDBQIAVA==\n``. Then, one of two things will happen:

* You'll get a packet ``>ERROR - incorrect authorization\n`` immediately followed by a disconnect.
* You'll get a packet ``>OK - client authorized - welcome\n``, at which point you're good to go!

A full exchange might look like the following::

    // Successful exchange:
    Server: "auth\n"
    Daemon: " token :4275310260\n>OK - token sent\n"
    Server: "codeword: AAYHWgEDWgEOUVZUVVoMBFADAAxSAApQAAFdUAACAgAAClYDBQIAVA==\n"
    Daemon: ">OK - client authorized - welcome\n"

    // Invalid password:
    Server: "auth\n"`
    Daemon: " token :3455545750\n>OK - token sent\n"
    Server: "codeword: VVUHDwdVX1EOVAQCUlAHDAUCVgldUlEHBgpbC10EAw9aWVdVClRQBA==\n"`
    Daemon: ">ERROR - incorrect authorization\n"

    // Already Authed:
    Server: "auth\n"
    Daemon: ">OK - already authed\n"

    // Invalid IP:
    Server: "auth\n"
    <Daemon Closes Connection>

To recap with a small flowchart::

            SERVER             DAEMON

    +----------------+   +-------------------+
    | `auth` packet  +---> check IP is valid |
    +----------------+   +---------+---------+
                                   |
                                   |
    +----------------+   +---------v---------+
    | crypto juggling<---+ send random token |
    +-------+--------+   +-------------------+
            |
            |
    +-------v--------+   +-------------------+
    | send `token`   +---> verify token      |
    +----------------+   +-------------------+
