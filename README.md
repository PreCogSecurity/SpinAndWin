# Spin and Win

A secure, well-tested slot machine game built with Java 17, demonstrating modern software engineering practices including proper architecture, input validation, secure random generation, comprehensive testing, and CI/CD.

## Features

- **Secure Random Generation**: Uses `SecureRandom` for cryptographically secure reel spins
- **Input Validation**: Robust validation with bounds checking and sanitization
- **Clean Architecture**: Separation of concerns with domain models, services, and interfaces
- **Comprehensive Testing**: Unit tests with JUnit 5 and Mockito covering all business logic
- **Static Analysis**: Checkstyle for code style, SpotBugs for bug detection
- **CI/CD Pipeline**: GitHub Actions with build, test, security scanning, and CodeQL analysis
- **Dependency Security**: OWASP Dependency Check integration

## Quick Start

### Prerequisites

- Java 17 or later
- Maven 3.9+

### Building

```bash
mvn clean compile
```

### Running Tests

```bash
mvn test
```

### Running Static Analysis

```bash
# Checkstyle
mvn checkstyle:check

# SpotBugs
mvn spotbugs:check

# All verification
mvn verify
```

### Running the Game

```bash
# Run from compiled classes
mvn exec:java -Dexec.mainClass="com.precogsecurity.spinandwin.SpinAndWin"

# Or run the packaged JAR
mvn package
java -jar target/spin-and-win-1.0.0-SNAPSHOT.jar
```

## Project Structure

```
src/
├── main/
│   └── java/
│       └── com/precogsecurity/spinandwin/
│           ├── SpinAndWin.java          # Main entry point
│           ├── Game.java                # Game controller
│           ├── SlotMachine.java         # Slot machine logic
│           ├── Player.java              # Player state management
│           ├── InputValidator.java      # Secure input handling
│           ├── GameOutput.java          # Output interface
│           └── ConsoleOutput.java       # Console implementation
└── test/
    └── java/
        └── com/precogsecurity/spinandwin/
            ├── SlotMachineTest.java
            ├── PlayerTest.java
            ├── InputValidatorTest.java
            ├── GameTest.java
            └── ConsoleOutputTest.java
```

## Architecture

### Domain Models

- **SlotMachine**: Encapsulates reel spinning and outcome evaluation using `SecureRandom`
- **Player**: Manages player points, betting, and game-over state
- **GameOutput**: Interface for output rendering (enables testing and future UI changes)

### Services

- **Game**: Orchestrates game flow, coordinates between components
- **InputValidator**: Handles all user input with validation and sanitization

### Security Considerations

1. **SecureRandom**: Used instead of `Math.random()` for unpredictable reel outcomes
2. **Input Validation**: All user input is validated with bounds checking
3. **No Injection Vectors**: No command execution, SQL, or template injection possible
4. **Resource Management**: Scanner properly closed on game exit
5. **Fail-Safe Defaults**: Points never go negative, bets validated before processing

## CI/CD Pipeline

The GitHub Actions workflow (`.github/workflows/ci.yml`) runs on every push and PR:

1. **Build & Test**: Compiles, runs Checkstyle, SpotBugs, and unit tests
2. **Security Scan**: OWASP Dependency Check for vulnerable dependencies
3. **CodeQL Analysis**: GitHub's semantic code analysis for security issues

## Configuration

### Checkstyle

Code style enforced via `checkstyle.xml` - based on Google Java Style with modifications.

### SpotBugs

Static analysis configured for maximum effort, low threshold, failing on any issues.

### Maven Compiler

Configured with `-Xlint:all -Werror` for strict compilation warnings.

## Development

### Adding Tests

Place test classes in `src/test/java/com/precogsecurity/spinandwin/` following the `*Test.java` naming convention.

### Code Style

Run `mvn checkstyle:check` before committing. The CI will fail on style violations.

### Dependency Updates

Use `mvn versions:display-dependency-updates` to check for updates.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes with tests
4. Ensure all checks pass (`mvn verify`)
5. Submit a pull request

## Security

For security issues, please email security@precogsecurity.com instead of opening a public issue.