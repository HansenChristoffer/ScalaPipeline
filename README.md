# ScalaPipeline

I am hard stuck into Scala at the moment so, am just playing around with different patterns, 
services and tools in Scala. This is one of them.

## How to test it
 * Git clone repository
 * Start IDE and run main
 * Open terminal and type the following: echo -e '\x50\x45\x57\x50\x45\x57' | socat - TCP4:localhost:9999

The pipeline and server takes an array of bytes as input. The application is just designed to return the very 
payload you sent. Basically an echo server with a pipeline... for some reason.

### Example terminal output

[2023-10-01 17:22:27.000000195]-(systems.miso.core.Server)-[Thread-0] | [INFO] -> Server is up and running on the port, 9999
[2023-10-01 17:22:27.000000196]-(systems.miso.core.Server)-[Thread-0] | [INFO] -> Told ConnectionHandler to start!
[2023-10-01 17:22:27.000000196]-(systems.miso.core.ConnectionHandler)-[Thread-2] | [INFO] -> ConnectionHandler has been started!
[2023-10-01 17:22:27.000000196]-(systems.miso.core.ConnectionHandler)-[Thread-2] | [INFO] -> Polling queue for a socket!
[2023-10-01 17:22:37.000000070]-(systems.miso.core.ConnectionHandler)-[Thread-0] | [INFO] -> Added a socket from [/127.0.0.1] to the queue
[2023-10-01 17:22:37.000000071]-(systems.miso.core.ConnectionHandler)-[Thread-2] | [INFO] -> Handling socket from [/127.0.0.1]
[2023-10-01 17:22:37.000000096]-(systems.miso.core.stage.ReceiverStage)-[Thread-2] | [INFO] -> ReceiverStage executed with the following byte array: Array(80, 69, 87, 80, 69, 87, 10)
[2023-10-01 17:22:37.000000096]-(systems.miso.core.stage.CryptographyStage)-[Thread-2] | [INFO] -> CryptographyStage executed with the following byte array: Array(80, 69, 87, 80, 69, 87, 10)
[2023-10-01 17:22:37.000000096]-(systems.miso.core.stage.AnnounceStage)-[Thread-2] | [INFO] -> AnnounceStage executed with the following byte array: Array(80, 69, 87, 80, 69, 87, 10)
[2023-10-01 17:22:37.000000096]-(systems.miso.core.ConnectionHandler)-[Thread-2] | [INFO] -> Pipeline end result: Array(80, 69, 87, 80, 69, 87, 10)
[2023-10-01 17:22:37.000000096]-(systems.miso.core.ConnectionHandler)-[Thread-2] | [INFO] -> Polling queue for a socket!
[2023-10-01 17:22:40.000000211]-(systems.miso.Main$$anon$1)-[Thread-1] | [WARN] -> Received signal to shutdown...
