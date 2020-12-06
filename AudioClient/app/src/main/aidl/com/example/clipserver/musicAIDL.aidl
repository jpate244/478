// musicAIDL.aidl
package com.example.clipserver;

// Declare any non-default types here with import statements

interface musicAIDL {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String[] getURL();
    boolean playMusic(int song);
    boolean pauseMusic(int song);
    boolean resumeMusic(int song);
    boolean stopMusic(int song);
}
