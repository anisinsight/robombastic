
# Robombastic - Combat Bot Simulation

## Project Description

**Robombastic** is a simulation of robot combat in a grid-based arena. Robots compete by placing bombs to eliminate opponents while avoiding explosions and obstacles. This project was developed as part of an advanced Java academic project and includes various customization and extension features.

### Key Features:
- **Turn-based combat simulation**: Robots can move or drop bombs in each turn.
- **Chain reactions**: Explosions can trigger other bombs to detonate.
- **Customizable arena**: Load arena configurations dynamically via text files.
- **Save and resume**: Serialize game state for later resumption.
- **Cross-team compatibility**: Robots from different teams can interact in the same arena.
- **Multi-threaded management**: Each robot operates on a separate thread.

## Execution Commands

The project can be launched using the following command:

```bash
java -jar robombastic.jar -arena <arena_file> -robotsDir <robots_directory> -radarSize <size> -armyLimit <limit> -turnDuration <ms> -bombDuration <turns>
```

### Available Options:
- `-arena`: Specifies the text file describing the arena.
- `-robotsDir`: Directory containing `.jar` files for robot teams.
- `-radarSize`: The radar's range for each robot.
- `-armyLimit`: The maximum number of robots per army.
- `-turnDuration`: The time allotted for each turn (in milliseconds).
- `-bombDuration`: The number of turns before a bomb explodes.

### Default Values:
If an option is omitted, the program will assign a default value automatically.

## Project Structure

- **`src/`**: Contains the Java source files.
- **`lib/`**: Third-party libraries used for graphical rendering.
- **`arena/`**: Sample files describing arenas.
- **`sprites/`**: Graphical assets for the game.

## Implemented Features

- Full management of game rules and bomb mechanics.
- Graphical interface for visualizing battles.
- Save and load game states.
- Compatibility with dynamic robot teams using introspection.
- Dynamic bot loading: Add new robot teams by simply placing `.jar` files in the robot directory.

## Developer Instructions

1. Clone the repository using Git and import it into your IDE.
2. Compile the project with `ant build.xml` to generate an executable `.jar` file.
3. Add your own robot armies by implementing the `Bot` interface.
