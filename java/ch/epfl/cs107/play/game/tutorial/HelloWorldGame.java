package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

/**
 * Simple game, to show basic the basic architecture
 */
public class HelloWorldGame implements Game {

	// Store context
	private Window window;

	// We need our physics engine
	private World world;

	// And we need to keep references on our game objects
	private Entity body;

	// graphical representation of the body
	private ImageGraphics graphics;
	private ImageGraphics graphics2;

	// This event is raised when game has just started
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {

		// Store context
		this.window = window;

		// creates physic engine
		world = new World();
		world.setGravity(new Vector(0.0f, -9.81f));

		// creation of the first object; first use a builder
		EntityBuilder entityBuilder = world.createEntityBuilder();
		// we make sure it doesnt move
		entityBuilder.setFixed(true);
		// we define its initial position
		entityBuilder.setPosition(new Vector(0.f, 0.f));
		// the body can now be built
		body = entityBuilder.build();
		
		// Successfully initiated
		graphics = new ImageGraphics("stone.broken.4.png", 1, 1);
		graphics2 = new ImageGraphics("duck.png", 5, 5);
		//Transparency can be chosen for each drawing (0.0 - tranparent, 1.0 - opaque)
		graphics.setAlpha(1.0f);
		graphics2.setAlpha(0.5f);
		//We can also choose depth when drawing
		// Therefore, we could draw behind what is already done
		graphics.setDepth(1.0f);
		graphics2.setDepth(0.0f);
		
		graphics2.setParent(body);
		graphics.setParent(body);
		
		return true;
	}

	// This event is called at each frame
	// will be called "forever" by the main program Program, each call simulating
	// the
	// evolution of the virtual world by time units
	@Override
	public void update(float deltaTime) {
		// The actual rendering will be done now, by the program loop

		// Game logic comes here
		// Nothing to do yet

		// Simulate physics
		// Our body is fixed, though, nothing will move
		world.update(deltaTime);
		
		// We must place the camera where we want
		// We will look at the origin (identity) and increase the view size a bit
		window.setRelativeTransform(Transform.I.scaled(10.0f));
		// we can render our scene now,
		graphics.draw(window);
		graphics2.draw(window);
	}

	// This event is raised after game ends, to release additional resources
	@Override
	public void end() {
		// Empty on purpose, no cleanup required yet
	}

}
