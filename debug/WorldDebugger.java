package ecs.debug;
import java.util.HashMap;
import ecs.System;

public class WorldDebugger {
	public int iterations;
	public HashMap<System, Long> systems = new HashMap<>();
	public long startTime;

	public void start() {
		startTime = java.lang.System.nanoTime();
	}

	public void end(System system) {
		long currentTime = java.lang.System.nanoTime();
		systems.put(system, currentTime - startTime);
	}
}
