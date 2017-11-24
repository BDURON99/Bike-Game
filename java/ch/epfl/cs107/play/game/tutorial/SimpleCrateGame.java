/*
 *	Author:      Blanche Duron
 *	Date:        23 nov. 2017
 */

package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

/**
 * Simple game where a block falls on another one
 */
public class SimpleCrateGame implements Game {
	// Store context
	private Window window;

	// We need our physics engine
	private World world;

	// And we need to keep references on our game objects
	private Entity block;
	private Entity crate;

	// graphical representation of the body
	private ImageGraphics graphicsBlock;
	private ImageGraphics graphicsCrate;

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
		entityBuilder.setPosition(new Vector(1.f, 0.5f));
		// the bodies can now be built
		block = entityBuilder.build();
		// At this point the body crate is in the world but it has no geometry
		// we need to use another builder to add shapes
		PartBuilder partBuilder = block.createPartBuilder();
		//Create a square polygon, and set the shape of the buiulder to this polygon
		Polygon polygon = new Polygon(
				new Vector(0.f, 0.f),
				new Vector(1.f, 0.f),
				new Vector(1.f, 1.f),
				new Vector(0.f, 1.f)
				);
		partBuilder.setShape(polygon);
		partBuilder.build();
		
		//the creation and construction of crate
		entityBuilder.setFixed(false);
		entityBuilder.setPosition(new Vector(0.2f, 4.f));
		crate = entityBuilder.build();

		graphicsBlock = new ImageGraphics("stone.broken.4.png", 1, 1);
		graphicsCrate = new ImageGraphics("crate.3.png", 1, 1);

		graphicsBlock.setParent(block);
		graphicsCrate.setParent(crate);

		return true;
	}

	// This event is called at each frame
	// will be called "forever" by the main program Program, each call simulating
	// the evolution of the virtual world by time units
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
		graphicsBlock.draw(window);
		graphicsCrate.draw(window);
	}

	// This event is raised after game ends, to release additional resources
	@Override
	public void end() {
		// Empty on purpose, no cleanup required yet
	}
}
