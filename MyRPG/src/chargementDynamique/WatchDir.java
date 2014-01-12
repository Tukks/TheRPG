package chargementDynamique;
/*
 * Copyright (c) 2008, 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.net.MalformedURLException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.LinkOption.*;

import java.nio.file.attribute.*;
import java.io.*;
import java.util.*;

import chargementDynamique.ListenerChargementDyn;

// TODO: Auto-generated Javadoc
/**
 * Example to watch a directory (or tree) for changes to files.
 */

public class WatchDir extends Thread{

	/** The watcher. */
	private final WatchService watcher;
	
	/** The keys. */
	private final Map<WatchKey,Path> keys;
	
	/** The recursive. */
	private final boolean recursive;
	
	/** The trace. */
	private boolean trace = false;
	
	/** The lcd. */
	ListenerChargementDyn lcd ;
	
	/** The dir. */
	final Path dir;
	
	/** The continu. */
	public volatile boolean continu = true;
	
	/**
	 * Cast.
	 *
	 * @param <T> the generic type
	 * @param event the event
	 * @return the watch event
	 */
	@SuppressWarnings("unchecked")
	static <T> WatchEvent<T> cast(WatchEvent<?> event) {
		return (WatchEvent<T>)event;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		while(continu){
		try {
			new WatchDir(dir, recursive).processEvents();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}

	/**
	 * Checks if is continu.
	 *
	 * @return true, if is continu
	 */
	public boolean isContinu() {
		return continu;
	}

	/**
	 * Sets the continu.
	 *
	 * @param continu the new continu
	 */
	public void setContinu(boolean continu) {
		this.continu = continu;
	}

	/**
	 * Register the given directory with the WatchService.
	 *
	 * @param dir the dir
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void register(Path dir) throws IOException {
		WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		if (trace) {
			Path prev = keys.get(key);
			if (prev == null) {
				System.out.format("register: %s\n", dir);
			} else {
				if (!dir.equals(prev)) {
					System.out.format("update: %s -> %s\n", prev, dir);
				}
			}
		}
		keys.put(key, dir);
	}

	/**
	 * Register the given directory, and all its sub-directories, with the
	 * WatchService.
	 *
	 * @param start the start
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void registerAll(final Path start) throws IOException {
		// register directory and sub-directories
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
					throws IOException
					{
				register(dir);
				return FileVisitResult.CONTINUE;
					}
		});
	}

	/**
	 * Creates a WatchService and registers the given directory.
	 *
	 * @param dir the dir
	 * @param recursive the recursive
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public WatchDir(Path dir, boolean recursive) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		this.watcher = FileSystems.getDefault().newWatchService();
		this.keys = new HashMap<WatchKey,Path>();
		this.recursive = recursive;
		this.dir = dir;
		lcd = ListenerChargementDyn.getInstance();

		if (recursive) {
			System.out.format("Scanning %s ...\n", dir);
			registerAll(this.dir);
			System.out.println("Done.");
		} else {
			register(dir);
		}

		// enable trace after initial registration
		this.trace = true;
	}

	/**
	 * Process all events for keys queued to the watcher.
	 *
	 * @throws MalformedURLException the malformed url exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	void processEvents() throws MalformedURLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		for (;;) {
			if(continu == true){
				// wait for key to be signalled
				WatchKey key;
				try {
					key = watcher.take();
				} catch (InterruptedException x) {
					return;
				}

				Path dir = keys.get(key);
				if (dir == null) {
					System.err.println("WatchKey not recognized!!");
					continue;
				}

				for (WatchEvent<?> event: key.pollEvents()) {
					@SuppressWarnings("rawtypes")
					WatchEvent.Kind kind = event.kind();

					// TBD - provide example of how OVERFLOW event is handled
					if (kind == OVERFLOW) {
						continue;
					}

					// Context for directory entry event is the file name of entry
					WatchEvent<Path> ev = cast(event);
					Path name = ev.context();
					Path child = dir.resolve(name);

					// print out event
					System.out.format("%s: %s\n", event.kind().name(), child);


					if(kind == ENTRY_CREATE){
						if(child.toString().contains("class")) lcd.ChargerClass(child.toString());
						if(child.toString().contains("jar")) lcd.ChargerJar(child.toString());

					}else if(event.kind().name().equals(ENTRY_DELETE)){
					}
					// if directory is created, and watching recursively, then
					// register it and its sub-directories
					if (recursive && (kind == ENTRY_CREATE)) {
						try {
							if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
								registerAll(child);
							}
						} catch (IOException x) {
							// ignore to keep sample readbale
						}
					}

				}

				// reset key and remove from set if directory no longer accessible
				boolean valid = key.reset();
				if (!valid) {
					keys.remove(key);

					// all directories are inaccessible
					if (keys.isEmpty()) {
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Usage.
	 */
	static void usage() {
		System.err.println("usage: java WatchDir [-r] dir");
		System.exit(-1);
	}

	/**
	 * Gets the lcd.
	 *
	 * @return the lcd
	 */
	public ListenerChargementDyn getLcd() {
		return lcd;
	}

	/**
	 * Sets the lcd.
	 *
	 * @param lcd the new lcd
	 */
	public void setLcd(ListenerChargementDyn lcd) {
		this.lcd = lcd;
	}


}
