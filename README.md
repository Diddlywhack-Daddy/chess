# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```

[Phase 2 sequence diagram(https://sequencediagram.org/index.html#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclKX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdKUAGJITgwamURkwHRhOnAUaYRnElknUG4lTlNA+BAIHEiFRsyXM0kgSFyFD8uE3RkM7RS9Rs4ylBQcDh8jqM1VUPGnTUk1SlHUoPUKHxgVKw4C+1LGiWmrWs06W622n1+h1g9W5U6Ai5lCJQpFQSKqJVYFPAmWFI6XGDXDp3SblVZPQN++oQADW6ErU32jsohfgyHM5QATE4nN0y0MxWMYFXHlNa6l6020C3Vgd0BxTF5fP4AtB2OSYAAZCDRJIBNIZLLdvJF4ol6p1JqtAzqBJoIei0azF5vDgHYtQAr5y7Drco67F8H5LCBALnMCBQJigpQIAePKwvuh6ouisTYrBBSuiypTklSNKvigJpEuGBSWtyvIGoKwowERjqiAAPEmOHqAxKjMbkrHutR2jsSg2Fhm6Hq6pksb+tOIbceRHLADaMDiQ6-5QJxsHwYhaDZrm7YqUmylXAM5bAeOrZfNOs7NiZi6YNk5iqVeZT9oOvSGSOowLpOfTmY2lkTn0S6cKu3h+IEXgoOge4Hr4zDHukmQ2eezCytQ17SAAoruaX1GlzQtA+qhPt03lzjpf5QQBXlBhZ87-JgykwXKcEIfY0Wws10VoRimGNYJpHCfh4kBlVPloCRTJujJpQAGY8raknaEKYTFeg-GcdxpTzfIMCwgAhAAvDAirKt1aoCfk60cCg3BiUGQ11iNY1mqok2XddhjTkYC20eJ1V1eVum5Gp7W+lpCBYD+nH6T0By2RgzE-qUTlDgFK6eMFG6Qrau7QjAADio6srFp4JTkSWXilaYVLjWW5fYo5FcNJU-mVQIVctNXWfV+RA9C+OjKobW8wTnUYfxvXjbhA23ezj1kfklozbym3AItCmMytsFrUJuHK9t+2HUqCAnU6Kji098FC-zsKyxN8scorc3Knjo7xo1Wt9bhfMoIy1t-azAM87EXuqKD4MOZD-1XFMdP8+MlT9DHKAAJLSHHACMvYAMwACxPCemQGhWExfDoCCgA2hfAcXTyJwAcqOxd7DAjQw4l8MOYjA5DtHBNxxUCejin6dZ7nUz5-qRnudXUyl+XldT30Xx1w3i9Ny3ZiBWj66BNgPhQNg3DwKJhheykcVnqTbII5UtQNLT9PBOrz69Mvozfg5LOplc7MQUOr-3Jzf6DVToiS9JkL2sI4DHy9iLLEYtzra1JFLP0d0ZwPVDB7dQk0HYwF1t9J+q0WKIPdLrXaB0jpG3getT0eoIGJxDMpduICaHgNHKHHSzEoZ9ETkPYubZYZgCYSUTuzkB6jF4YvNs0k7YbXkl7B0y4grbwCJYK6CFkgwAAFIQB5M7UYgRZ4gAbCTcwV8O430pHeFoicGb3TnEOA+wBVFQDgBABCUBZg8OkO-Cmn9gQGR-v8Iczwy7ONce4kC0xuGD1TrVLmakABWOi0AQKSTyGBKA0RdSocQvCFJBoywwRLLBMicF4LCD9EahCuK5NIfrChxtRBm3DHksA8jtCwi8TbFk2DZp6O9l9MIXjqnrXoXxLCCDMHuj8FoVhowfZjPkNiRhSZEnJIyTmMGHC9KR2hiYuGqyO5I16AcaRloZl6n6TRIZMSN6ozXCFAIXgnFdi9LAYA2AD6EHiIkM+xMBFmIpuUCo6VMrZVysYUq+R9J+1TMAk2cFXoUhQLCJFmRYGNNNpM4p7oQDcDwL7M5HI8VvLVnGcZPVsXmxJQS7pJTLQ0tgLxeQOSpkenxXCZZ-0hHgjRSgdhEMdn+yuK3Um9kgXHL2US9lpL2kssUUAA)]
