START CMD /C CALL "bin\windows\zookeeper-server-start.bat" config\zookeeper.properties
timeout 5
START CMD /C CALL "bin\windows\kafka-server-start.bat" config\server.properties
