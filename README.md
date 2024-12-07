TP_JAVA_NETWORK_ADEM_REMI

Description :

This project, written in Java, explores network communication using the TCP and UDP protocols. It includes clients, servers, and a graphical user interface to interact with certain network features. The components are tested using JUnit 5 to ensure proper functionality.

There is the structure of the project :


TP_JAVA_NETWORK_ADEM_REMI
├── bin                     # Compiled files (.class)
├── lib                     # External libraries (JAR)
├── src
│   ├── com/project/network
│   │   ├── GUI             # Graphical User Interface (TCPClientGUI.java)
│   │   ├── TCP             # TCP Protocols (TCPClient.java, TCPServer.java, TCPMultiServer.java ConnectionThread.java
│   │   └── UDP             # UDP Protocols (UDPClient.java, UDPServer.java)
│   └── tests/com/project/network
│       ├── TCP             # Unit tests for TCP
│       └── UDP             # Unit tests for UDP
├── vscode                  # VSCode configuration (settings.json)
├── pom.xml                 # Maven configuration file
└── README.md               # Project documentation



Some results : 

![alt text](image-1.png)

![alt text](image-2.png)



## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).



